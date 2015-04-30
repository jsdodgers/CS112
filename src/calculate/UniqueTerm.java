/**
 * 
 */
package calculate;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * @author CHazyhabiT
 *
 */
public class UniqueTerm {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		UniqueTerm ut = new UniqueTerm();
		String pathTerm = "./IndexOne/index23.txt";
		String pathPage = "./IndexTwo/IndexTwo.txt";
		long a=System.currentTimeMillis();
		
		System.out.println(ut.NumOfUniqueTerms(pathTerm));
		long b=System.currentTimeMillis();
		System.out.println(ut.NumOfUniqueTerms(pathPage));
		long c=System.currentTimeMillis();
		System.out.println("time consuming : "+(b-a)/1000f+" seconds ");
		System.out.println("time consuming : "+(c-b)/1000f+" seconds ");

	}
	
	
	public int NumOfUniqueTerms(String path) throws Exception{
		int num = 0;
		try{
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String lineText;
			while((lineText=br.readLine())!=null)
				num++;
			br.close();

			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		return num;
	}

}
