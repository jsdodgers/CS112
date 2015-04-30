package search;

import indexbuilder.WriteIntoFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class ResultGenerator {
	
	public static void generateResult(LinkedList<String> urls){
		
		int size=urls.size()-1;
		String query=urls.removeFirst();
		String htmlHead="<h3>"+query+":"+"</h3>";
		for(int i=0;i<size;i++){
			String url=urls.removeFirst();
			String htmlLink="<a href=\""+url+"\">"+url+"</a>";
			urls.add(htmlLink);
		}
		
		try {
			WriteIntoFile.WriteResult("<html>");
			WriteIntoFile.WriteResult("<body>");	
			WriteIntoFile.WriteResult("\n");
			WriteIntoFile.WriteResult(htmlHead);
			Iterator<String> iter=urls.iterator();
			while(iter.hasNext()){
				WriteIntoFile.WriteResult(iter.next());
				WriteIntoFile.WriteResult("<br/>");
			}
			WriteIntoFile.WriteResult("\n");
			WriteIntoFile.WriteResult("</body>");
			WriteIntoFile.WriteResult("</html>");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
