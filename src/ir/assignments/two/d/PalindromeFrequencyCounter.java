package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.MergeSort;
import ir.assignments.two.b.QuickSort;
import ir.assignments.two.b.SeparateChainingST;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		// TODO Write body!
		SeparateChainingST st=new SeparateChainingST();
		ArrayList<String> palin=new ArrayList<String>();
		ArrayList<Frequency> freq1=new ArrayList<Frequency>();
		ArrayList<Frequency> freq2=new ArrayList<Frequency>();
		int size=words.size();
		String[] strings=new String[size];
		for(int i=0;i<size;++i){
			strings[i]=words.get(i);
		}
		int[] index=new int[size];
		int t=0;
		if(size>0)index[0]=0;
		for(int i=1;i<size;++i){
			t=t+strings[i-1].length();
			index[i]=t;
		}
		palin=LongestPalindromicSubstring.longestPalindrome(strings,index);
		Iterator<String> iter1=palin.iterator();
		while(iter1.hasNext()){
			String temp=(String)iter1.next();
			st.put(temp);
		}
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
		
		// You will likely want to create helper methods / classes to help implement this functionality
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File("/Users/xuke/Documents/workspace/Textprocessing/src/ir/assignments/two/a/test3.txt");
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
