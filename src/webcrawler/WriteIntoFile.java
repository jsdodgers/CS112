// Connor Richards	- 54689185
// Daniel Yang		- 84311865
// Justin Saletta	- 38006614


package webcrawler;

import ir.assignments.two.a.Frequency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



public class WriteIntoFile {
	public static void WritePage(String id, String content, String URL) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut/Pages");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/"+id+".txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(content);
			fileWriter.write("\r");
			fileWriter.close();
			
			stb = new StringBuffer("./CrawlOut/PagesURL");

			file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/"+id+".txt");
			filePath = stb.toString();
			fileWriter = new FileWriter(filePath, true);
			fileWriter.write(URL);
			fileWriter.write("\r");
			fileWriter.close();
			

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();
		}
	}

	public static void WriteURL(String URL) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/URLs.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(URL);
			fileWriter.write("\r");
			fileWriter.close();

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
	public static void WriteSubdomain(String subdomain) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/subdomain.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(subdomain);
			fileWriter.write("\r");
			fileWriter.close();

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
	public static void WriteWordSmith(List<Frequency> frequencies, String longestURL, int longestWords) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/WordSmith.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, false);
			fileWriter.write("Longest Page: " + longestURL + " - " + longestWords + " Words\n\n\n\n");
			for (Frequency f : frequencies) {
				fileWriter.write(f.getText() + ":" + f.getFrequency() + "\n");
			}
//			fileWriter.write(length);
			fileWriter.write("\r");
			fileWriter.close();
			
			filePath = "./CrawlOut/CommonWords.txt";
			fileWriter = new FileWriter(filePath, false);
			for (int n=0;n<Math.min(500, frequencies.size());n++) {
				Frequency f = frequencies.get(n);
				fileWriter.write(f.getText() + ":" + f.getFrequency() + "\n");
			}
			fileWriter.write("\r");
			fileWriter.close();
			
			

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
	public static void WriteSubdomainFrequencies(List<Frequency> frequencies) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/Subdomains.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, false);
			//for (int n=0;n<Math.min(500, frequencies.size());n++) {
			for (Frequency f : frequencies) {
				fileWriter.write(f.getText() + ", " + f.getFrequency() + "\n");
			}
			fileWriter.write("\r");
			fileWriter.close();
		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
	public static void WriteLength(String length) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/Length.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(length);
			fileWriter.write("\r");
			fileWriter.close();

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
}
