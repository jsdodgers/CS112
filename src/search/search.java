package search;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.*;
import org.json.simple.parser.*;

import ucar.ma2.Section.Iterator;

public class search {

	public static void main(String[] args) throws IOException, ParseException {
		String file = args[0];
		readFromFile(file);
		System.out.println("done");
	}
	
	static void readFromFile(String file) throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		try {
			FileReader fr = new FileReader(file);
			Object obj = parser.parse(fr);	//this take FOREVER!!!
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println("searching...");
			JSONArray termid2termArray = (JSONArray) jsonObject.get("termid2term");
			String keyofTerm = termid2termArray.indexOf("mondego") + "";
			
			System.out.println(keyofTerm);
			
			JSONObject indexObject = (JSONObject) jsonObject.get("index");
			JSONObject docsTermIsIn = (JSONObject) indexObject.get(keyofTerm);
			
			System.out.println(docsTermIsIn);
			
			Iterable tempIter = docsTermIsIn.keySet();
			for (Object key : tempIter) {
				double values = (double)docsTermIsIn.get(key);
				System.out.println(values);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	//get term id
	//get docs including the term id
	//return docs that pass algorithm
	
	/**
	 * index			hashmap of all termID's. Each with their own hashmap of documentID's to TFIDF.
	 * termid2term		list of terms (for getting their id's)
	 * docid2doc		list of documents (for getting their id's)
	 * docid2termlist	map of documents. with list of terms inside each.
	 */
}
