/**
 * 
 */
package search;

/**
 * @author CHazyhabiT
 *
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Iterator;

import calculate.TFIDF;

import org.json.JSONObject;
import org.json.JSONArray;

import parser.Mysql;

import java.util.Scanner;

import calculate.Token;
import calculate.Stopword;


public class SearchWeb {
	// terms of tokenized query
	ArrayList<String> query = new ArrayList<String>();
	// frequency of corresponding terms
	ArrayList<Integer> queryTF = new ArrayList<Integer>();
	
	
	public static void main(String[] args) throws Exception{
		
		Mysql.establishConnection();
		
		Scanner input = new Scanner(System.in);
		System.out.print("Please Input a query: ");
		String userQuery = input.nextLine();
		input.close();
		
		System.out.println(userQuery);

		SearchWeb sw = new SearchWeb();
		sw.tokenQuery(userQuery);
		
		Hashtable<Integer, DocScore> docS = sw.calDocScore(sw.query, sw.queryTF);
		
		Ranking rank = new Ranking(sw.query.size());
		rank.sort(docS);
		int top = 10;
		LinkedList<String> result = rank.topList(top, userQuery);
		
	
	}
	
	
	public String inputQuery(){
		Scanner input = new Scanner(System.in);
		System.out.print("Please Input a query: ");
		String query = input.nextLine();
		input.close();
		return query;
		
	}
	
	
	
	
	public LinkedList<String> searchQuery(String userQuery) throws Exception{
		

		Mysql.establishConnection();
		
		System.out.println("Restult for query: "+userQuery);
		tokenQuery(userQuery);

		Hashtable<Integer, DocScore> docS = calDocScore(query, queryTF);
		
		Ranking rank = new Ranking(query.size());
		rank.sort(docS);
		int top = 10;
		LinkedList<String> result = rank.topList(top, userQuery);
		ResultGenerator.generateResult(result);
		return result;
		
		
		
	}
	
	
	
	public void tokenQuery(String userQuery) throws Exception {

		HashSet<String> stopword = new Stopword().hashSet();
		
		ArrayList<String> terms = Token.tokenize(userQuery, stopword);
		
		if(!terms.isEmpty()){
			Hashtable<String, Integer> termHt = new Hashtable<String, Integer>();
			for(String term : terms){
				if(!termHt.containsKey(term)){
					termHt.put(term, 1);
					
					
				}else{
					int fre = termHt.get(term);
					++fre;
					termHt.put(term, fre);
				}	
			}
			
			Set<Entry<String, Integer>> entrySet = termHt.entrySet();
			Iterator<Entry<String, Integer>> iter = entrySet.iterator();
			while(iter.hasNext()){
				Entry<String, Integer> entry = iter.next();
				query.add(entry.getKey());
				queryTF.add(entry.getValue());
				

			}
			
		}
		

	}
	
	
	
	public Hashtable<Integer, DocScore> calDocScore(ArrayList<String> query, 
			ArrayList<Integer> queryTF) throws Exception{
		
		Hashtable<Integer, DocScore> docVHt = new Hashtable<Integer, DocScore>();
		if(query.isEmpty()) return docVHt;
		
		// calculate the query vertor (tf-idf)
		
		int len = query.size();
		double[] queryVAnchor = new double[len];
		double[] queryVTitle = new double[len];
		double[] queryVBody = new double[len];
		DocScore queryDocScore = new DocScore(-1, len,null);
		
		

		for(int i=0;i<len;++i){
			String term = query.get(i);
			int qTF = queryTF.get(i);
			
			// extract posting from index
			JSONObject termInfo = Posting.extractPosting(term);
			if(termInfo==null) continue;
			// get the term information
						
			// query

			int qDFAnchor = termInfo.getInt("dfAnchor");
			int qDFTitle = termInfo.getInt("dfTitle");
			int qDFBody = termInfo.getInt("dfBody");
			int N = 70000;
			
			
			
			queryVAnchor[i] = TFIDF.comTFIDF(qTF, qDFAnchor, N);
			queryVTitle[i] = TFIDF.comTFIDF(qTF, qDFTitle, N);
			queryVBody[i] = TFIDF.comTFIDF(qTF, qDFBody, N);
			
			
			// doc
			JSONArray docPosting = termInfo.getJSONArray("termfreq");
	
			for(int j=0;j<docPosting.length();++j){
				JSONObject doc = docPosting.getJSONObject(j);
				
				
				int docID = doc.getInt("docID");
				double docTFIDFAnchor = doc.getDouble("tfidfAnchor");
				double docTFIDFTitle = doc.getDouble("tfidfTitle");
				double docTFIDFBody = doc.getDouble("tfidfBody");
				
				DocScore docScore=new DocScore(docID,len,query);
				if(i!=0){
					if(docVHt.containsKey(docID)){
						docScore = docVHt.get(docID);
						docScore.increaseUpdateTimes();
					}
				}
				
				// Anchor

				double[] tfidfVAnchor = docScore.getTfidfVAnchor();
				tfidfVAnchor[i] = docTFIDFAnchor;
				docScore.setTfidfVAnchor(tfidfVAnchor);
				// Title
				double[] tfidfVTitle = docScore.getTfidfVTitle();
				tfidfVTitle[i] = docTFIDFTitle;
				docScore.setTfidfVTitle(tfidfVTitle);
				// Body
				double[] tfidfVBody = docScore.getTfidfVBody();
				tfidfVBody[i] = docTFIDFBody;
				docScore.setTfidfVBody(tfidfVBody);
				
				
				
				docVHt.put(docID, docScore);
	
			}
		}
		
		queryDocScore.setTfidfVAnchor(queryVAnchor);
		queryDocScore.setTfidfVTitle(queryVTitle);
		queryDocScore.setTfidfVBody(queryVBody);
		
		
		Set<Entry<Integer, DocScore>> entrySet = docVHt.entrySet();
		Iterator<Entry<Integer, DocScore>> iter = entrySet.iterator();
		while(iter.hasNext()){
			Entry<Integer, DocScore> entry = iter.next();
			int docID = entry.getKey();
			JSONObject obj = Posting.extractDocInfo(docID);
			DocScore docScore = entry.getValue();
			docScore.setPageRank(obj.getDouble("pageRank"));
			docScore.calDocScore(queryDocScore);
//			docVHt.put(docID, docScore);
			
		}
		
		return docVHt;
		

	}

}
