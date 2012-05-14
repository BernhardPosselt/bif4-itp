package websocket.json.out;

import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Status {
	public final String type;
	public StatusData data;
	
	public Status() {
		this.type = "status";
	}
	
	public static String genStatus(String level, String msg)
	{
		String json = "";
		try{
			
			Status s = new Status();
			s.data.level = level;
			s.data.msg = msg;
			JSONSerializer sser = new JSONSerializer();
			json = sser.exclude("*.class").serialize(s);
			
		 } catch (JSONException e) {	 
			 e.printStackTrace();
		 }
		return json;
	}

}

