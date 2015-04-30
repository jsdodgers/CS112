/**
 * 
 */
package search;

/**
 * @author CHazyhabiT
 *
 */
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;

import parser.Mysql;

public class Posting {

	

	public static JSONObject extractPosting(String term){

		JSONObject obj=null;
		try {
			try {
				String sql="select json from terms where term=?";
				PreparedStatement pState;
				pState = Mysql.getConnection().prepareStatement(sql);
				pState.setString(1, term);
				ResultSet rs=pState.executeQuery();
				String s=null;
				while(rs.next()){
					s=rs.getString("json");
				}
				if(s==null) return obj;
				JSONTokener tokener=new JSONTokener(s);
				obj=new JSONObject(tokener);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
		
	}

	public static JSONObject extractDocInfo(int docID) throws Exception {
		
		JSONObject obj=null;
		try {
			try {
				String sql="select json from urls where docID=?";
				PreparedStatement pState;
				pState = Mysql.getConnection().prepareStatement(sql);
				pState.setInt(1, docID);
				ResultSet rs=pState.executeQuery();
				String s=null;
				while(rs.next()){
					s=rs.getString("json");
				}
				JSONTokener tokener=new JSONTokener(s);
				obj=new JSONObject(tokener);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;

	}

}
