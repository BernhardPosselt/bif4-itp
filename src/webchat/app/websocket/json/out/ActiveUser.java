package websocket.json.out;

import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class ActiveUser {
	public String type;
	public Boolean init;
	public Map<Integer,String> actions = new HashMap<Integer,String>();
	public ActiveUserData data;
	
	public ActiveUser(){
		this.type = "activeuser";
		this.init = true;
		this.data = new ActiveUserData();
	}
	
	public static JsonNode genActiveUser(int userid){
		String json = "";
		try {

			ActiveUser acuser = new ActiveUser();
			acuser.actions.put(userid, "create");
			acuser.data.id = userid;
			JSONSerializer acuserser = new JSONSerializer().include("*.actions");
			json = acuserser.exclude("*.class").serialize(acuser);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}
}
