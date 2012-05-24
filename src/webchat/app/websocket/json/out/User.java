package websocket.json.out;

import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class User {
	public String type;
	public Boolean init;
	public Map<Integer,UserData> data = new HashMap<Integer,UserData>();
	public Map<Integer,String> actions = new HashMap <Integer,String>();
	
	public User(){
		type = "user";
	}
	
	
	public static JsonNode geninitUser(int userid){
		String json = "", action = "create";
		try {
			User user = new User();
			user.init = true;
			for (Iterator<models.User> uit = models.User.find.all().iterator(); uit.hasNext();){
				models.User muser = uit.next();
				
				UserData udata = new UserData();
				udata.email = muser.email;
				udata.lastname = muser.lastname;
				udata.username = muser.username;
				udata.prename = muser.firstname;
				udata.online = muser.online;
				udata.modified = new Date();
			
				for (Iterator<models.Groups> itgroup = muser.groups.iterator(); itgroup.hasNext();){
					udata.groups.add(itgroup.next().id);
				}
				
				user.data.put(muser.id, udata);
				user.actions.put(muser.id, action);
			}	
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions", "*.groups");
			json = aser.exclude("*.class").serialize(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}
	
	public static JsonNode genUserchanged(int userid, String action){
		String json = "";
		try {
			User user = new User();
			user.init = false;
		
			models.User muser = models.User.find.byId(userid);
			
			UserData udata = new UserData();
			udata.email = muser.email;
			udata.lastname = muser.lastname;
			udata.username = muser.username;
			udata.prename = muser.firstname;
			udata.online = muser.online;
			udata.modified = new Date();
		
			for (Iterator<models.Groups> itgroup = muser.groups.iterator(); itgroup.hasNext();){
				udata.groups.add(itgroup.next().id);
			}
			
			user.data.put(muser.id, udata);
			user.actions.put(muser.id, action);
		
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions", "*.groups");
			json = aser.exclude("*.class").serialize(user);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}
}