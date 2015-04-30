/**
 * 
 */
package calculate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Pattern;
import java.util.ArrayList;

import configuration.EngineConfiguration;

/**
 * @author CHazyhabiT
 * 
 */

public class SplitFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		SplitFile sf = new SplitFile();
		sf.splitFile(EngineConfiguration.textPath);

	}

	public ArrayList<String> splitFile(String readPath) throws Exception {
		ArrayList<String> alFile = new ArrayList<String>();
		
		try {
			int fileCapacity = 5000;
//			int fileCapacity = 2;
			int docCount = 0;
			int fileCount = 1;
			String fileName = "sub_text_";
			

			// read file
			File fileRead = new File(readPath);
			FileReader fr = new FileReader(fileRead);
			BufferedReader br = new BufferedReader(fr);
			Pattern filters = Pattern.compile("#<.*>#.*");

			String writeDir = EngineConfiguration.subFileDir;
			File file = new File(writeDir);
			if (!file.exists()) {
				file.mkdirs();
				System.out.println("new fold created!");
			}

			FileWriter fw = new FileWriter(writeDir + fileName + fileCount, true);
			alFile.add(writeDir+fileName+fileCount);

			String lineText;
			while ((lineText = br.readLine()) != null) {

				if (filters.matcher(lineText).matches()&& lineText.contains("#<start>#")) {
					docCount++;
					if (docCount > fileCapacity) {
						fileCount++;
						docCount = 1;
						fw.close();
						fw = new FileWriter(writeDir + fileName + fileCount, true);
						alFile.add(writeDir+fileName+fileCount);

					}

				}
				fw.write(lineText);
				fw.write("\r");

			}
			br.close();
			fw.close();
			
			
		} catch (Exception e) {
			System.out.println("error in reading file");
			e.printStackTrace();
		}

		return alFile;
	}
}
