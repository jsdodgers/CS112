package indexbuilder;

import java.util.ArrayList;

public class TermInDoc implements Comparable<TermInDoc>{
	
	private int docID;
	private int frequencyInTitle;
	private int frequencyInAnchor;
	private int frequencyInBody;
	private double tfidfTitle;
	private double tfidfAnchor;
	private double tfidfBody;
	private ArrayList<Integer> places;
	
	public TermInDoc(int docID,int frequencyInTitle,
			int frequencyInAnchor,int frequencyInBody,
			double tfidfTitle,double tfidfAnchor,
			double tfidfBody,ArrayList<Integer> places){
		this.docID=docID;
		this.frequencyInTitle=frequencyInTitle;
		this.frequencyInAnchor=frequencyInAnchor;
		this.frequencyInBody=frequencyInBody;
		this.tfidfTitle=tfidfTitle;
		this.tfidfAnchor=tfidfAnchor;
		this.tfidfBody=tfidfBody;
		this.places=places;
	}
	
	public int compareTo(TermInDoc that){
		if(this.docID<that.docID)return -1;
		else if(this.docID>that.docID)return 1;
		else return 0;
	}
	
	public void setID(int id){
		this.docID=id;
	}
	
	public int getID(){
		return docID;
	}
	
	public void setFrequecnyInTitle(int frequencyInTitle){
		this.frequencyInTitle=frequencyInTitle;
	}
	
	public int getFrequencyInTitle(){
		return frequencyInTitle;
	}
	
	public void setFrequencyInAnchor(int frequencyInAnchor){
		this.frequencyInAnchor=frequencyInAnchor;
	}
	
	public int getFrequencyInAnchor(){
		return frequencyInAnchor;
	}
	
	public void setFrequencyInBody(int frequencyInBody){
		this.frequencyInBody=frequencyInBody;
	}
	
	public int getFrequencyInBody(){
		return frequencyInBody;
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
	
	public void setPlaces(ArrayList<Integer> places){
		this.places=places;
	}
	
	public ArrayList<Integer> getPlaces(){
		return places;
	}
	

}
