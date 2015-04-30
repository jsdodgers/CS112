package indexbuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class XTTerm implements Comparable<XTTerm>{

	private String word;
	private int totalFreq;
	private int dfTitle;	
	private int dfAnchor;	
	private int dfBody;	
	private HashMap<Integer, TermFreq> termFreq;

	public XTTerm(String word){
		this.word=word;
		totalFreq=0;
		dfTitle=0;
		dfAnchor=0;
		dfBody=0;
		termFreq=new HashMap<Integer,TermFreq>(1000);
	}
	
	@Override
	public int compareTo(XTTerm term){
		if(word.compareTo(term.word)<0)return -1;
		else if(word.compareTo(term.word)>0)return 1;
		else return 0;
	}
	
	public ArrayList<Integer> getIDs(){
		ArrayList<Integer> docIDs=new ArrayList<Integer>();
		Iterator<Integer> iterK=termFreq.keySet().iterator();
		while(iterK.hasNext()){
			docIDs.add(iterK.next());
		}
		return docIDs;
	}

	public String getWord(){
		return word;
	}

	private void increaseTotalFreq(){
		totalFreq++;
	}

	public int getTotalFreq(){
		return totalFreq;
	}

	public void increaseDFTitle(){
		dfTitle++;
	}

	public int getDFTitle(){
		return dfTitle;
	}

	public void increaseDFAnchor(){
		dfAnchor++;
	}
	
	public int getDFAnchor(){
		return dfAnchor;
	}
	
	public void increaseDFBody(){
		dfBody++;
	}
	
	public int getDFBody(){
		return dfBody;
	}
	
	public void update(int docID,int place){
		increaseTFBody(docID);
		addPlace(docID,place);
		increaseTotalFreq();
	}

	private void increaseTFBody(int docID){
		if(!termFreq.containsKey(docID)){
			TermFreq termF=new TermFreq(docID);
			termF.increaseTFBody();
			termFreq.put(docID, termF);
		}else{
			termFreq.get(docID).increaseTFBody();
		}		
	}
	
	public void increaseTFTitle(int docID){
		if(!termFreq.containsKey(docID)){
			TermFreq termF=new TermFreq(docID);
			termF.increaseTFTitle();
			termFreq.put(docID, termF);
		}else{
			termFreq.get(docID).increaseTFTitle();
		}		
	}
	
	public void increaseTFAnchor(int docID){
		if(!termFreq.containsKey(docID)){
			TermFreq termF=new TermFreq(docID);
			termF.increaseTFAnchor();
			termFreq.put(docID, termF);
		}else{
			termFreq.get(docID).increaseTFAnchor();
		}		
	}

	public int getTFTitle(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TF:There is no such word in the doc"+docID);
			return Integer.MIN_VALUE;
		}
		return termFreq.get(docID).getTFTitle();
	}
	
	public int getTFAnchor(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TF:There is no such word in the doc"+docID);
			return Integer.MIN_VALUE;
		}
		return termFreq.get(docID).getTFAnchor();
	}
	
	public int getTFBody(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TF:There is no such word in the doc"+docID);
			return Integer.MIN_VALUE;
		}
		return termFreq.get(docID).getTFBody();
	}

	private void addPlace(int docID,int place){
		if(!termFreq.containsKey(docID)){
			TermFreq termF=new TermFreq(docID);
			termF.addPlace(place);
			termFreq.put(docID, termF);
		}else{
			termFreq.get(docID).addPlace(place);
		}
	}

	public void setTFIDFTitle(int docID,double tfidfTitle){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
		}else{
			termFreq.get(docID).setTFIDFTitle(tfidfTitle);;
		}
	}

	public double getTFIDFTitle(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
			return Double.MIN_VALUE;
		}
		return termFreq.get(docID).getTFIDFTitle();

	}
	
	public void setTFIDFAnchor(int docID,double tfidfAnchor){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
		}else{
			termFreq.get(docID).setTFIDFAnchor(tfidfAnchor);;
		}
	}

	public double getTFIDFAnchor(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
			return Double.MIN_VALUE;
		}
		return termFreq.get(docID).getTFIDFAnchor();

	}public void setTFIDFBody(int docID,double tfidfBody){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
		}else{
			termFreq.get(docID).setTFIDFBody(tfidfBody);;
		}
	}

	public double getTFIDFBody(int docID){
		if(!termFreq.containsKey(docID)){
			System.out.println("TFIDF:There is no such term in the doc"+docID);
			return Double.MIN_VALUE;
		}
		return termFreq.get(docID).getTFIDFBody();

	}

	public void toJSON(int index) throws FileNotFoundException{
		JSONArray obj2=new JSONArray();
		JSONObject obj4=new JSONObject();
		LinkedList<TermInDoc> termInDocs=new LinkedList<TermInDoc>();
		Iterator<Entry<Integer,TermFreq>> iterE=termFreq.entrySet().iterator();
		while(iterE.hasNext()){
			Entry<Integer,TermFreq> entry=iterE.next();
			TermFreq termF=entry.getValue();
			int docID=termF.getDocID();
			int frequencyInTitle=termF.getTFTitle();
			int frequencyInAnchor=termF.getTFAnchor();
			int frequencyInBody=termF.getTFBody();
			double tfidfTitle=termF.getTFIDFTitle();
			double tfidfAnchor=termF.getTFIDFAnchor();
			double tfidfBody=termF.getTFIDFBody();
			ArrayList<Integer> places=termF.getPlaces();
			TermInDoc termInDoc=new TermInDoc(docID,frequencyInTitle,frequencyInAnchor,
					frequencyInBody,tfidfTitle,tfidfAnchor,tfidfBody,places);
			termInDocs.add(termInDoc);
		}
		try {
			int size=termInDocs.size();
			for(int i=0;i<size;i++){
				JSONArray obj1=new JSONArray();
				JSONObject obj3=new JSONObject();
				TermInDoc temp=termInDocs.removeFirst();
				ArrayList<Integer> buffer=temp.getPlaces();
				Iterator<Integer> iterA=buffer.iterator();
				while(iterA.hasNext()){
					obj1.put(iterA.next());
				}
				try {
					obj3.put("docID", temp.getID()).put("places", obj1).
					put("frequencyInTitle",temp.getFrequencyInTitle()).
					put("frequencyInAnchor", temp.getFrequencyInAnchor()).
					put("frequencyInBody", temp.getFrequencyInBody()).
					put("tfidfTitle", temp.getTFIDFTitle()).
					put("tfidfAnchor",temp.getTFIDFAnchor()).
					put("tfidfBody",temp.getTFIDFBody());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj2.put(obj3);
			}
//			Iterator<TermInDoc> iterT=termInDocs.iterator();
//			while(iterT.hasNext()){
//				JSONArray obj1=new JSONArray();
//				JSONObject obj3=new JSONObject();
//				TermInDoc temp=iterT.next();
//				ArrayList<Integer> buffer=temp.getPlaces();
//				Iterator<Integer> iterA=buffer.iterator();
//				while(iterA.hasNext()){
//					obj1.put(iterA.next());
//				}
//				try {
//					obj3.put("docID", temp.getID()).put("places", obj1).
//					put("frequencyInTitle",temp.getFrequencyInTitle()).
//					put("frequencyInAnchor", temp.getFrequencyInAnchor()).
//					put("frequencyInBody", temp.getFrequencyInBody()).
//					put("tfidfTitle", temp.getTFIDFTitle()).
//					put("tfidfAnchor",temp.getTFIDFAnchor()).
//					put("tfidfBody",temp.getTFIDFBody());
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				obj2.put(obj3);
//			}
			obj4.put("term",word).put("dfTitle", dfTitle).put("dfAnchor", dfAnchor).put("dfBody", dfBody);
			obj4.put("termfreq", obj2);

			try {
				WriteIntoFile.WriteIndexOne(obj4.toString(),index);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
