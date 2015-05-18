package indexer;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.*;
import org.json.simple.parser.*;

import webcrawler.Crawler;
import webcrawler.WriteIntoFile;

public class indexer {
	public static void main(String[] args) {
		String folder = args[0];
		readFromFolder(folder);
	}
	
	public static void readFromFolder(String folderPath) {
		final File folder = new File(folderPath);
		Collection<String> files = listFilesForFolder(folder);
		long start=System.currentTimeMillis();
		
//		System.out.println(folderPath + ":\n\n" + files);
	//	List<Frequency> frequencies = new ArrayList<Frequency>();
	//	int i = 0;
	//	int longest = 0;
	//	String url = "";
	//	Scanner lengths = new Scanner(new File("./CrawlOut/Length.txt"));
			
			
		/*	i++;
			String current = lengths.nextLine();
			File file = new File("./CrawlOut/Pages/" + s);
			List<String> words = Utilities.tokenizeFile(file);
			frequencies = WordFrequencyCounter.computeWordFrequencies(words, frequencies);
			System.out.println(i + " " + current + "   " + s + " - " + frequencies.get(0).getText() + " : " + frequencies.get(0).getFrequency());
			if (words.size() > longest) {
				longest = words.size();
				url = current + "  _-_  " + s;
			}
		//	if (i > 20) break;
		 
		 */
//		}
		JSONParser parser = new JSONParser();
		try {
			HashMap<String, Integer> term2termid = new HashMap<String,Integer>();
			HashMap<Integer,List<Integer>> docid2termlist = new HashMap<Integer,List<Integer>>();
			int count = 0;
			int docIndex = 0;
			for (String s : files) {
				Object obj = parser.parse(new FileReader(folderPath + "/" + s));
				JSONObject jsonObject = (JSONObject)obj;
				String text = ((String)jsonObject.get("text")).toLowerCase();
				String[] words = text.split(" (|a|about|above|after|again|against|all|am|an|and|any|are|arent|as|at|be|because|been|before|being|below|between|both|but|by|cant|cannot|could|couldnt|did|didnt|do|does|doesnt|doing|dont|down|during|each|few|for|from|further|had|hadnt|has|hasnt|have|havent|having|he|hed|hell|hes|her|here|heres|hers|herself|him|himself|his|how|hows|i|im|ive|if|in|into|is|isnt|it|its|its|itself|lets|me|more|most|mustnt|my|myself|no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shant|she|shed|shell|shes|should|shouldnt|so|some|such|than|that|thats|the|their|theirs|them|themselves|then|there|theres|these|they|theyd|theyll|theyre|theyve|this|those|through|to|too|under|until|up|very|was|wasnt|we|wed|well|were|weve|were|werent|what|whats|when|whens|where|wheres|which|while|who|whos|whom|why|whys|with|wont|would|wouldnt|you|youd|youll|youre|youve|your|yours|yourself|yourselves) | ");
			//	String[] words = text.split(" ");//(|a|about|above|after|again|against|all|am|an|and|any|are|arent|as|at|be|because|been|before|being|below|between|both|but|by|cant|cannot|could|couldnt|did|didnt|do|does|doesnt|doing|dont|down|during|each|few|for|from|further|had|hadnt|has|hasnt|have|havent|having|he|hed|hell|hes|her|here|heres|hers|herself|him|himself|his|how|hows|i|im|ive|if|in|into|is|isnt|it|its|its|itself|lets|me|more|most|mustnt|my|myself|no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shant|she|shed|shell|shes|should|shouldnt|so|some|such|than|that|thats|the|their|theirs|them|themselves|then|there|theres|these|they|theyd|theyll|theyre|theyve|this|those|through|to|too|under|until|up|very|was|wasnt|we|wed|well|were|weve|were|werent|what|whats|when|whens|where|wheres|which|while|who|whos|whom|why|whys|with|wont|would|wouldnt|you|youd|youll|youre|youve|your|yours|yourself|yourselves) | ");
				if (text.equals("")) words = new String[0];
				List<Integer> termlist = new ArrayList<Integer>();
				int index = 0;
				for (String word : words) {
			//		if (stopWords.matcher(word).matches()) continue;
					if (word.length() == 0) continue;
					int i = count;
					if (term2termid.containsKey(word)) {
						i = term2termid.get(word);
					}
					else {
						term2termid.put(word, count);
						count++;
					}
					termlist.add(i);
				}
				docid2termlist.put(docIndex++, termlist);
			}
			int[] docFrequenciesMax = new int[docid2termlist.size()];
			for (Integer docid : docid2termlist.keySet()) {
				List<Integer> termlist = docid2termlist.get(docid);
				docFrequenciesMax[docid] = computeTermFrequenciesMax(termlist, null);
			}
			String[] termid2term = new String[term2termid.size()];
			for (String term : term2termid.keySet()) {
				termid2term[term2termid.get(term)] = term;
			}
			HashMap<Integer,HashMap<Integer,Double>> index = new HashMap<Integer,HashMap<Integer,Double>>();
			double docCount = files.size();
			for (int n=0;n<termid2term.length;n++) {
				HashMap<Integer,Double> tfidfmap = new HashMap<Integer,Double>();
				double docWith = 0;
				for (List<Integer> termlist : docid2termlist.values()) {
					if (termlist.contains(n)) docWith++;
				}
				double idf = Math.log10(docCount/docWith);
				for (Integer docid : docid2termlist.keySet()) {
					int occurences = 0;
					List<Integer> termlist = docid2termlist.get(docid);
					for (Integer i : termlist) {
						if (i == n) occurences++;
					}
					if (occurences==0) continue;
					double tf = 0.5 + (0.5 * occurences)/docFrequenciesMax[docid];
					double tfidf = tf * idf;
					tfidfmap.put(docid, tfidf);
				}
				index.put(n, tfidfmap);
			}
			
	/*		System.out.println(term2termid + "\n");
			for (List<Integer> termlist : docid2termlist.values()) {
				System.out.println(termlist);
			}
			System.out.println("\n");
			System.out.println(Arrays.toString(termid2term));
			System.out.println("\n");
			System.out.println("\n");
			System.out.println(index);*/
			long end=System.currentTimeMillis();
			long runningTime=(end-start);
			System.out.println("1. Number of documents: " + files.size());
			System.out.println("2. Unique words: " + termid2term.length);
			System.out.println("5. Total Time: " + runningTime + "ms (" + (runningTime/1000) + "s)");
			
			
			JSONObject indexJSON = new JSONObject();
			indexJSON.put("termid2term",Arrays.asList(termid2term));
			indexJSON.put("docid2termlist",docid2termlist);
			indexJSON.put("docid2doc", files);
			indexJSON.put("index", index);
//			System.out.println("\n\n\n\n\n\n\n");
//			System.out.println(indexJSON);
			FileWriter file = new FileWriter("./index.txt");
			file.write(indexJSON.toJSONString());
			file.flush();
			file.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Pattern stopWords = Pattern.compile("(a|about|above|after|again|against|all|am|an|and|any|are|arent|as|at|be|because|been|before|being|below|between|both|but|by|cant|cannot|could|couldnt|did|didnt|do|does|doesnt|doing|dont|down|during|each|few|for|from|further|had|hadnt|has|hasnt|have|havent|having|he|hed|hell|hes|her|here|heres|hers|herself|him|himself|his|how|hows|i|im|ive|if|in|into|is|isnt|it|its|its|itself|lets|me|more|most|mustnt|my|myself|no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shant|she|shed|shell|shes|should|shouldnt|so|some|such|than|that|thats|the|their|theirs|them|themselves|then|there|theres|these|they|theyd|theyll|theyre|theyve|this|those|through|to|too|under|until|up|very|was|wasnt|we|wed|well|were|weve|were|werent|what|whats|when|whens|where|wheres|which|while|who|whos|whom|why|whys|with|wont|would|wouldnt|you|youd|youll|youre|youve|your|yours|yourself|yourselves)$");
	
	public static int computeTermFrequenciesMax(List<Integer> words, List<TermFrequency> frs) {
		// TODO Write body!
		if (frs == null)
			frs = new ArrayList<TermFrequency>();
		for (Integer w : words) {
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
	
	public static Collection<String> listFilesForFolder(final File folder) {
		ArrayList<String> files = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	         files.add(fileEntry.getName());
	    }
	  //  String[] filesArr = (String[]) files.toArray();
	    Collections.sort(files, new Comparator<String>() {
	        public int compare(String s1, String s2) {
	            return Integer.compare(Integer.parseInt(s1.replace(".txt", "")), Integer.parseInt(s2.replace(".txt","")));
	        }
	    });
	    return files;
	//    return Arrays.asList(filesArr);
	}
	
}
