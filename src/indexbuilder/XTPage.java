package indexbuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class XTPage {
	
	private int docID;
	private String URL;
	private String title;
	private String anchor;
	private double pageRank;
	private int length;
	private int numOfChildren;
	private HashSet<Integer> parents;
	
	public XTPage(int docID){
		this.docID=docID;
		URL=null;
		title=null;
		anchor=null;
		pageRank=1;
		length=0;
		numOfChildren=0;
		parents=new HashSet<Integer>();
	}
	
	public int getID(){
		return docID;
	}
	
	public void setURL(String URL){
		this.URL=URL;
	}
	
	public String getURL(){
		return URL;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setLength(int length){
		this.length=length;
	}
	
	public int getLength(){
		return length;
	}
	
	public void setPageRank(double pageRank){
		this.pageRank=pageRank;
	}
	
	public double getPageRank(){
		return pageRank;
	}
	
	public void setAnchor(String anchor){
		this.anchor=anchor;
	}
	
	public String getAnchor(){
		return anchor;
	}
	
	public void increaseChildrenNum(){
		numOfChildren++;
	}
	
	public int getChildrenNum(){
		return numOfChildren;
	}
	
	public void addParent(int docID){
		if(!parents.contains(docID)&&docID!=this.docID){
			parents.add(docID);
		}
	}
	
	public LinkedList<Integer> getParents(){
		LinkedList<Integer> list=new LinkedList<Integer>();
		Iterator<Integer> iter=parents.iterator();
		while(iter.hasNext()){
			list.add(iter.next());
			}
		return list;
	}
	
	public void toJSON(){
		JSONObject obj1=new JSONObject();
		JSONArray array1=new JSONArray();
		try {
			Iterator<Integer> iter=parents.iterator();
			while(iter.hasNext()){
				array1.put(iter.next());
			}
			obj1.put("docID", docID).put("URL", URL).put("title", title).put("anchor", anchor).put("pageRank", pageRank).put("length", length).put("numOfChildren", numOfChildren).put("parents",array1 );
			try {
				WriteIntoFile.WriteIndexTwo(obj1.toString());
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
