package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
		// TODO Write body!
		SeparateChainingST st=new SeparateChainingST();
		Iterator<String> iter1=words.iterator();
		while(iter1.hasNext()){
			st.put(iter1.next());
		}
		ArrayList<Frequency> freq1=new ArrayList<Frequency>();
		ArrayList<Frequency> freq2=new ArrayList<Frequency>();
		freq1=st.iterate();
		int capacity=freq1.size();
		Frequency[] wordandfreq=new Frequency[capacity];
		for(int i=0;i<capacity;++i){
			wordandfreq[i]=freq1.get(i);
		}
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
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File("/Users/xuke/Documents/workspace/Textprocessing/src/ir/assignments/two/a/test3.txt");
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
