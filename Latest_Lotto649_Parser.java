
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * Taiwan Lotto 649 (大樂透)
 * 
 * version: June 05, 2013	03:30 PM
 * Last revision: July 30, 2017 00: 42 PM
 */

/*
 * Jar
 * jsoup-1.10.1.jar 
 * json-simple-1.1.1
 */

public class Latest_Lotto649_Parser 
{
	// Taiwan Lottery URL
	private String url = "http://www.taiwanlottery.com.tw/lotto/Lotto649/history.aspx";
	// 
	private String ID_number;
	private String date_str;
	private int number_array[];
	private int bonus_array[];
	private int unit_array[];
	// Json output
	private JSONObject obj;
	
	public Latest_Lotto649_Parser() throws Exception
	{
		Document doc = Jsoup.connect(url).get();				
		Elements links = doc.select("#Lotto649Control_history_dlQuery");					
		
		// ID
		ID_number = links.select("#Lotto649Control_history_dlQuery_L649_DrawTerm_0").text();						
		// Date
		date_str = links.select("#Lotto649Control_history_dlQuery_L649_DDate_0").text();
		// �j�p����
		number_array = new int[7];
		number_array[0] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No1_0").text());
		number_array[1] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No2_0").text());
		number_array[2] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No3_0").text());
		number_array[3] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No4_0").text());
		number_array[4] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No5_0").text());
		number_array[5] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_No6_0").text());
		number_array[6] = Integer.parseInt(links.select("#Lotto649Control_history_dlQuery_SNo_0").text());
		// �������t
		bonus_array = new int[8];
		bonus_array[0] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_L649_CategA4_0").text()));
		bonus_array[1] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label7_0").text()));
		bonus_array[2] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label8_0").text()));
		bonus_array[3] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label9_0").text()));
		bonus_array[4] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label10_0").text()));
		bonus_array[5] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label11_0").text()));
		bonus_array[6] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label12_0").text()));
		bonus_array[7] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label13_0").text()));
		// �����`��
		unit_array = new int[8];
		unit_array[0] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_L649_CategA3_0").text()));
		unit_array[1] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_L649_CategB3_0").text()));
		unit_array[2] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_L649_CategC3_0").text()));
		unit_array[3] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label2_0").text()));
		unit_array[4] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label3_0").text()));
		unit_array[5] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label4_0").text()));
		unit_array[6] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label5_0").text()));
		unit_array[7] = Integer.parseInt(Remove_tag(links.select("#Lotto649Control_history_dlQuery_Label6_0").text()));
		
		Output_JSON();
		System.out.println(obj);
	}
	
	private void Output_JSON()
	{
		obj = new JSONObject();
		obj.put("Title", "Taiwan Lottery 649");
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
			Latest_Lotto649_Parser LP = new Latest_Lotto649_Parser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
