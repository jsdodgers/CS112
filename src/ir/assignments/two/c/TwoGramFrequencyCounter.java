package ir.assignments.two.c;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.MergeSort;
import ir.assignments.two.b.SeparateChainingST;
import ir.assignments.two.b.QuickSort;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		// TODO Write body!
		int capacity=words.size();
		ArrayList<Frequency> freq1=new ArrayList<Frequency>();
		ArrayList<Frequency> freq2=new ArrayList<Frequency>();
		StringBuilder[] twogram;
		if(capacity>0){
		twogram=new StringBuilder[capacity-1];
		for(int i=0;i<capacity-1;i++){
			StringBuilder temp=new StringBuilder(words.get(i));
			twogram[i]=temp.append(" "+words.get(i+1));
		}
		}
		else twogram=new StringBuilder[0];
		int length=twogram.length;
		SeparateChainingST st=new SeparateChainingST();
		for(int i=0;i<length;i++){
			String s=twogram[i].toString();
			st.put(s);
		}
		freq1=st.iterate();
		int size=freq1.size();
		Frequency[] wordandfreq=freq1.toArray(new Frequency[size]);
		freq1.clear();
		QuickSort.sort(wordandfreq);
		MergeSort.sort(wordandfreq);
		int count=0;
		for(int i=0;i<wordandfreq.length;++i){
			count=count+wordandfreq[i].getFrequency();
		}
		for(int i=0;i<wordandfreq.length;++i){
			freq2.add(wordandfreq[i]);
		}
		Frequency f=new Frequency("total",count);
		freq2.add(f);
		return freq2;
	
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File("/Users/xuke/Documents/workspace/Textprocessing/src/ir/assignments/two/a/test3.txt");
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
