package indexbuilder;

import java.util.ArrayList;

public class TermFreq {
	
	private int docID;
	private int tfTitle;
	private int tfAnchor;
	private int tfBody;
	private double tfidfTitle;
	private double tfidfAnchor;
	private double tfidfBody;
	private ArrayList<Integer> places;
	
	public TermFreq(int docID){
		this.docID=docID;
		tfTitle=0;
		tfAnchor=0;
		tfBody=0;
		tfidfTitle=0;
		tfidfAnchor=0;
		tfidfBody=0;
		places=new ArrayList<Integer>();
		
	}
	
	public int getDocID(){
		return docID;
	}
	
	public void increaseTFTitle(){
		tfTitle++;
	}
	
	public int getTFTitle(){
		return tfTitle;
	}
	
	public void increaseTFAnchor(){
		tfAnchor++;
	}
	
	public int getTFAnchor(){
		return tfAnchor;
	}
	
	public void increaseTFBody(){
		tfBody++;
	}
	
	public int getTFBody(){
		return tfBody;
	}
	
	public void addPlace(int place){
		places.add(place);
	}
	
	public ArrayList<Integer> getPlaces(){
		return places;
	}
	
	public void setTFIDFTitle(double tfidfTitle){
		this.tfidfTitle=tfidfTitle;
	}
	
	public double getTFIDFTitle(){
		return tfidfTitle;
	}

	public void setTFIDFAnchor(double tfidfAnchor){
		this.tfidfAnchor=tfidfAnchor;
	}
	
	public double getTFIDFAnchor(){
		return tfidfAnchor;
	}
	
	public void setTFIDFBody(double tfidfBody){
		this.tfidfBody=tfidfBody;
	}
	
	public double getTFIDFBody(){
		return tfidfBody;
	}
}
