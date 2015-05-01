//Justin Saletta 38006614
package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency Frequencies}.
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
		List<Frequency> frs = new ArrayList<Frequency>();
		for (int n=0;n<words.size();n++) {
			for (int m = words.size()-1;m>=n;m--) {
				String s = getPalindrome(words,n,m);
				if (s != null) {
					Frequency fr = Utilities.getFrequency(frs,s);
					if (fr == null) fr = new Frequency(s);
					fr.incrementFrequency();
					int loc = frs.size();
					while (loc > 0 && (fr.getText().length() > frs.get(loc-1).getText().length() || (fr.getText().length() == frs.get(loc-1).getText().length() && Utilities.isGreater(fr, frs.get(loc-1))))) {
						loc--;
					}
					frs.add(loc, fr);
					n = m;
					break;
				}
			}
		}
		return frs;
	}
	
	private static String getPalindrome(ArrayList<String> words, int n, int m) {
		int orN = n;
		int orM = m;
		int a = 0;
		int b = words.get(m).length()-1;
		if (n == m && a == b) return null;
		while (n < m || (n == m && a < b)) {
			if (words.get(n).charAt(a) != words.get(m).charAt(b)) return null;
			a++;
			b--;
			if (b < 0) {
				m--;
				b = words.get(m).length()-1;
			}
			if (a >= words.get(n).length()) {
				n++;
				a = 0;
			}
		}
		String ret = "";
		for (int x = orN; x <= orM; x++) {
			if (x > orN) ret += " ";
			ret += words.get(x);
		}
		return ret;// (ret.length() > 1 ? ret : null);
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
