package com.taiwan;

/*
 * Taiwan Lottery 649 parser (All)
 * 
 * version: March 17, 2019 02:49 PM
 * Last revision: March 17, 2019 09:50 PM
 * 
 */

/*
 * Reference
 * https://blog.csdn.net/sbsujjbcy/article/details/44853967
 * https://www.jianshu.com/p/5277cea9d3ac
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TaiwanLottery649Parser_All {

	String pageBase = "https://en.lottolyzer.com/history/taiwan/lotto-649/page/";
	String pageURL;
	private int totalPageNum = 25;
	private int countPerPaee = 50;

	// Element
	Document doc;
	Elements tbodytr;
		// data
		private String drawStr;
		private String dateStr;		Date theDate;
		private List<Integer> numList = new ArrayList<Integer> (); 
		// Json
		private JSONObject obj;
	// Mongodb
	private String host = "localhost";    
	private int port = 27017;    
	private String dbName = "Lottery";
	private MongoClient mongoClient;
    private DB db;
    private DBCollection collection;
    // MongoDB
    private String collectionName = "649";
		
	public TaiwanLottery649Parser_All() throws Exception {
		
		// Mongo
		mongoClient = new MongoClient(host, port);
		db = mongoClient.getDB(dbName);
		collection = db.getCollection(collectionName);
		
		// data parsing
		for(int pageIndex=totalPageNum; pageIndex>=0; pageIndex--) {
			pageURL = pageBase + String.valueOf(pageIndex) + "/per-page/"+String.valueOf(countPerPaee)+"/summary";
			//System.out.println(pageURL);
			
			doc = Jsoup.connect(pageURL).get();
			
			tbodytr = doc.select("tbody").select("tr");	
			for (int tdIndex=tbodytr.size()-1; tdIndex>=0; tdIndex--)
			{
				numList.clear();
				obj = new JSONObject();
				
				// drew
				getDrew(tdIndex);
				// Date
				getDate(tdIndex);
				// Number
				getNum(tdIndex);
				// JSON generation
				JSONgeneration();
				// str to timestamp
				StrToTimestamp(dateStr);
				// db storage
				SaveDB();
				
				System.out.println(drawStr+"	"+dateStr+"	"+numList.size()+"	"+obj);
			}
			
		}
		
		mongoClient.close();
	}
	
	private void getDrew(int index) {
		List<Node> notetd = tbodytr.get(index).childNodes();
		Element aaele = (Element) notetd.get(1);
		drawStr = aaele.text();        	        	        	        
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
			numList.add(Integer.parseInt(temp[numIndex]));
		}
	}
	
	private void JSONgeneration() {
		JSONArray numArray = new JSONArray();
		
		for(int numIndex=0; numIndex<numList.size(); numIndex++) {
			numArray.put(numList.get(numIndex));
		}
		
		//obj.put("Draw", drawStr);
		//obj.put("Date", dateStr);
		obj.put("Number", numArray);
	}

	private void StrToTimestamp(String input) {
		String temp[];
		temp = input.split("-");
		String year_str = temp[0];
		String month_str = temp[1];
		String day_str = temp[2];
		int year = Integer.parseInt(year_str);
		int month = Integer.parseInt(month_str);
		int day = Integer.parseInt(day_str);
		
		Calendar myCal = Calendar.getInstance();
		myCal.set(Calendar.YEAR, year);
		myCal.set(Calendar.MONTH, month-1);
		myCal.set(Calendar.DAY_OF_MONTH, day);
		myCal.set(Calendar.HOUR_OF_DAY, 20);
		myCal.set(Calendar.MINUTE, 30);
		myCal.set(Calendar.SECOND, 0);
		theDate = myCal.getTime();		
		
		//System.out.println(theDate);
	}
	
	private void SaveDB() {
		List<DBObject> documents = new ArrayList<>();
		
		BasicDBObject document = new BasicDBObject();
		document.put("Draw", drawStr);
		document.put("Date", theDate);
		document.put("Number", numList);
		
		// Insert
		collection.insert(document);
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
