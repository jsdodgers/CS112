/**
 * 
 */
package search;

/**
 * @author CHazyhabiT
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONObject;

public class Ranking {

	ArrayList<DocScore> rankDoc ;
	public final int THRESHOLD;

	//	public static void main(String[] args) throws Exception{
	//		Ranking rank = new Ranking();
	//
	//		
	//		
	//		
	//	}

	public Ranking(int length){
		rankDoc=new ArrayList<DocScore>();
		THRESHOLD=(int)(length*0.7)+1;
	}


	public void sort(Hashtable<Integer, DocScore> docS){
		if(docS.isEmpty()) return;
		Set<Entry<Integer, DocScore>> entrySet = docS.entrySet();
		Iterator<Entry<Integer, DocScore>> iter = entrySet.iterator();
		while(iter.hasNext()){
			DocScore next=iter.next().getValue();
			if(next.getUpdateTimes()>=THRESHOLD){
				//if(PlaceFilter.adjacent(next)){
					rankDoc.add(next);
				//}
			}
		}
		Collections.sort(rankDoc);

	}


	public LinkedList<String> topList(int top, String query) throws Exception{
		LinkedList<String> result = new LinkedList<String>();
		result.add(query);
		if(rankDoc.isEmpty()) return result;
		int len = rankDoc.size();
		System.out.println("all relevant doc: " + len);

		for(int i=1;i<=top;++i){
			DocScore doc = rankDoc.get(i-1);
			int docID = doc.getDocID();			
			JSONObject docInfo = Posting.extractDocInfo(docID);

			String title = docInfo.getString("title");
			String URL = docInfo.getString("URL");
			result.add(URL);


			System.out.println();
			//			System.out.println("Top " + i + "------------");
			//			System.out.println("Title " + title);
			//			System.out.println("URL " + URL);
			System.out.println("Page "+i+": " + URL);
			System.out.println("pagerank "+doc.getPageRank()+" cosine: "+ doc.getCosineAnchor()
					+" "+doc.getCosineTitle()+" "+doc.getCosineBody()+ " score: "
					+doc.getDocScore());


		}
		return result;

	}



}
