// Justin Saletta	- 38006614
// Daniel Yang		- 84311865
// Connor Richards	- 54689185


package ir.assignments.two.a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */

	public static ArrayList<String> tokenizeFile(File input) {
		ArrayList<String> tokens = new ArrayList<String>();
		try {
			Scanner s = new Scanner(input);
			s.useDelimiter("[^a-zA-Z']+");
			while (s.hasNext()) {
				
				tokens.add(s.next().toLowerCase());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Not Found!");
		}
		return tokens;
	}
	public static ArrayList<String> tokenizeFileSubdomain(File input) {
		ArrayList<String> tokens = new ArrayList<String>();
		try {
			Scanner s = new Scanner(input);
			while (s.hasNextLine()) {
				
				tokens.add(s.nextLine());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Not Found!");
		}
		return tokens;
	}

	public static boolean isGreater(Frequency fr, Frequency other) {
		return fr.getFrequency() > other.getFrequency() ||
				(fr.getFrequency() == other.getFrequency() && fr.getText().compareTo(other.getText()) < 0);
	}

	public static boolean isGreaterSubdomain(Frequency fr, Frequency other) {
		return  fr.getText().compareTo(other.getText()) < 0;
	}
	
	public static Frequency getFrequency(List<Frequency> frs, String word) {
		for (Frequency fr : frs) {
			if (fr.getText().equals(word)) {
				frs.remove(fr);
				return fr;
			}
		}
		return null;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		String gramName = "item";
	//	if (frequencies.size() > 0) {
	//		int grams = frequencies.get(0).getText().split(" +").length;
	//		if (grams > 1) gramName = grams + "-gram";
	//	}
		int total = 0;
		int unique = 0;
		int longest = 0;
		for (Frequency fr : frequencies) {
			total += fr.getFrequency();
			unique++;
			longest = Math.max(longest, fr.getText().length() + 4);
		}
		System.out.println("Total " + gramName + " count: " + total);
		System.out.println("Unique " + gramName + " count: " + unique);
		System.out.println();
		for (Frequency fr : frequencies) {
			System.out.println(String.format("%-" + longest + "s%d",fr.getText(),fr.getFrequency()));
		}
		// TODO Write body!
	}
}
