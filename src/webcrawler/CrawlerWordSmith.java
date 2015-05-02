// Connor Richards	- 54689185
// Justin Saletta	- 38006614
// Daniel Yang		- 84311865

package webcrawler;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CrawlerWordSmith {

	public static void main(String[] args) throws FileNotFoundException {
		checkFrequencyOfSubdomains();
		checkFrequencyOfWords();
	}
	
	public static void checkFrequencyOfWords() throws FileNotFoundException {
		final File folder = new File("./CrawlOut/Pages");
		Collection<String> files = listFilesForFolder(folder);
		List<Frequency> frequencies = new ArrayList<Frequency>();
		int i = 0;
		int longest = 0;
		String url = "";
		Scanner lengths = new Scanner(new File("./CrawlOut/Length.txt"));
		for (String s : files) {
			i++;
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
		}
		try {
			WriteIntoFile.WriteWordSmith(frequencies, url, longest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void checkFrequencyOfSubdomains() throws FileNotFoundException {
		File subdomainFile = new File("./CrawlOut/subdomain.txt");
		List<Frequency> frequencies = WordFrequencyCounter.computeSubdomainFrequencies(Utilities.tokenizeFileSubdomain(subdomainFile), null);
		try {
			WriteIntoFile.WriteSubdomainFrequencies(frequencies);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


