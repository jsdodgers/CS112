// Justin Saletta	- 38006614
// Daniel Yang		- 84311865
// Connor Richards	- 54689185

package webcrawler;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 
 *
 */
public class Controller {
	public static void main(String[] args) throws Exception {
		

		
		// crawlStorageFolder is a folder where intermediate crawl data is stored.
		String crawlStorageFolder = "./CrawlOut";

		int numberOfCrawlers = 30;

		CrawlConfig config = new CrawlConfig();

		config.setMaxDepthOfCrawling(-1);
		config.setMaxPagesToFetch(-1);

		
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setResumableCrawling(true);
		config.setIncludeBinaryContentInCrawling(false);
		config.setPolitenessDelay(500);

	   	config.setUserAgentString("UCI Inf141-CS121 crawler 84311865 54689185 38006614");

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		controller.addSeed("http://www.ics.uci.edu/");
		long start=System.currentTimeMillis();
		controller.start(Crawler.class, numberOfCrawlers);
		System.out.println("Crawler Finished");
		long end=System.currentTimeMillis();
		long runningTime=(end-start)/1000;

		
		System.out.println("Aggregated Statistics:");
		System.out.println("   Total Running Time: " + runningTime);
	}
}
