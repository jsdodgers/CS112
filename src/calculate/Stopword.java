/**
 * 
 */
package calculate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author CHazyhabiT
 * 
 */
public class Stopword {
	HashSet<String> hs = new HashSet<String>();
	
	public static void main(String[] args) throws IOException{
		Stopword st = new Stopword();
		String test = "adfsfasf";
		
		System.out.println(st.hashSet().contains(test));
	}

	public HashSet<String> hashSet() throws IOException {
		try {
			String filePath = "./stopword.txt";
			String encoding = "GBK";
			String temp;

			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				FileInputStream inputStream = new FileInputStream(file);
				InputStreamReader read = new InputStreamReader(inputStream,
						encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((temp = bufferedReader.readLine()) != null) {
					hs.add(temp);
				}
				bufferedReader.close();

			}
			
			Iterator<String> iter = hs.iterator();
			int i = 0;
			while(iter.hasNext()){
//				System.out.println(iter.next());
				iter.next();
				i++;
				
			}
			System.out.println(i);
			
				
			

		} catch (IOException e) {
			e.printStackTrace();

		}
		return hs;
	}

}
