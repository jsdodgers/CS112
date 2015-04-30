package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONInserter {

	public static int insertTermJSON(File file){

		int count=0;
		try {

			try {
				BufferedReader reader=new BufferedReader(new FileReader(file));
				String string;
				while((string=reader.readLine())!=null){
					JSONTokener tokener=new JSONTokener(string);
					JSONObject obj=new JSONObject(tokener);
					XTParser.parseTerm(obj);
					count++;
				}
				reader.close();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int insertURLJSON(File file){
		
		
		int count=0;
		try {

			try {
				BufferedReader reader=new BufferedReader(new FileReader(file));
				String string;
				while((string=reader.readLine())!=null){
					JSONTokener tokener=new JSONTokener(string);
					JSONObject obj=new JSONObject(tokener);
					XTParser.parseURL(obj);
					count++;
				}
				reader.close();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

}
