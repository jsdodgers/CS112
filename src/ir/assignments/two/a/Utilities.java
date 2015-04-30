package ir.assignments.two.a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

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
		// TODO Write body!
		ArrayList<String> s1=new ArrayList<String>();
		ArrayList<String> s2=new ArrayList<String>();
		try{
		Scanner in=new Scanner(input);
		while(in.hasNext()){
			s1.add(in.next());
		}
		}catch (FileNotFoundException ex) {
	        ex.printStackTrace();  
		}
		int count=0;
		int size=s1.size();
	    while(count<size){
	    	String m=s1.get(count);
	        int N=m.length();
	        int end=m.charAt(N-1);
	        int start=m.charAt(0);
	        int i=0,j=N-1;
	        while(!((end>=48&&end<=57)||(end>=65&&end<=90)||(end>=97&&end<=122))){
	        	j--;
	        	if(j<0)break;
	        	end=m.charAt(j);
	        	}
	        while(!((start>=48&&start<=57)||(start>=65&&start<=90)||(start>=97&&start<=122))){
	        	i++;
	        	if(i>N-1)break;
	        	start=m.charAt(i);
	        	}
	        count++;
	        if(i<=j){
	        	m=m.substring(i,j+1);
	        	String[] aux=m.split("[^A-Za-z0-9]");
	        	for(int k=0;k<aux.length;++k){
	        		if(aux[k].length()>0)s2.add(aux[k].toLowerCase());
	        		}
	        	
	        	}
	        }
		return s2;
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
		// TODO Write body!
		Iterator<Frequency> iter=frequencies.iterator();
		int itemcount=0,twogramcount=0,palindromecount=0;
		while(iter.hasNext()){
			Frequency f=(Frequency)iter.next();
			String s=f.getText();
			int N=s.length();
			int i=50-N;
			System.out.printf(s);
			for(int j=0;j<i;++j){
				System.out.printf(" ");
			}
			if(s.contains(" ")){
				twogramcount++;
				palindromecount++;
			}
			else if(s!="total"){
				itemcount++;
				palindromecount++;
			}
			System.out.println(f.getFrequency());
			}
		if(itemcount>=0&&twogramcount==0){
			System.out.println("Total item count: "+frequencies.get(frequencies.size()-1).getFrequency());
		    System.out.println("Unique item count: "+itemcount);
		}
		else if(twogramcount>=0&&itemcount==0){
			System.out.println("Total two-gram count: "+frequencies.get(frequencies.size()-1).getFrequency());
		    System.out.println("Unique two-gram count: "+twogramcount);
		}
		else{
			System.out.println("Total palidrome count: "+frequencies.get(frequencies.size()-1).getFrequency());
			System.out.println("Unique palidrome count: "+palindromecount);
			}
		}
	}
