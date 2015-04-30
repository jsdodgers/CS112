/**
 * 
 */
package webcrawler;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author xuke
 *
 */
public class Controller {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Needed parameters: ");
			System.out.println("\t rootFolder (it will contain intermediate crawl data)");
			System.out.println("\t numberOfCralwers (number of concurrent threads)");
			return;
		}

		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */
		String crawlStorageFolder = args[0];

		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling.
		 */
		int numberOfCrawlers = Integer.parseInt(args[1]);

		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * Be polite: Make sure that we don't send more than 1 request per
		 * second (1000 milliseconds between requests).
		 */
		config.setPolitenessDelay(500);

		/*
		 * You can set the maximum crawl depth here. The default value is -1 for
		 * unlimited depth
		 */
		config.setMaxDepthOfCrawling(10);

		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(-1);

		/*
		 * Do you need to set a proxy? If so, you can use:
		 * config.setProxyHost("proxyserver.example.com");
		 * config.setProxyPort(8080);
		 * 
		 * If your proxy also needs authentication:
		 * config.setProxyUsername(username); config.getProxyPassword(password);
		 */
		config.setUserAgentString("UCI Inf141-CS121 crawler 84311865 54689185 38006614");
		/*
		 * This config parameter can be used to set your crawl to be resumable
		 * (meaning that you can resume the crawl from a previously
		 * interrupted/crashed crawl). Note: if you enable resuming feature and
		 * want to start a fresh crawl, you need to delete the contents of
		 * rootFolder manually.
		 */
		config.setResumableCrawling(false);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		controller.addSeed("http://www.ics.uci.edu/");
//		controller.addSeed("http://honors.ics.uci.edu/",2);
//		controller.addSeed("http://sdcl.ics.uci.edu/",3);
//		controller.addSeed("http://asterix.ics.uci.edu/",4);
//		controller.addSeed("http://www.ics.uci.edu/~lopes/",5);
		double start=System.currentTimeMillis();
		controller.start(XTCrawler.class, numberOfCrawlers);
		double end=System.currentTimeMillis();
		double runningTime=(end-start-30000.0)/1000.0;

		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		long totalLinks = 0;
		long totalTextSize = 0;
		int totalProcessedPages = 0;
		int maxLength=0;
		String maxLengthURL=new String();
		for (Object localData : crawlersLocalData) {
			CrawlStat stat = (CrawlStat) localData;
			totalLinks += stat.getTotalLinks();
			totalTextSize += stat.getTotalTextSize();
			totalProcessedPages += stat.getTotalProcessedPages();
			if(stat.getLength()>maxLength){
				maxLength=stat.getLength();
				maxLengthURL=stat.getMaxLengthURL();		
			}
		}
		WriteIntoFile.WriteResult("Aggregated Statistics:");
		WriteIntoFile.WriteResult("   Processed Pages: " + totalProcessedPages);
		WriteIntoFile.WriteResult("   Total Text Size: " + totalTextSize);
		WriteIntoFile.WriteResult("   Total Links found: " + totalLinks);
		WriteIntoFile.WriteResult("   Max Text Size: " + maxLength);
		WriteIntoFile.WriteResult("   Max Text Size URL: " + maxLengthURL);
		WriteIntoFile.WriteResult("   Total Running Time: " + runningTime);
	}
}
