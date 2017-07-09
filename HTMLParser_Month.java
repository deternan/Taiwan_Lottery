
/*
 * 103000081期（開獎日期2014年9月26日）起新增柒獎獎項
 * Jar
 * jsoup-1.10.1.jar
 * 
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLParser_Month 
{			
	private Vector ID_vec;	
	private Vector Year_vec;
	private Vector Month_vec;
	private Vector Day_vec;
	private Vector Ball_vec;
	private Vector Special_Ball_vec;
	private Vector Awards_vec;
	
	private int Open_Number = 0;
	
	// Remove Tag
	private String Date_Tag = "<td rowspan=\"2\" align=\"center\"> <span style=\"font-size:18px; color:#fb4202; font-weight:bold;\">";
	private String Ball_Tag = "class=\"history_ball_link\"";
	private String Special_Tag = "<td align=\"center\" style=\"color:#005aff; font-size:48px; font-weight:bolder;\">";
	private String Award_Tag = "$";
	// SQL
	private String update_str;	
		// MySQL
		private String read_sql = "jdbc:mysql://IP/DBname";		// IP
		private String user = "";								// Account
		private String pass = "";								// Password
		private String DB = "";									// DB name
		private	Connection con;
		private Statement stat = null;
		private PreparedStatement stmt;
		private ResultSet rs = null;
		private PreparedStatement preparedStmt;
		
	public HTMLParser_Month() throws Exception
	{
		// Initial
		ID_vec = new Vector();
		//Date_vec = new Vector();
		Year_vec = new Vector();
		Month_vec = new Vector();
		Day_vec = new Vector();
		Ball_vec = new Vector();
		Special_Ball_vec = new Vector();
		Awards_vec = new Vector();
						
		// MySQL
		con = DriverManager.getConnection(read_sql,user,pass);
		stat = con.createStatement();
		
		// 
		//All_Data_updated();
		// Regular Month Updated
		Regular_updated();
		
		/*
		// All Data
		//Document doc = Jsoup.connect("http://lotto.auzonet.com/biglotto/list_2015.html").get();
		Document doc = Jsoup.connect("http://lotto.auzonet.com/biglotto/list_2009_2.html").get();
		
		// 開獎 (次數/月)
		Elements newsHeadlines_0 = doc.select("tbody tr td[height=20]");
		Month_Open_Count(newsHeadlines_0);		
		// 開獎號碼大小順序
		//Elements newsHeadlines_1 = doc.select("ul li a[class=\"history_ball_link\"]");
		Elements newsHeadlines_1 = doc.select("ul li[class=\"ball_blue\"] a");
		Balls_Parser(newsHeadlines_1);
		// 開獎日期
		Elements newsHeadlines_21 = doc.select("td[rowspan=\"2\"]");
		ID_Data_Parser(newsHeadlines_21);
		// 期數
		//Elements newsHeadlines_22 = doc.select("span");		
		// 特別號碼
		Elements newsHeadlines_3 = doc.select("td[style=color:#005aff; font-size:48px; font-weight:bolder;]");
		Special_Parser(newsHeadlines_3);							
		// 獎金 
		Elements newsHeadlines_4 = doc.select("td[height=\"20\"]");
		Awards_Parser(newsHeadlines_4);
		
		//System.out.println(newsHeadlines_4);
		
		// SQL Update
		SQL_Update();
			System.out.println("Updated Finished");
		//Show_All();
		// Clean
		Clean();
		*/
		con.close();
	}
	
	private void Regular_updated() throws Exception
	{
		// Time Today
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int month = localDate.getMonthValue();
		
		String Web_str;
		Web_str = "http://lotto.auzonet.com/biglotto/list_"+year+"_"+month+".html";
		
		Document doc = Jsoup.connect(Web_str).get();
		
		// 開獎 (次數/月)
		Elements newsHeadlines_0 = doc.select("tbody tr td[height=20]");
		Month_Open_Count(newsHeadlines_0);		
		// 開獎號碼大小順序
		//Elements newsHeadlines_1 = doc.select("ul li a[class=\"history_ball_link\"]");
		Elements newsHeadlines_1 = doc.select("ul li[class=\"ball_blue\"] a");
		Balls_Parser(newsHeadlines_1);
		// 開獎日期
		Elements newsHeadlines_21 = doc.select("td[rowspan=\"2\"]");
		ID_Data_Parser(newsHeadlines_21);
		// 期數
		//Elements newsHeadlines_22 = doc.select("span");		
		// 特別號碼
		Elements newsHeadlines_3 = doc.select("td[style=color:#005aff; font-size:48px; font-weight:bolder;]");
		Special_Parser(newsHeadlines_3);							
		// 獎金 
		Elements newsHeadlines_4 = doc.select("td[height=\"20\"]");
		Awards_Parser(newsHeadlines_4);
		
		// SQL Update
		SQL_Update();
		
			System.out.println(year+"/"+month+" Updated Finished");
		//Show_All();
		// Clean
		Clean();
	}
	
	private void All_Data_updated() throws Exception
	{
		// http://lotto.auzonet.com/biglotto/list_2009_2.html
		String Web_str;
		
		for(int i=2004; i<=2014; i++)
		{
			for(int j=1; j<=12; j++)
			{
				Web_str = "http://lotto.auzonet.com/biglotto/list_"+i+"_"+j+".html";
				
				Document doc = Jsoup.connect(Web_str).get();
				
				// 開獎 (次數/月)
				Elements newsHeadlines_0 = doc.select("tbody tr td[height=20]");
				Month_Open_Count(newsHeadlines_0);		
				// 開獎號碼大小順序
				//Elements newsHeadlines_1 = doc.select("ul li a[class=\"history_ball_link\"]");
				Elements newsHeadlines_1 = doc.select("ul li[class=\"ball_blue\"] a");
				Balls_Parser(newsHeadlines_1);
				// 開獎日期
				Elements newsHeadlines_21 = doc.select("td[rowspan=\"2\"]");
				ID_Data_Parser(newsHeadlines_21);
				// 期數
				//Elements newsHeadlines_22 = doc.select("span");		
				// 特別號碼
				Elements newsHeadlines_3 = doc.select("td[style=color:#005aff; font-size:48px; font-weight:bolder;]");
				Special_Parser(newsHeadlines_3);							
				// 獎金 
				Elements newsHeadlines_4 = doc.select("td[height=\"20\"]");
				Awards_Parser(newsHeadlines_4);
	
				// SQL Update
				SQL_Update();
					System.out.println(i+"/"+j+" Updated Finished");
				//Show_All();
				// Clean
				Clean();
				
				Web_str = "";
			}
		}
	}
	
	private void Month_Open_Count(Elements newsHeadlines)
	{
		for(int i=0; i<newsHeadlines.size(); i++)
		{
			if(newsHeadlines.get(i).toString().contains("獎金分配")){
				Open_Number++;
			}			
		}		
	}
	
	private void Balls_Parser(Elements newsHeadlines)
	{
		String temp_ball;
		
		for(int i=0; i<newsHeadlines.size(); i++)
		{
			if(newsHeadlines.get(i).toString().contains(Ball_Tag)){
				temp_ball = newsHeadlines.get(i).toString().substring(newsHeadlines.get(i).toString().indexOf(">")+1, newsHeadlines.get(i).toString().indexOf("</"));
				Ball_vec.add(Integer.parseInt(temp_ball.trim()));				
			}
		}
	}
	
	private void ID_Data_Parser(Elements newsHeadlines)
	{		
		String temp_ID;
		String temp_Date;
		
		// ID
		for(int i=0; i<newsHeadlines.size(); i++)
		{
			// ID
			if(newsHeadlines.get(i).toString().contains(Date_Tag)){
				temp_ID = newsHeadlines.get(i).toString().substring(Date_Tag.length(), Date_Tag.length()+9);
				ID_vec.add(temp_ID.trim());
				//System.out.println(temp_ID);
			}
			// Date
			if(newsHeadlines.get(i).toString().contains("<br><br>")){
				temp_Date = newsHeadlines.get(i).toString().trim().substring(newsHeadlines.get(i).toString().indexOf("<br><br>")+"<br><br>".length(), 
																		newsHeadlines.get(i).toString().indexOf("<br>("));
				Date_Break(temp_Date.trim());
				//System.out.println(temp_Date.trim());
			}
		}
	}
	
	private void Date_Break(String Date_str)
	{
		int Year = Integer.parseInt(Date_str.substring(0, 4));
		int Month = Integer.parseInt(Date_str.substring(5, 7));
		int Day = Integer.parseInt(Date_str.substring(Date_str.lastIndexOf("-")+1, Date_str.length()));
		
		Year_vec.add(Year);
		Month_vec.add(Month);
		Day_vec.add(Day);
		//System.out.println(Year+"	"+Month+"	"+Day);
	}
	
	private void Special_Parser(Elements newsHeadlines)
	{
		int Spe_ball;
		
		// Special
		for(int i=0; i<newsHeadlines.size(); i++)
		{
			if(newsHeadlines.get(i).toString().contains(Special_Tag)){
				Spe_ball = Integer.parseInt(newsHeadlines.get(i).toString().substring(Special_Tag.length(),newsHeadlines.get(i).toString().indexOf("</td>")));
				Special_Ball_vec.add(Spe_ball);
				//System.out.println(Spe_ball);
			}
		}			
	}
	
	private void Awards_Parser(Elements newsHeadlines)
	{
		String temp_award;
		//String award_int;
		
		for(int i=0; i<newsHeadlines.size(); i++)
		{
			if(newsHeadlines.get(i).toString().contains(Award_Tag)){				
				temp_award = newsHeadlines.get(i).toString().substring(newsHeadlines.get(i).toString().indexOf(">")+1, newsHeadlines.get(i).toString().indexOf("</"));				
				Awards_vec.add(Awards_Translation(temp_award.trim()));				
				//System.out.println(count+"	"+Awards_Translation(temp_award.trim()));
				//System.out.println(i+"	"+newsHeadlines.get(i).toString()+"	"+temp_award+"	"+Awards_Translation(temp_award.trim()));				
			}
		}
	}
	
	private int Awards_Translation(String _str)
	{
		int temp = 0;
		String temp_str;
		temp_str = _str.replace("$", "");
		temp_str = temp_str.replace(",", "");
		temp = Integer.parseInt(temp_str);
		
		return temp; 
	}
	
	private void Show_All()
	{
		int b1,b2,b3,b4,b5,b6,bs;
		int a1,a2,a3,a4,a5,a6,a7,a8;
		
		//for(int i=0; i<Open_Number; i++)
		{
			
			/*
			System.out.print(ID_vec.get(i)+" "+Year_vec.get(i)+"-"+Month_vec.get(i)+"-"+Day_vec.get(i)+"	");
			b1 = Integer.parseInt(Ball_vec.get((i*6)+0).toString());
			b2 = Integer.parseInt(Ball_vec.get((i*6)+1).toString());
			b3 = Integer.parseInt(Ball_vec.get((i*6)+2).toString());
			b4 = Integer.parseInt(Ball_vec.get((i*6)+3).toString());
			b5 = Integer.parseInt(Ball_vec.get((i*6)+4).toString());
			b6 = Integer.parseInt(Ball_vec.get((i*6)+5).toString());
			System.out.print(b1+","+b2+","+b3+","+b4+","+b5+","+b6+"  "+Special_Ball_vec.get(i)+"	");
			a1 = Integer.parseInt(Awards_vec.get((i*8)+0).toString());
			a2 = Integer.parseInt(Awards_vec.get((i*8)+1).toString());
			a3 = Integer.parseInt(Awards_vec.get((i*8)+2).toString());
			a4 = Integer.parseInt(Awards_vec.get((i*8)+3).toString());
			a5 = Integer.parseInt(Awards_vec.get((i*8)+4).toString());
			a6 = Integer.parseInt(Awards_vec.get((i*8)+5).toString());
			a7 = Integer.parseInt(Awards_vec.get((i*8)+6).toString());
			a8 = Integer.parseInt(Awards_vec.get((i*8)+7).toString());			
			System.out.print(a1+";"+a2+";"+a3+";"+a4+";"+a5+";"+a6+";"+a7+";"+a8);
			System.out.println();
			*/
		}
	}
	
	private void SQL_Update() throws Exception
	{
		int b1,b2,b3,b4,b5,b6,bs;
		int a1,a2,a3,a4,a5,a6,a7,a8;
	
		for(int i=0; i<Open_Number; i++)
		{
			b1 = Integer.parseInt(Ball_vec.get((i*6)+0).toString());
			b2 = Integer.parseInt(Ball_vec.get((i*6)+1).toString());
			b3 = Integer.parseInt(Ball_vec.get((i*6)+2).toString());
			b4 = Integer.parseInt(Ball_vec.get((i*6)+3).toString());
			b5 = Integer.parseInt(Ball_vec.get((i*6)+4).toString());
			b6 = Integer.parseInt(Ball_vec.get((i*6)+5).toString());
			
			a1 = Integer.parseInt(Awards_vec.get((i*8)+0).toString());
			a2 = Integer.parseInt(Awards_vec.get((i*8)+1).toString());
			a3 = Integer.parseInt(Awards_vec.get((i*8)+2).toString());
			a4 = Integer.parseInt(Awards_vec.get((i*8)+3).toString());
			a5 = Integer.parseInt(Awards_vec.get((i*8)+4).toString());
			a6 = Integer.parseInt(Awards_vec.get((i*8)+5).toString());
			a7 = Integer.parseInt(Awards_vec.get((i*8)+6).toString());
			a8 = Integer.parseInt(Awards_vec.get((i*8)+7).toString());	
			
			update_str = "INSERT INTO "+DB+" (ID,Year,Month,Day,Ball_1,Ball_2,Ball_3,Ball_4,Ball_5,Ball_6,Ball_S,First_prize,Second_prize,Third_prize,Forth_prize,Fifth_prize,Sixth_prize,Seventh_prize,General_prize) "
					+ "VALUES ('"+ID_vec.get(i)+"','"+Year_vec.get(i)+"','"+Month_vec.get(i)+"','"+Day_vec.get(i)+"','"+b1+"','"+b2+"','"+b3+"','"+b4+"','"+b5+"','"+b6+"','"+Special_Ball_vec.get(i)+"',"
							+ "'"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+a5+"','"+a6+"','"+a7+"','"+a8+"') ON DUPLICATE KEY UPDATE "
					+"ID='"+ID_vec.get(i)+"'";
			
			preparedStmt = con.prepareStatement(update_str);
			preparedStmt.executeUpdate();
			
			update_str = "";
		}
		
	}
	
	private void Clean()
	{
		Open_Number = 0;
		
		ID_vec.clear();
		Year_vec.clear();
		Month_vec.clear();
		Day_vec.clear();
		//Date_vec.clear();
		Ball_vec.clear();
		Special_Ball_vec.clear();
		Awards_vec.clear();
		
		update_str = "";
	}
	
	public static void main(String[] args)
	{
		try {
			HTMLParser_Month HP = new HTMLParser_Month();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
