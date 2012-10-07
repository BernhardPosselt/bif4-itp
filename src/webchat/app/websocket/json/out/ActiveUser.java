package websocket.json.out;

import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import websocket.message.IOutMessage;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class ActiveUser extends IOutMessage  {
	public String type;
	public String action;
	public ActiveUserData data = new ActiveUserData();
	
	public ActiveUser(){
		this.type = "activeuser";
	}
	
	/*public static JsonNode genActiveUser(int userid){
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
	}*/
}
