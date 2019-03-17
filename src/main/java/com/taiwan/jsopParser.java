package com.taiwan;

import java.io.IOException;

/*
 * Jsoup Prser
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

public class jsopParser {

	String pageURL = "https://en.lottolyzer.com/home/taiwan/lotto-649";
	
	public jsopParser() throws Exception {
		
		Prser_649();
		
        
	}
	
	private void Prser_649() throws Exception{
		
        // get Num
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
			jsopParser jp = new jsopParser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
