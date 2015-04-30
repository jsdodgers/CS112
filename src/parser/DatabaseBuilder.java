package parser;

import java.io.File;

import configuration.EngineConfiguration;
import search.Posting;




public class DatabaseBuilder {
	
	public static void main(String[] args){
		Mysql.establishConnection();
		File termFile=new File(EngineConfiguration.finalTermIndexPath);
		File urlFile=new File(EngineConfiguration.finalPageIndexPath);
		int termsCounter=JSONInserter.insertTermJSON(termFile);
		int URLsCounter=JSONInserter.insertURLJSON(urlFile);
		System.out.println("Total terms: "+termsCounter);
		System.out.println("Total urls: "+URLsCounter);
		
	}

}
