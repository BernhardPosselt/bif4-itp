package jsonmodelsout;


import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Auth {
	public final String type;
	public AuthData data;
	
	public Auth(){
		this.type = "auth";
		this.data = new AuthData();
	}
	
	public static JsonNode genAuth(String level,String msg){
		String json = "";	
		try{
			Auth a = new Auth();
			a.data.level = level;
			a.data.msg = msg;
			JSONSerializer aser = new JSONSerializer();
			json = aser.exclude("*.class").serialize(a);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
}
