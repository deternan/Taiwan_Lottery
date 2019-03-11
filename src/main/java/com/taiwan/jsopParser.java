package com.taiwan;

/*
 * Bobble Sort (Array)
 * 
 * version: March 11, 2019 10:01 PM
 * Last revision: March 11, 2019 11:26 PM
 * 
 */

/*
 * Reference
 * https://blog.csdn.net/sbsujjbcy/article/details/44853967
 * 
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
		
        String html = Jsoup.connect(pageURL).get().html();
        //System.out.println(html);
        
        Document doc = Jsoup.connect(pageURL).get();
        Elements spanId = doc.select("span");
		
        // latest date
        System.out.println(doc.getElementById("latest_date").text());
        
        
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
