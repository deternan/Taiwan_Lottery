package com.taiwan;

/*
 * Taiwan Lottery 649 parser (latest)
 * 
 * version: March 11, 2019 10:01 PM
 * Last revision: March 17, 2019 12:10 PM
 * 
 */

/*
 * Reference
 * https://blog.csdn.net/sbsujjbcy/article/details/44853967
 * https://www.jianshu.com/p/5277cea9d3ac
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TaiwanLottery649Parser_Latest {

	String pageURL = "https://en.lottolyzer.com/home/taiwan/lotto-649";
	
	public TaiwanLottery649Parser_Latest() throws Exception {
		
		// get latest num
		getBallNum();
		
	}
	
	private void getBallNum() throws Exception
	{
		String html = Jsoup.connect(pageURL).get().html();
        
        Document doc = Jsoup.connect(pageURL).get();
        Elements spanId = doc.select("span");
		
        // latest date
        System.out.println(doc.getElementById("latest_date").text());
        // Number
        
        for(int ballNum=0; ballNum < doc.getElementsByClass("ball").size(); ballNum++)
        {
        	System.out.println(doc.getElementsByClass("ball").get(ballNum).attr("title"));
        	
        	//System.out.println(doc.getElementsByClass("ball").attr("title"));
        }
	}
	
	public static void main(String[] args) {
		try {
			TaiwanLottery649Parser_Latest jp = new TaiwanLottery649Parser_Latest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
