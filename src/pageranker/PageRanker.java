package pageranker;

import indexbuilder.XTPage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class PageRanker {

	public final int ITERATION_TIMES;
	public HashMap<Integer,XTPage> pageMap;

	public PageRanker(int iterationTimes){
		ITERATION_TIMES=iterationTimes;
		pageMap=new HashMap<Integer,XTPage>(80000);
	}



	public LinkedList<XTPage> rankPage(LinkedList<XTPage> pages){
		Iterator<XTPage> iter=pages.iterator();
		while(iter.hasNext()){
			XTPage temp=iter.next();
			pageMap.put(temp.getID(), temp);
		}
		for(int i=0;i<ITERATION_TIMES;i++){
			int size=pages.size();
			for(int j=0;j<size;j++){
				XTPage page=pages.removeFirst();
				double pageRank=0;
				Iterator<Integer> iterID=page.getParents().iterator();
				while(iterID.hasNext()){
					int docID=iterID.next();
					XTPage parent=pageMap.get(docID);
//					if(parent==null) continue;
//					if(parent.getChildrenNum()==0){
//						parent.increaseChildrenNum();
//					}
					pageRank+=parent.getPageRank()/parent.getChildrenNum();
				}

				pageRank=0.15+0.85*(pageRank);

				//System.out.println(pageRank);
				page.setPageRank(pageRank);
				pageMap.put(page.getID(), page);
				pages.add(page);
			}
		}
		return pages;
	}

}
