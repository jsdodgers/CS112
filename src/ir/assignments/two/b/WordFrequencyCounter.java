//Justin Saletta 38006614
package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
	 * of {@link Frequency Frequencies}.
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
	public static Pattern stopWords = Pattern.compile("(a|about|above|after|again|against|all|am|an|and|any|are|aren't|as|at|be|because|been|before|being|below|between|both|but|by|can't|cannot|could|couldn't|did|didn't|do|does|doesn't|doing|don't|down|during|each|few|for|from|further|had|hadn't|has|hasn't|have|haven't|having|he|he'd|he'll|he's|her|here|here's|hers|herself|him|himself|his|how|how's|i|i'd|i'll|i'm|i've|if|in|into|is|isn't|it|it's|its|itself|let's|me|more|most|mustn't|my|myself|no|nor|not|of|off|on|once|only|or|other|ought|our|ours|ourselves|out|over|own|same|shan't|she|she'd|she'll|she's|should|shouldn't|so|some|such|than|that|that's|the|their|theirs|them|themselves|then|there|there's|these|they|they'd|they'll|they're|they've|this|those|through|to|too|under|until|up|very|was|wasn't|we|we'd|we'll|we're|we've|were|weren't|what|what's|when|when's|where|where's|which|while|who|who's|whom|why|why's|with|won't|would|wouldn't|you|you'd|you'll|you're|you've|your|yours|yourself|yourselves)$");
	public static List<Frequency> computeWordFrequencies(List<String> words, List<Frequency> frs) {
		// TODO Write body!
		if (frs == null)
			frs = new ArrayList<Frequency>();
		for (String w : words) {
			if (w.length() == 1 || stopWords.matcher(w).matches()) continue;
			Frequency fr = Utilities.getFrequency(frs,w);
			if (fr == null) fr = new Frequency(w);
			fr.incrementFrequency();
			int loc = frs.size();
			while (loc > 0 && Utilities.isGreater(fr, frs.get(loc-1))) {
				loc--;
			}
			frs.add(loc, fr);
		}
		return frs;
	}
	
	public static List<Frequency> computeSubdomainFrequencies(List<String> words, List<Frequency> frs) {
		// TODO Write body!
		if (frs == null)
			frs = new ArrayList<Frequency>();
		for (String w : words) {
			Frequency fr = Utilities.getFrequency(frs,w);
			if (fr == null) fr = new Frequency(w);
			fr.incrementFrequency();
			int loc = frs.size();
			while (loc > 0 && Utilities.isGreaterSubdomain(fr, frs.get(loc-1))) {
				loc--;
			}
			frs.add(loc, fr);
		}
		return frs;
	}
	
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words, null);
		Utilities.printFrequencies(frequencies);
	}
}
