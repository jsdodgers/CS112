package webcrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import edu.uci.ics.crawler4j.url.WebURL;


public class WriteIntoFile {
	public static void WritePage(String id, String content) throws IOException{
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
	public static void WriteText(String text) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut/text");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/Text.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(text);
			fileWriter.write("\r");
			fileWriter.close();

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
	public static void WriteText(WebURL text) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut/text");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/Text.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(text.toString());
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
	public static void WriteResult(String result) throws IOException{
		try{
			StringBuffer stb = new StringBuffer("./CrawlOut");

			File file = new File(stb.toString());
			if(!file.exists()){
				file.mkdirs();
				System.out.println("new folder created!");
			}
			stb.append("/Result.txt");
			String filePath = stb.toString();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(result);
			fileWriter.write("\r");
			fileWriter.close();

		}catch(IOException e){
			System.out.println("writing file error!");
			e.printStackTrace();

		}
	}
}
