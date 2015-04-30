package parser;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

public class XTParser {

	public static void parseTerm(JSONObject obj){

		try {
			try {
				String sql="INSERT INTO terms(term,json)VALUES(?,?)";
				PreparedStatement pState=Mysql.getConnection().prepareStatement(sql);
				pState.setString(1, obj.getString("term"));
				pState.setString(2, obj.toString());
				pState.execute();
				//System.out.println("JSON of term "+obj.getString("term")+" is inserted!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parseURL(JSONObject obj){

		try {
			try {
				String sql="INSERT INTO urls(docID,json)VALUES(?,?)";
				PreparedStatement pState=Mysql.getConnection().prepareStatement(sql);
				pState.setInt(1, obj.getInt("docID"));
				pState.setString(2, obj.toString());
				pState.execute();
				//System.out.println("JSON of URL "+obj.getString("URL")+" is inserted!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
