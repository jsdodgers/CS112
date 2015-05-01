package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler{
	static Collection<String> strings = null;
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf|ppx?t" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
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

	public static Collection<String> crawl(String seedURL) throws Exception {
		strings = new ArrayList<String>();
		String crawlStorageFolder = "./CrawlOut";

		int numberOfCrawlers = 1;

		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);

		config.setMaxDepthOfCrawling(-1);

		config.setMaxPagesToFetch(-1);
		
				
		config.setIncludeBinaryContentInCrawling(false);
	    
	    config.setResumableCrawling(false);

	    config.setPolitenessDelay(500);

 
	    config.setUserAgentString("UCI Inf141-CS121 crawler 84311865 54689185 38006614");

	
	    
	    PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		controller.addSeed(seedURL);
		controller.start(Crawler.class, numberOfCrawlers);
		return strings;
	}
	
	@Override
	public void visit(Page page) {
		System.out.println(Thread.currentThread().getName()+" Visited: " + page.getWebURL().getURL());
		int docid = page.getWebURL().getDocid();
	    String url = page.getWebURL().getURL();
	    String subDomain = page.getWebURL().getSubDomain();
	    	

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
			if (strings!=null) strings.add(text);
			int length=text.length();
			try{
				WriteIntoFile.WriteLength(Integer.toString(length) + "        " + url);
				WriteIntoFile.WritePage(Integer.toString(docid), text);
			}catch(IOException e2){
				System.out.println("writing file error!");
				e2.printStackTrace();
			}
		}
	}
}

