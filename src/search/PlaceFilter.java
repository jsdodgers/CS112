package search;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceFilter {

	public static boolean adjacent(DocScore docScore){

		final int THRESHOLD=(int)(docScore.getQuery().size()*0.7)+1;
		int count=0;
		int range=2*docScore.getQuery().size();
		if(docScore.getQuery().size()==1)return true;
		
		try {

			ArrayList<JSONObject> terms=new ArrayList<JSONObject>();
			ArrayList<ArrayList<Integer>> placesOfTerms=new ArrayList<ArrayList<Integer>>();
			for(int a=0;a<docScore.getQuery().size();a++){
				System.out.println(a);
				String term=docScore.getQuery().get(a);
				JSONObject obj1=Posting.extractPosting(term);
				terms.add(obj1);
			}
			for(int i=0;i<terms.size();i++){
				JSONArray array1=terms.get(i).getJSONArray("termfreq");

				for(int j=0;j<array1.length();j++){
					JSONObject obj2=array1.getJSONObject(j);
					if(obj2.getInt("docID")==docScore.getDocID()){
						JSONArray array2=obj2.getJSONArray("places");
						ArrayList<Integer> places =new ArrayList<Integer>();
						for(int k=0;k<array2.length();k++){
							places.add(array2.getInt(k));					
						}
						array2=null;
						placesOfTerms.add(places);
						places.clear();
						break;
					}
				}
				array1=null;
			}
			terms.clear();
			int start=-1;
			int min=Integer.MAX_VALUE;
			for(int i=0;i<placesOfTerms.size();i++){
				if(placesOfTerms.get(i).size()<min){
					min=placesOfTerms.get(i).size();
					start=i;
				}
			}

			ArrayList<Integer> base=placesOfTerms.get(start);
			
			for(int i=0;i<base.size();i++){
				int basePlace=base.get(i);
				for(int j=0;j<placesOfTerms.size();j++){
					if(j!=start){
						for(int k=0;k<placesOfTerms.get(j).size();k++){
							int place=placesOfTerms.get(j).get(k);
							if(Math.abs(place-basePlace)<=2*range){
								count++;
								break;
							}
						}
					}
					if(count>=THRESHOLD){
						break;
					}
				}
			}
			base.clear();
			placesOfTerms.clear();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count>=THRESHOLD;

	}

}
