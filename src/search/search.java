package search;

import indexer.TermFrequency;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.Collection;

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
			JSONObject indexJSON = (JSONObject) obj;
			System.out.println("searching...");
			JSONObject index = (JSONObject) indexJSON.get("index");
			JSONObject docid2termlist = (JSONObject) indexJSON.get("docid2termlist");
			JSONArray termid2term = (JSONArray) indexJSON.get("termid2term");
			JSONArray docid2doc = (JSONArray)indexJSON.get("docid2doc");
			JSONArray docMaxFrequencies = (JSONArray)indexJSON.get("docMaxFrequencies");
			System.out.println(docMaxFrequencies);
			
			int[] docFrequenciesMax = new int[docid2termlist.size()];
			for (Object docid : docid2termlist.keySet()) {
				JSONArray termlist = (JSONArray)docid2termlist.get(docid);
				docFrequenciesMax[Integer.parseInt((String)docid)] = computeTermFrequenciesMax(termlist, null);
			}
			List<Integer> docFrequencyList = new ArrayList<Integer>();
			for (int n : docFrequenciesMax) {
				docFrequencyList.add(n);
			}
		//	JSONObject indexJSON2 = new JSONObject();
			long start = System.currentTimeMillis();
			ArrayList<ArrayList<Integer>> docid2termlistarrays = new ArrayList<ArrayList<Integer>>();
			for (Object termlist : docid2termlist.values()) {
				ArrayList<Integer> list = new ArrayList<Integer>();     
				JSONArray jsonArray = (JSONArray)termlist; 
				if (jsonArray != null) { 
					int len = jsonArray.size();
					for (int i=0;i<len;i++){ 
						list.add(Integer.parseInt(jsonArray.get(i).toString()));
					} 
				} 
				docid2termlistarrays.add(list);
//				if (list.contains(n)) docWith++;
			}
			long end1=System.currentTimeMillis();
			long runningTime1=(end1-start);
			System.out.println("Current running time: " + runningTime1);
			List<Integer> idfs = new ArrayList<Integer>();
			for (int n=0;n<termid2term.size();n++) {
				long end=System.currentTimeMillis();
				long runningTime=(end-start);
				if (n % 500 == 0) System.out.print(n + "/" + termid2term.size() + "  --  " + runningTime + "ms (" + (runningTime/1000) + "s)");
				
				int docWith = 0;
				for (ArrayList<Integer> termlist : docid2termlistarrays) {
					if (termlist.contains(n)) docWith++;
				}
				if (n % 500 == 0) System.out.println("  -->  " + docWith);
				idfs.add(docWith);
			}
			
	//		JSONObject indexJSON = new JSONObject();
			indexJSON.put("termid2doccount", idfs);
			FileWriter filee = new FileWriter("./index.txt");
			//	file.write(indexJSON.toJSONString());
			indexJSON.writeJSONString(filee);
			filee.flush();
			filee.close();
			
			
			
			
			Scanner sc = new Scanner(System.in);
			//"mondego"
			while (sc.hasNextLine()) {
				String keyofTerm = termid2term.indexOf(sc.nextLine()) + "";
				
				System.out.println(keyofTerm);
				
				JSONObject docsTermIsIn = (JSONObject) index.get(keyofTerm);
				
				System.out.println(docsTermIsIn);
					
				Iterable tempIter = docsTermIsIn.keySet();
				for (Object key : tempIter) {
					double values = (Double)docsTermIsIn.get(key);
					System.out.println(values);
					int docid = Integer.parseInt((String)key);
					System.out.println(docid2doc.get(docid));
				}
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
	

	public static int computeTermFrequenciesMax(JSONArray words, List<TermFrequency> frs) {
		// TODO Write body!
		if (frs == null)
			frs = new ArrayList<TermFrequency>();
		for (int n=0;n<words.size();n++) {
			long ww = (Long)words.get(n);
			int w = (int)ww;
			TermFrequency fr = getFrequency(frs,w);
			if (fr == null) fr = new TermFrequency(w);
			fr.incrementFrequency();
			int loc = frs.size();
			while (loc > 0 && isGreater(fr, frs.get(loc-1))) {
				loc--;
			}
			frs.add(loc, fr);
		}
		if (frs.size() == 0) return 0;
		return frs.get(0).getFrequency();
	}
	

	public static boolean isGreater(TermFrequency fr, TermFrequency other) {
		return fr.getFrequency() > other.getFrequency();
	}
	
	public static TermFrequency getFrequency(List<TermFrequency> frs, Integer word) {
		for (TermFrequency fr : frs) {
			if (fr.getText() == word) {
				frs.remove(fr);
				return fr;
			}
		}
		return null;
	}
	
}
