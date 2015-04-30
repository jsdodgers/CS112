package webcrawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.poi.util.SystemOutLogger;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler{
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if(filters.matcher(href).matches())
        	return false;
        else if (href.contains("archive.ics.uci.edu")||href.contains("calendar.ics.uci.edu")||href.contains("?"))
        	return false;
        return href.contains("ics.uci.edu");
        //return href.contains("ics.uci.edu");
    }

	@Override
	public void visit(Page page) {
		System.out.println(Thread.currentThread().getName()+" Visited: " + page.getWebURL().getURL());
		int docid = page.getWebURL().getDocid();
	    String url = page.getWebURL().getURL();
	    String domain = page.getWebURL().getDomain();
	    String path = page.getWebURL().getPath();
	    String subDomain = page.getWebURL().getSubDomain();
	    String parentUrl = page.getWebURL().getParentUrl();
	    String anchor = page.getWebURL().getAnchor();
	    	

		try{
			WriteIntoFile.WriteURL(""+url);
			WriteIntoFile.WriteSubdomain(""+subDomain);
		}catch(IOException e1){
			System.out.println("writing file error!");
			e1.printStackTrace();
		}

		if (page.getParseData() instanceof HtmlParseData) {
			
			HtmlParseData parseData = (HtmlParseData) page.getParseData();
			
			String text=parseData.getText();
			String title=parseData.getTitle();
			Set<WebURL> links = parseData.getOutgoingUrls();
			int length=text.length();
			try{
				WriteIntoFile.WriteText("#<start>#"+docid);
				WriteIntoFile.WriteText("#<url>#"+url);
				for (WebURL s : links) {
				    WriteIntoFile.WriteText(s);
				}
				WriteIntoFile.WriteText("#<anchor>#"+anchor);
				WriteIntoFile.WriteText("#<title>#"+title+"\n");
				WriteIntoFile.WriteText(text);
				WriteIntoFile.WriteText("#<end>#"+docid+"\n");
				WriteIntoFile.WriteLength(Integer.toString(length)+"        "+url);
				WriteIntoFile.WritePage(Integer.toString(docid), text);
			}catch(IOException e2){
				System.out.println("writing file error!");
				e2.printStackTrace();
			}
		}
	}
}

