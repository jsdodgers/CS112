package webcrawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler{
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	CrawlStat myCrawlStat;

	public Crawler() {
		myCrawlStat = new CrawlStat();
	}

	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !filters.matcher(href).matches() && href.
				contains("ics.uci.edu")&&!href.
				contains("archive.ics.uci.edu")&&!href.
				contains("calendar.ics.uci.edu")&&!href.
				contains("sli.ics.uci.edu/classes/2013s-77b?action=download&upname=jester-train.csv")&&!href.
				contains("http://sli.ics.uci.edu/classes/2013s-77b?action=download&upname=jester-test.csv")&&!href.
				contains("http://kdd.ics.uci.edu/databases/movies/data/") && !href.
				contains("?");
	}
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !filters.matcher(href).matches()
               && href.startsWith("http://www.ics.uci.edu/");
    }

	@Override
	public void visit(Page page) {
		System.out.println(Thread.currentThread().getName()+" Visited: " + page.getWebURL().getURL());
		myCrawlStat.incProcessedPages();
		String url=page.getWebURL().getURL();
		String subDomain=page.getWebURL().getSubDomain();
		String anchor=page.getWebURL().getAnchor();
	    
		int docID=page.getWebURL().getDocid();
		

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
			ArrayList<WebURL> outgoingURLs = new ArrayList();
			outgoingURLs.addAll(parseData.getOutgoingUrls());
			int length=text.length();
			try{
				WriteIntoFile.WriteText("#<start>#"+docID);
				WriteIntoFile.WriteText("#<url>#"+url);
				Iterator<WebURL> iter=outgoingURLs.iterator();
				while(iter.hasNext()){
					String outgoingURL=iter.next().getURL();
					WriteIntoFile.WriteText("#<outgoingurls>#"+outgoingURL);
				}
				WriteIntoFile.WriteText("#<anchor>#"+anchor);
				WriteIntoFile.WriteText("#<title>#"+title+"\n");
				WriteIntoFile.WriteText(text);
				WriteIntoFile.WriteText("#<end>#"+docID+"\n");
				WriteIntoFile.WriteLength(Integer.toString(length)+"        "+url);
				WriteIntoFile.WritePage(Integer.toString(docID), text);
			}catch(IOException e2){
				System.out.println("writing file error!");
				e2.printStackTrace();
			}
			if(myCrawlStat.getLength()<length){
				myCrawlStat.setLength(length);
				myCrawlStat.setMaxLengthURL(url);
			}
			List<WebURL> links = new ArrayList<WebURL>(); 
			links.addAll(parseData.getOutgoingUrls());
			myCrawlStat.incTotalLinks(links.size());
			try {
				myCrawlStat.incTotalTextSize(parseData.getText().getBytes("UTF-8").length);
			} catch (UnsupportedEncodingException ignored) {
				// Do nothing
			}
		}
	}

	// This function is called by controller to get the local data of this
	// crawler when job is finished
	@Override
	public Object getMyLocalData() {
		return myCrawlStat;
	}
}

