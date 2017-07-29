
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * Taiwan superlotto638 369 (威力彩)
 * 
 * version: July 30, 2017 00: 42 PM
 * Last revision: July 30, 2017 01: 16 PM
 */

public class Latest_Lottery_superlotto638_Parser 
{
	// Taiwan Lottery URL (superlotto638)
	private String url = "http://www.taiwanlottery.com.tw/lotto/superlotto638/history.aspx";
	// 
	private String ID_number;		// ID
	private String date_str;		// Date
	private int number_array[];		// Number 
	private int bonus_array[];		// Bouns
	private int unit_array[];		// Units
	// Json output
	private JSONObject obj;
	
	public Latest_Lottery_superlotto638_Parser() throws Exception
	{		
		Document doc = Jsoup.connect(url).get();				
		Elements links = doc.select("#SuperLotto638Control_history1_dlQuery");						
		// ID
		ID_number = links.select("#SuperLotto638Control_history1_dlQuery_DrawTerm_0").text();						
		// Date
		date_str = links.select("#SuperLotto638Control_history1_dlQuery_Date_0").text();
		// Number
		number_array = new int[7];
			// First Area 
			number_array[0] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo1_0").text());
			number_array[1] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo2_0").text());
			number_array[2] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo3_0").text());
			number_array[3] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo4_0").text());
			number_array[4] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo5_0").text());
			number_array[5] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo6_0").text());
			// Second Area 
			number_array[6] = Integer.parseInt(links.select("#SuperLotto638Control_history1_dlQuery_SNo7_0").text());		
		// bonus
		bonus_array = new int[10];		 
		bonus_array[0] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategA4_0").text()));
		bonus_array[1] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategB4_0").text()));
		bonus_array[2] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategC4_0").text()));
		bonus_array[3] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategD4_0").text()));
		bonus_array[4] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategE4_0").text()));
		bonus_array[5] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategF4_0").text()));
		bonus_array[6] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategG4_0").text()));
		bonus_array[7] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategH4_0").text()));
		bonus_array[8] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategJ4_0").text()));
		bonus_array[9] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategI4_0").text()));
		// Unit
		unit_array = new int[10];
		unit_array[0] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategA3_0").text()));
		unit_array[1] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategB3_0").text()));
		unit_array[2] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategC3_0").text()));
		unit_array[3] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategD3_0").text()));
		unit_array[4] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategE3_0").text()));
		unit_array[5] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategF3_0").text()));
		unit_array[6] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategG3_0").text()));
		unit_array[7] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategH3_0").text()));
		unit_array[8] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategJ3_0").text()));
		unit_array[9] = Integer.parseInt(Remove_tag(links.select("#SuperLotto638Control_history1_dlQuery_SL638_CategI3_0").text()));
		
		Output_JSON();
		System.out.println(obj);
	}
	
	private void Output_JSON()
	{
		obj = new JSONObject();
		obj.put("Title", "SuperLotto638");
		obj.put("ID", ID_number);
		obj.put("Date", date_str);
		
		JSONArray list_number = new JSONArray();
		JSONArray list_bonus = new JSONArray();
		JSONArray list_unit = new JSONArray();
		for(int i=0; i<number_array.length; i++)
		{
			list_number.add(number_array[i]);
		}
		for(int i=0; i<bonus_array.length; i++)
		{
			list_bonus.add(bonus_array[i]);
			list_unit.add(unit_array[i]);			
		}
		
		obj.put("Number", list_number);
		obj.put("bouns", list_bonus);
		obj.put("Unit", list_unit);				
	}
	
	private String Remove_tag(String input_str)
	{
		String new_str = "";		
		
		if(input_str.contains(",")){
			new_str = input_str.replace(",", ""); 
		}else{
			new_str = input_str;
		}
		
		return new_str;
	}
	
	public static void main(String[] args)
	{
		try {
			Latest_Lottery_superlotto638_Parser superlotto638 = new Latest_Lottery_superlotto638_Parser ();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
