package json_models;

import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Ping {
	public final String type;
	public final String[] data;
	
	public Ping(){
		this.type = "ping";
		this.data = null;
	}

	public static String genPing(){
		String json = "";
		try{
			
			Ping p = new Ping();
			JSONSerializer sser = new JSONSerializer();
			json = sser.exclude("*.class").serialize(p);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return json;
	}

}

