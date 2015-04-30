/**
 * 
 */
package search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author CHazyhabiT
 *
 */
public class DocScore implements Comparable<DocScore>{
	private int docID;
	private int updateTimes;
	private ArrayList<String> query;
	private double docScore;
	private double pageRank;
	private double cosineAnchor;
	private double cosineTitle;
	private double cosineBody;
	private double[] tfidfVAnchor;
	private double[] tfidfVTitle;
	private double[] tfidfVBody;
	public final int THRESHOLD;

	public DocScore(int ID, int len,ArrayList<String> query){
		docID = ID;
		updateTimes=1;
		this.query=query;
		docScore = 0.0;
		pageRank = 0.0;
		cosineAnchor = 0.0;
		cosineTitle = 0.0;
		cosineBody = 0.0;
		tfidfVAnchor = new double[len];
		tfidfVTitle = new double[len];
		tfidfVBody = new double[len];
		Arrays.fill(tfidfVAnchor, 0.0);
		Arrays.fill(tfidfVTitle, 0.0);
		Arrays.fill(tfidfVBody, 0.0);
		if(query==null){
			THRESHOLD=0;
		}else{
			THRESHOLD=(int)(query.size()*0.7)+1;
		}


	}

	public int compareTo(DocScore ds){
		Double a = docScore;
		Double b = ds.getDocScore();
		return -a.compareTo(b);
	}

	public void calDocScore(DocScore query){
		calCosine(query);
		double pageRankScaled=pageRank;
		
		int counterAnchor=0;
		for(int i=0;i<tfidfVAnchor.length;i++){
			if(tfidfVAnchor[i]>0){
				counterAnchor++;
			}

		}
		int counterTitle=0;
		for(int i=0;i<tfidfVTitle.length;i++){
			if(tfidfVTitle[i]>0){
				counterTitle++;
			}

		}
		
		if(pageRank>3){
			if(cosineAnchor==0&&cosineTitle==0){
				pageRankScaled=3;
			}
			if(counterAnchor<THRESHOLD&&counterTitle<THRESHOLD){
				pageRankScaled=3;
			}
		}

		
		//		System.out.println(cosineAnchor);
		//		System.out.println(cosineTitle);
		//		System.out.println(cosineBody);
		docScore =  pageRankScaled*(cosineAnchor*0.3 + cosineTitle*0.4 + cosineBody*0.3);



	}

	public void calCosine(DocScore query){


		//		double cosBody = docScore.getCosineBody();
		//		double[] tfidfVBody = docScore.getTfidfVBody();
		//		cosBody += docTFIDFBody * queryVBody[i];
		//		tfidfVBody[i] = docTFIDFBody;
		//		docScore.setCosineBody(cosBody);
		//		docScore.setTfidfVBody(tfidfVBody);
		if(calLength(tfidfVAnchor)==0){
			cosineAnchor=0;
		}else{
			cosineAnchor = calCosine(tfidfVAnchor, query.getTfidfVAnchor())/calLength(tfidfVAnchor);
		}

		if(calLength(tfidfVTitle)==0){
			cosineTitle=0;
		}else{
			cosineTitle = calCosine(tfidfVTitle, query.getTfidfVTitle())/calLength(tfidfVTitle);
		}

		if(calLength(tfidfVBody)==0){
			cosineBody=0;
		}else{
			cosineBody = calCosine(tfidfVBody, query.getTfidfVBody())/calLength(tfidfVBody);
		}




	}

	private double calCosine(double[] doc, double[] query){
		if(doc.length!=query.length){
			System.out.println("error!");
			return 0.0;
		}
		double cosine = 0;
		for(int i=0;i<query.length;++i){
			cosine += doc[i] * query[i];

		}
		return cosine;


	}

	private double calLength(double[] tfidfV){
		double length = 0;
		for(double x : tfidfV){
			length += x * x;
		}
		return Math.sqrt(length);

	}








	public int getDocID(){
		return docID;
	}

	public void increaseUpdateTimes(){
		updateTimes++;
	}

	public int getUpdateTimes(){
		return updateTimes;
	}

	public void setDocScore(double val){
		docScore = val;
	}

	public double getDocScore(){
		return docScore;
	}

	public void setPageRank(double val){
		pageRank = val;
	}

	public double getPageRank(){
		return pageRank;
	}

	public void setCosineAnchor(double val){
		cosineAnchor = val;
	}

	public double getCosineAnchor(){
		return cosineAnchor;
	}

	public void setCosineTitle(double val){
		cosineTitle = val;
	}

	public double getCosineTitle(){
		return cosineTitle;
	}

	public void setCosineBody(double val){
		cosineBody = val;
	}

	public double getCosineBody(){
		return cosineBody;
	}

	public void setTfidfVAnchor(double[] val){
		tfidfVAnchor = val;
	}

	public double[] getTfidfVAnchor(){
		return tfidfVAnchor;
	}
	public void setTfidfVTitle(double[] val){
		tfidfVTitle = val;
	}

	public double[] getTfidfVTitle(){
		return tfidfVTitle;
	}
	public void setTfidfVBody(double[] val){
		tfidfVBody = val;
	}

	public double[] getTfidfVBody(){
		return tfidfVBody;
	}

	public void setQuery(ArrayList<String> val){
		query = val;
	}

	public ArrayList<String> getQuery(){
		return query;
	}




}
