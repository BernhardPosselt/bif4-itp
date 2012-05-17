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
	
	
	public static JsonNode genUser(int userid){
		String json = "";
		try {
			User user = new User();
			user.init = true;
			models.User muser =  models.User.find.byId(userid);
			
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