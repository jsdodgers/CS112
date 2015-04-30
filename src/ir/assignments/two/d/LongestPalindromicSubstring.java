/**
 * 
 */
package ir.assignments.two.d;

import java.util.ArrayList;

/**
 * @author xuke
 *
 */
public class LongestPalindromicSubstring {
	public static ArrayList<String> longestPalindrome(String[] words,int[] index){
		ArrayList<String> palin=new ArrayList<String>();
		StringBuilder s=new StringBuilder();		
		int length=words.length;
		for(int i=0;i<length;++i){
			s.append(words[i]);
		}
		int N=s.length();
		boolean table[][]=new boolean[N][N];
		for(int i=0;i<N;++i){
			if(s.charAt(i)!=32){
				table[i][i]=true;
				int match1=BinarySearch.search(index,i);
				int match2=BinarySearch.search(index,i+1);
				int match3=BinarySearch.search(index,i+1-words[words.length-1].length());
				if(match1>=0&&(match2>=0||i==s.length()-1)){
					if(i==s.length()-1)palin.add(words[match3]);
					else palin.add(words[match1]);
					}
				}
			}
		for(int i=0;i<N-1;++i){
			if(s.charAt(i)==s.charAt(i+1)){
					table[i][i+1]=true;
					int match1=BinarySearch.search(index,i);
					int match2=BinarySearch.search(index,i+2);
					int match3=BinarySearch.search(index,i+2-words[words.length-1].length());
				    if(match1>=0&&(match2>=0||i+1==s.length()-1)){
				    	String aux=new String();
				    	if(i+1==s.length()-1){
				    		for(int count=match1;count<=match3;++count){
					    		aux=aux+words[count]+" ";
					    	}
					    	palin.add(aux);
				    	}
				    	else {
				    		for(int count=match1;count<match2;++count){
					    		aux=aux+words[count]+" ";
					    		}
					    		palin.add(aux);
					    		}
				    	}
				    }
			}		
		for(int len=3;len<=N;++len){
			for(int i=0;i<N-len+1;++i){
				int j=i+len-1;
				if(s.charAt(i)==s.charAt(j)&&table[i+1][j-1]){
						table[i][j]=true;
						int match1=BinarySearch.search(index,i);
						int match2=BinarySearch.search(index,j+1);
						int match3=BinarySearch.search(index,j+1-words[words.length-1].length());
					    if(match1>=0&&(match2>=0||j==s.length()-1)){
					    	String aux=new String();
					    	if(j==s.length()-1){
					    		for(int count=match1;count<=match3;++count){
						    		if(count!=match3)aux=aux+words[count]+" ";
						    		else aux=aux+words[count];
						    	}
						    	palin.add(aux);
					    	}
					    	else{
					    		for(int count=match1;count<match2;++count){
					    			if(count!=match2-1)aux=aux+words[count]+" ";
					    		    else aux=aux+words[count];
					    			}
					    		palin.add(aux);
					    		}
					    	}
					    }
					}
				}
		return palin;
	}
}
