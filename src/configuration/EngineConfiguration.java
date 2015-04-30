/**
 * 
 */
package configuration;

/**
 * @author CHazyhabiT
 *
 */
public class EngineConfiguration {
	// number of pages
		private static int nPages = 0;
		// number of overall unique terms
		public static final int nTokens = 100000;
		// number of unique terms in one page
		public static final int nTerms = 50000;
		
		// directory 
		public static final String sourceDir = "./SourceDoc/";
		public static final String intermediateDir = "./Intermediate/";
		public static final String indexDir = "./FinalIndex/";
		public static final String resultDir= "./Result/";
		
		public static final String subIndexDir = intermediateDir+"/subIndex/";
		public static final String subFileDir = intermediateDir+"/subFile/";
		
		// path
		public static final String textPath = sourceDir +"Text.txt";
//		public static final String textPath = sourceDir +"text_new.txt";
		public static final String stopwordPath= sourceDir +"stopword.txt";
		public static final String finalTermIndexPath = indexDir +"termIndex.txt";
		public static final String finalPageIndexPath = indexDir +"pageIndex.txt";
		
		public static void updateNPages(int count){
			nPages += count;
			
		}
		
		public static int getNPages(){
			return nPages;
		}
		

}
