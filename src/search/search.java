package search;

import indexer.TermFrequency;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.TreeMap;

import org.json.simple.*;
import org.json.simple.parser.*;

import ucar.ma2.Section.Iterator;

public class search {

	public static void main(String[] args) throws IOException, ParseException {
		String file = args[0];
		readFromFile(file,args[1]);
		System.out.println("done");
	}
	
	static void readFromFile(String file, String folderPath) throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		try {
			FileReader fr = new FileReader(file);
			Object obj = parser.parse(fr);	//this take FOREVER!!!
			JSONObject indexJSON = (JSONObject) obj;
			JSONObject index = (JSONObject) indexJSON.get("index");
			JSONObject docid2termlist = (JSONObject) indexJSON.get("docid2termlist");
			JSONArray termid2term = (JSONArray) indexJSON.get("termid2term");
			JSONArray docid2doc = (JSONArray)indexJSON.get("docid2doc");
			JSONArray docMaxFrequencies = (JSONArray)indexJSON.get("docMaxFrequencies");
			JSONArray termid2doccount = (JSONArray)indexJSON.get("termid2doccount");
		/*	
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
			indexJSON.put("docMaxFrequencies", docFrequencyList);
			indexJSON.put("termid2doccount", idfs);
			FileWriter filee = new FileWriter("./index11.txt");
			//	file.write(indexJSON.toJSONString());
			indexJSON.writeJSONString(filee);
			filee.flush();
			filee.close();
			
			*/
			

			System.out.println("Enter a search query:");
			Scanner sc = new Scanner(System.in);
			//"mondego"
			while (sc.hasNextLine()) {
				String text = sc.nextLine();
		//		String[] query = text.split(" ");
				String[] query = text.split(" (|a|about|above|after|again|against|all|am|an|and|any|are|arent|as|at|be|because|been|before|being|below|between|both|but|by|cant|cannot|could|couldnt|did|didnt|do|does|doesnt|doing|dont|down|during|each|few|for|from|further|had|hadnt|has|hasnt|have|havent|having|he|hed|hell|hes|her|here|heres|hers|herself|him|himself|his|how|hows|i|im|ive|if|in|into|is|isnt|it|its|its|itself|lets|me|more|most|mustnt|my|myself|no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shant|she|shed|shell|shes|should|shouldnt|so|some|such|than|that|thats|the|their|theirs|them|themselves|then|there|theres|these|they|theyd|theyll|theyre|theyve|this|those|through|to|too|under|until|up|very|was|wasnt|we|wed|well|were|weve|were|werent|what|whats|when|whens|where|wheres|which|while|who|whos|whom|why|whys|with|wont|would|wouldnt|you|youd|youll|youre|youve|your|yours|yourself|yourselves) | ");
				if (text.equals("")) query = new String[0];
				
			//	System.out.println("You are searching for: " + text);
				List<Integer> termlist = new ArrayList<Integer>();
				List<String> uniqueTermList = new ArrayList<String>();
				int ind = 0;
				int count = 0;
				for (String word : query) {
			//		if (stopWords.matcher(word).matches()) continue;
					if (word.length() == 0) continue;
					int i = count;
					if (termid2term.contains(word)) {//.containsKey(word)) {
						i = termid2term.indexOf(word);//term2termid.get(word);
					}
					else {
					//	term2termid.put(word, count);
						continue;
					//	count++;
					}
					termlist.add(i);
					if (!uniqueTermList.contains(word)) uniqueTermList.add(word);
				}
				
				HashMap<Integer,ArrayList<Double>> termVectors = new HashMap<Integer,ArrayList<Double>>();
				int currentTerm = 0;
				for (String term : uniqueTermList) {
					if (!termid2term.contains(term)) continue;
					currentTerm++;
					int keyofTerm = termid2term.indexOf(term);
					JSONObject docsTermIsIn = (JSONObject) index.get(keyofTerm+"");
					Iterable tempIter = docsTermIsIn.keySet();
					for (Object key : tempIter) {
						double value = (Double)docsTermIsIn.get(key);
						int docid = Integer.parseInt((String)key);
						ArrayList<Double> termVector;
						if (termVectors.containsKey(docid)) {
							termVector = termVectors.get(docid);
						}
						else {
							termVector = new ArrayList<Double>();
							termVectors.put(docid, termVector);
						}
						while (termVector.size() < currentTerm-1) termVector.add(0.0);
						termVector.add(value);
					}
				}
				HashMap<Integer,Integer> termqueryfrequencies = new HashMap<Integer,Integer>();
				for (int term : termlist) {
					if (termqueryfrequencies.containsKey(term)) {
						termqueryfrequencies.put(term, termqueryfrequencies.get(term)+1);
					}
					else {
						termqueryfrequencies.put(term, 1);
					}
				}
				int max = 0;
				for (Integer i : termqueryfrequencies.values()) {
					if (i > max) max = i;
				}
				double[] queryTFIDFs = new double[uniqueTermList.size()];
				for (int n=0;n<uniqueTermList.size();n++) {
					String term = uniqueTermList.get(n);
					int termid = termid2term.indexOf(term);
					int termcount = 0;
					for (int term2 : termlist) {
						if (term2 == termid) termcount++;
					}
					double tf = 0.5 + (0.5 * termcount) / max;
					double idf = Math.log10(49957.0/(((Long)termid2doccount.get(termid)).doubleValue() + 1.0));
					double tfidf = tf * idf;
					queryTFIDFs[n] = tfidf;
				}
				int queryM = 0;
				for (double d : queryTFIDFs) {
					queryM += d * d;
				}
				double queryMag = Math.sqrt(queryM);
				HashMap<Integer,Double> cosines = new HashMap<Integer,Double>();
			//	HashMap<Integer,Double> dotProducts = new HashMap<Integer,Double>();
				for (Integer docid : termVectors.keySet()) {
					ArrayList<Double> termVector = termVectors.get(docid);
					double mag = 0;
					double dotProduct = 0;
					int i = 0;
					for (Double d : termVector) {
						if (i < queryTFIDFs.length) {
							dotProduct += queryTFIDFs[i] * d;
						}
						mag += d * d;
						i++;
					}
					if (uniqueTermList.size() == 1) cosines.put(docid, termVector.get(0));
					else cosines.put(docid, dotProduct/(queryMag * Math.sqrt(mag)));
				}

		//        ValueComparator bvc =  new ValueComparator(cosines);
				ValueComparator bvc = new ValueComparator(cosines);
				TreeMap<Integer,Double> sorted_map = new TreeMap<Integer,Double>(bvc);

		        sorted_map.putAll(cosines);

		        int maxResults = 5;
		    	for (Integer key : sorted_map.keySet()) {
		    		if (maxResults <=0) break;
					FileReader fr2 = new FileReader(folderPath + "/" + docid2doc.get(key));
					Object obj2 = parser.parse(fr2);
					JSONObject jsonObject = (JSONObject)obj2;
					String url = ((String)jsonObject.get("_id"));
					System.out.println(url);
					fr2.close();
					maxResults--;
		    	}
		    	if (maxResults == 5) System.out.println("No results found for query: " + text);

				System.out.println("Enter a search query:");
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


class ValueComparator implements Comparator<Integer> {

    Map<Integer, Double> base;
    public ValueComparator(Map<Integer, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(Integer a, Integer b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
