/**
 * 
 */
package calculate;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author CHazyhabiT
 *
 */
public class Token {
	
	public static void main(String[] args) throws Exception{
		String test = "#<title>#Donald 3\\\\include\\\\server\\\\port\\\\win3";
		ArrayList<String> al = tokenize(test, new Stopword().hashSet());
		for(String w : al)
			System.out.println(w);
			
	}
	
	

	public static ArrayList<String> tokenize(String lineText, HashSet<String> stopword){
		ArrayList<String> tokenList = new ArrayList<String>();
		String tempToken;
//		String[] temp = lineText.split("[\\+]");
//		String[] temp = lineText.split("[^A-Za-z0-9]");
		String[] temp = lineText.split("[^A-Za-z]");
//		String[] temp = lineText.split("[ ,.?!\"]");
    	for(int i=0;i<temp.length;i++){
    		tempToken = temp[i];
    		// a - z 97 - 122
    		// A - Z 65 - 90
    		// 0 - 9 48 - 57

    		while(tempToken.length()!=0){
    			char tC = tempToken.charAt(0);
    			if((tC>=48&&tC<=57)||(tC>=65&&tC<=90)||(tC>=97&&tC<=122)){
    				break;
    				 
    			}else{
    				if(tempToken.length()==1){
    					tempToken = "";

    				}else{
    					tempToken = tempToken.substring(1,tempToken.length());
    					 
    				} 
    			}
    		}
    		 
    		for(int j=tempToken.length()-1;j>=0;j--){
    			char tC = tempToken.charAt(j);
    			if((tC>=48&&tC<=57)||(tC>=65&&tC<=90)||(tC>=97&&tC<=122)){
    				tempToken = tempToken.substring(0,j+1);
    				break;
    				 
    			}
    		}

    		if(tempToken.length()>0&&!stopword.contains(tempToken)){
    			tokenList.add(tempToken.toLowerCase());
    			 
    		}	 
    	}
		
    	return tokenList;
		
		
		
	}
	
	
}
