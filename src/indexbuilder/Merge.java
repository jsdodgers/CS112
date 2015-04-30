package indexbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import configuration.EngineConfiguration;

public class Merge {
	
	public static String merge(ArrayList<File> files){
		int length=files.size();
		return mergeFiles(files,length);
		
	}
	

	private static String mergeFiles(ArrayList<File> files,int index){
		int length=files.size();
		if(length<=1)return EngineConfiguration.subIndexDir+"index"+index+".txt";
		int count=length/2;
		ArrayList<File> mergedFiles=new ArrayList<File>();
		for(int i=0;i<count;i++){
			try {
				merge(files.get(2*i),files.get(2*i+1),++index);
				File file=new File(EngineConfiguration.subIndexDir+"index"+index+".txt");
				mergedFiles.add(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(length%2!=0){
			mergedFiles.add(files.get(length-1));
		}
		return mergeFiles(mergedFiles,index);

	}
	

	private static void merge(File file1, File file2,int index) throws FileNotFoundException{

		try {
			try {
				BufferedReader reader1=new BufferedReader(new FileReader(file1));
				BufferedReader reader2=new BufferedReader(new FileReader(file2));
				String s1=reader1.readLine();
				String s2=reader2.readLine();
				while(s1!=null&&s2!=null){
					JSONObject obj1=new JSONObject(new JSONTokener(s1));
					JSONObject obj2=new JSONObject(new JSONTokener(s2));
//					String[] strings1=s1.split("\"");
					String term1=obj1.getString("term");
//					String[] strings2=s2.split("\"");
					String term2=obj2.getString("term");
					if(term1.compareTo(term2)>0){
						WriteIntoFile.WriteIndexOne(s2, index);
						s2=reader2.readLine();

					}else if(term1.compareTo(term2)<0){
						WriteIntoFile.WriteIndexOne(s1, index);
						s1=reader1.readLine();
					}else{
						JSONArray jArray=obj1.getJSONArray("termfreq");
						int length=jArray.length();
						for(int i=0;i<length;i++){
							JSONObject jObj=(JSONObject)jArray.get(i);
							obj2.getJSONArray("termfreq").put(jObj);
						}
						int newDFTitle=obj1.getInt("dfTitle")+obj2.getInt("dfTitle");
						int newDFAnchor=obj1.getInt("dfAnchor")+obj2.getInt("dfAnchor");
						int newDFBody=obj1.getInt("dfBody")+obj2.getInt("dfBody");
						obj2.put("dfTitle", newDFTitle).put("dfAnchor", newDFAnchor).put("dfBody", newDFBody);
//						JSONStringer js=new JSONStringer();
//						js.object().key(term2).value(obj2.getJSONObject(term2)).endObject();
						WriteIntoFile.WriteIndexOne(obj2.toString(), index);
						s1=reader1.readLine();
						s2=reader2.readLine();
					}
				}
				if(s1==null){
					while(s2!=null){
						WriteIntoFile.WriteIndexOne(s2, index);
						s2=reader2.readLine();
					}
				}
				if(s2==null){
					while(s1!=null){
						WriteIntoFile.WriteIndexOne(s1, index);
						s1=reader1.readLine();
					}
				}
				reader1.close();
				reader2.close();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
