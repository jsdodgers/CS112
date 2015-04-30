/**
 * 
 */
package search;

/**
 * @author CHazyhabiT
 *
 */
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONObject;

import parser.Mysql;

public class Query {

	public static void main(String[] args) throws Exception{
		Mysql.establishConnection();
		startQuery();

	}

	public static void startQuery() throws Exception{
		ArrayList<String> query = new ArrayList<String>();
		query.add("mondego");
//		query.add("machine learning");
//		query.add("software engineering");
//		query.add("security");
//		query.add("student affairs");
//		query.add("graduate courses");
//		query.add("Crista Lopes");
//		query.add("REST");
//		query.add("computer games");
//		query.add("information retrieval");

		
		for(String str : query){
			SearchWeb sw = new SearchWeb();
			System.out.println("Query Term: "+str+" ========================");
			LinkedList<String> list=sw.searchQuery(str);
			ResultGenerator.generateResult(list);

		}
//		long a = System.currentTimeMillis();
//		SearchWeb sw = new SearchWeb();
//		String userQuery = sw.inputQuery();
//		LinkedList<String> list=sw.searchQuery(userQuery);
//		ResultGenerator.generateResult(list);	
//		long b = System.currentTimeMillis();
//		System.out.println("time consuming : " + (b - a) / 1000f + " seconds ");
//		
		
		

	}

}
