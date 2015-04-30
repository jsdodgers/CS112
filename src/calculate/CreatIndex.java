/**
 * 
 */
package calculate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Collections;

import indexbuilder.XTTerm;
import indexbuilder.XTPage;
import indexbuilder.Merge;

import org.json.JSONTokener;
import org.json.JSONObject;
import org.json.JSONArray;

import configuration.EngineConfiguration;
import pageranker.PageRanker;

/**
 * @author CHazyhabiT
 * 
 */
public class CreatIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		long a=System.currentTimeMillis();
		CreatIndex ci = new CreatIndex();
		ci.createIndex(EngineConfiguration.textPath);
		long b=System.currentTimeMillis();
		System.out.println("time consuming : "+(b-a)/1000f+" seconds ");
		

	}

	public File[] findFile(String dir) {

		File file = new File(dir);
		
		return file.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File file,String name){
				return name.contains(".txt");
			}
		});
	}

	// should be split
	public void createIndex(String filePath) throws Exception {

		// number of pages
		int nPage = 70000;

		// get the stopword
		HashSet<String> stopword = new Stopword().hashSet();

		Pattern filters = Pattern.compile("#<.*>#.*");

		// store all the pages
		Hashtable<String, XTPage> htPage = new Hashtable<String, XTPage>(nPage);
		// split one big file into several files
		ArrayList<String> alFile = new SplitFile().splitFile(filePath);
		int fileCount = 0;

		// build raw index : tf & df
		for (String subFile : alFile) {
			// read file
			fileCount++;

			createSubIndex(subFile, htPage, stopword, filters, fileCount);

		}


		File[] indexFiles = findFile(EngineConfiguration.subIndexDir);
		ArrayList<File> indexFilesA = new ArrayList<File>();
		for (int i = 0; i < indexFiles.length; ++i) {
			System.out.println(indexFiles[i]);
			indexFilesA.add(indexFiles[i]);

		}
		String indexPath = Merge.merge(indexFilesA);
		termIndex(indexPath);

		
		// record outLinks, for PageRank
		pageIndex(filePath, htPage, filters);

		Set<Entry<String, XTPage>> entrySet = htPage.entrySet();
		Iterator<Entry<String, XTPage>> iterPage = entrySet.iterator();
		LinkedList<XTPage> pages = new LinkedList<XTPage>();
		XTPage page;
		while (iterPage.hasNext()) {
			page = iterPage.next().getValue();
			if(page==null){
				System.out.println("!!!!!");
				break;
			}
			pages.add(page);

		}
		PageRanker pr = new PageRanker(5);
		LinkedList<XTPage> newPages = pr.rankPage(pages);
		for(XTPage newPage : newPages){
			newPage.toJSON();
		}
		
		
		
		

	}

	private void createSubIndex(String filePath, Hashtable<String, XTPage> htPage, 
			HashSet<String> stopword, Pattern filters, int fileCount) throws Exception {

		try {
			int docID = 0;
			String url = "";
			String anchor;
			String title;
			int location = 0;
			int pageCount = 0;

			
			// number of overall unique terms
			int nTokens = EngineConfiguration.nTokens;
			// number of unique terms in one page
			int nTerms = EngineConfiguration.nTerms;

			// store all the terms in one subfile
			Hashtable<String, XTTerm> htTerm = new Hashtable<String, XTTerm>(nTokens);
			// store all the terms in one page
			HashSet<String> hsTerm = new HashSet<String>(nTerms);

			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String lineText;
			while ((lineText = br.readLine()) != null) {

				if (filters.matcher(lineText).matches()) {
					if (lineText.contains("#<start>#")) {
						++pageCount;

						docID = Integer.parseInt(lineText.substring(9));
						location = 0;
						hsTerm.clear();

					} else if (lineText.contains("#<url>#")) {
						url = lineText.substring(7);
						XTPage page = new XTPage(docID);
						page.setURL(url);
						htPage.put(url, page);

					} else if (lineText.contains("#<anchor>#")) {
						anchor = lineText.substring(10);
						XTPage page = htPage.get(url);
						page.setAnchor(anchor);
						htPage.put(url, page);

						HashSet<String> hsTemp = new HashSet<String>(30);
						ArrayList<String> tokens = Token.tokenize(anchor,
								stopword);
						for (String token : tokens) {
							if (!hsTemp.contains(token)) {
								hsTemp.add(token);
								XTTerm term;
								if (!htTerm.containsKey(token))
									term = new XTTerm(token);
								else
									term = htTerm.get(token);
								term.increaseTFAnchor(docID);
								term.increaseDFAnchor();
								htTerm.put(token, term);

							} else {
								XTTerm term = htTerm.get(token);
								term.increaseTFAnchor(docID);
								htTerm.put(token, term);
							}

						}

					} else if (lineText.contains("#<title>#")) {

						title = lineText.substring(9);
						XTPage page = htPage.get(url);
						page.setTitle(title);
						htPage.put(url, page);

						HashSet<String> hsTemp = new HashSet<String>(30);
						ArrayList<String> tokens = Token.tokenize(title, stopword);
						for (String token : tokens) {
							if (!hsTemp.contains(token)) {
								hsTemp.add(token);
								XTTerm term;
								if (!htTerm.containsKey(token))
									term = new XTTerm(token);
								else
									term = htTerm.get(token);
								term.increaseTFTitle(docID);
								term.increaseDFTitle();
								htTerm.put(token, term);

							} else {
								XTTerm term = htTerm.get(token);
								term.increaseTFTitle(docID);
								htTerm.put(token, term);
							}

						}

					} else if (lineText.contains("#<end>#")) {
						XTPage page = htPage.get(url);
						page.setLength(location + 1);
						htPage.put(url, page);

					} else {
					}

				} else {

					ArrayList<String> tokens = Token.tokenize(lineText,stopword);
					for (String token : tokens) {
						if (!htTerm.containsKey(token)) {
							hsTerm.add(token);

							XTTerm term = new XTTerm(token);
							term.update(docID, location);
							term.increaseDFBody();
							htTerm.put(token, term);

						} else {
							XTTerm term = htTerm.get(token);
							term.update(docID, location);
							if (!hsTerm.contains(token)) {
								hsTerm.add(token);
								term.increaseDFBody();
							}
							htTerm.put(token, term);
						}
						location++;

					}

				}

			}
			EngineConfiguration.updateNPages(pageCount);

			br.close();
			fr.close();

			Set<Entry<String, XTTerm>> entrySetT = htTerm.entrySet();
			Iterator<Entry<String, XTTerm>> iterTerm = entrySetT.iterator();
			ArrayList<XTTerm> alTerm = new ArrayList<XTTerm>();

			XTTerm term;
			int count = 0;
			while (iterTerm.hasNext()) {
				term = iterTerm.next().getValue();
				/*
				 * int df = term.getDF(); ArrayList<Integer> docIDs =
				 * term.getIDs(); double tfidf; for (int id : docIDs) { int tf =
				 * term.getTF(id); tfidf = TFIDF.comTFIDF(tf, df, nPage);
				 * term.setTFIDF(id, tfidf);
				 * 
				 * }
				 */
				alTerm.add(term);
				count++;

				// term.toJSON();

			}

			Collections.sort(alTerm);

			for (XTTerm x : alTerm) {

				x.toJSON(fileCount);
			}

			System.out.println("--" + count);

		} catch (Exception e) {
			System.out.println("error in reading file");
			e.printStackTrace();
		}

	}

	private void termIndex(String filePath) throws Exception {
		
		int nPage = EngineConfiguration.getNPages();
		try {
			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String finalIndex = EngineConfiguration.finalTermIndexPath;
			File create = new File(EngineConfiguration.indexDir);
			if(!create.exists()) create.mkdir();
			else file.delete();
			FileWriter fw = new FileWriter(finalIndex, true);

			String lineText;
			while ((lineText = br.readLine()) != null) {

				JSONTokener token = new JSONTokener(lineText);
				JSONObject obj = new JSONObject(token);

				int anchorDF = obj.getInt("dfAnchor");
				int titleDF = obj.getInt("dfTitle");
				int bodyDF = obj.getInt("dfBody");
				JSONArray arr = obj.getJSONArray("termfreq");
				int len = arr.length();
				for (int i = 0; i < len; ++i) {
					JSONObject term = arr.getJSONObject(i);
					int anchorTF = term.getInt("frequencyInAnchor");
					int titleTF = term.getInt("frequencyInTitle");
					int bodyTF = term.getInt("frequencyInBody");
			

					double tfidfAnchor = TFIDF.comTFIDF(anchorTF, anchorDF, nPage);
					double tfidfTitle = TFIDF.comTFIDF(titleTF, titleDF, nPage);
					double tfidfBody = TFIDF.comTFIDF(bodyTF, bodyDF, nPage);

//					double tfidf = tfidfAnchor * 2 + tfidfTitle * 4 + tfidfBody * 4;
					
					term.put("tfidfAnchor", tfidfAnchor);
					term.put("tfidfTitle", tfidfTitle);
					term.put("tfidfBody", tfidfBody);

				}

				String jsonString = obj.toString();
				fw.write(jsonString);
				fw.write("\r\n");

			}
			br.close();
			fr.close();
			fw.close();

		} catch (Exception e) {
			System.out.println("error in reading file");
			e.printStackTrace();
		}

	}

	public void pageIndex(String filePath, Hashtable<String, XTPage> htPage,
			Pattern filters) throws Exception {

		int docID = 0;
		String url = "";
		String outLinks;

		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String lineText;

		while ((lineText = br.readLine()) != null) {

			if (filters.matcher(lineText).matches()) {
				if (lineText.contains("#<start>#")) {

					docID = Integer.parseInt(lineText.substring(9));
					url = "";

				} else if (lineText.contains("#<url>#")) {
					url = lineText.substring(7);

				} else if (lineText.contains("#<outgoingurls>#")) {
					outLinks = lineText.substring(16);
					if (htPage.containsKey(outLinks)) {
						XTPage parent = htPage.get(url);
						if (parent == null)
							continue;

						parent.increaseChildrenNum();
						XTPage child = htPage.get(outLinks);
						child.addParent(docID);

					}

				}

			}

		}
		br.close();

	}

}
