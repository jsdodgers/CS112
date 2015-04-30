/**
 * 
 */
package calculate;

/**
 * @author CHazyhabiT
 *
 */
public class TFIDF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public static Double comTFIDF(int tf, int df, int N){
		if(tf<=0) return 0.0;
		
		return Math.log10(1+tf)*Math.log10(N/df);

	}

}
