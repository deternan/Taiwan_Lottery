package com.taiwan;

import java.util.ArrayList;
import java.util.List;

/*
 * Taiwan Lottery 649 parser (All)
 * 
 * version: March 17, 2019 02:49 PM
 * Last revision: March 17, 2019 02:49 PM
 * 
 */

/*
 * Reference
 * https://blog.csdn.net/sbsujjbcy/article/details/44853967
 * https://www.jianshu.com/p/5277cea9d3ac
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class TaiwanLottery649Parser_All {

	String pageBase = "https://en.lottolyzer.com/history/taiwan/lotto-649/page/";
	String pageURL;
	private int totalPageNum = 25;
	private int countPerPaee = 50;

	// Element
	Document doc;
	Elements tbodytr;
		// drew
		private String drewStr;
		private String dateStr;
		private List<Integer> list = new ArrayList<Integer> (); 
		
		
	public TaiwanLottery649Parser_All() throws Exception {
		
		for(int pageIndex=totalPageNum; pageIndex>=0; pageIndex--) {
			pageURL = pageBase + String.valueOf(pageIndex) + "/per-page/"+String.valueOf(countPerPaee)+"/summary";
			System.out.println(pageURL);
			
			doc = Jsoup.connect(pageURL).get();
			
			tbodytr = doc.select("tbody").select("tr");	
			for (int tdIndex=0; tdIndex<tbodytr.size(); tdIndex++)
			{
				list.clear();
				
				// drew
				getDrew(tdIndex);
				// Date
				getDate(tdIndex);
				// Number
				getNum(tdIndex);
				System.out.println(drewStr+"	"+dateStr+"	"+list.size());
			}
			
		}

	}
	
	private void getDrew(int index) {
		List<Node> notetd = tbodytr.get(index).childNodes();
		Element aaele = (Element) notetd.get(1);
		drewStr = aaele.text();        	        	        	        
	}
	
	private void getDate(int index) {
		List<Element> eletd = tbodytr.get(index).getElementsByClass("sum-p1");
		dateStr = eletd.get(0).text();
	}
	
	private void getNum(int index) {
		List<Element> eletd = tbodytr.get(index).getElementsByClass("sum-p1");
		String numStr = eletd.get(1).text();
		String temp[];
		temp = numStr.split(",");
		for(int numIndex=0; numIndex<temp.length; numIndex++) {
			list.add(Integer.parseInt(temp[numIndex]));
		}
		
	}
	
	public static void main(String[] args) {
		try {
			TaiwanLottery649Parser_All jp = new TaiwanLottery649Parser_All();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
