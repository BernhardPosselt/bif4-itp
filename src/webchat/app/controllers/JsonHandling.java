package controllers;



import java.util.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;



import json_models.Auth;
import json_models.AuthData;
import json_models.Message;
import json_models.MessageData;







import flexjson.*;
import play.libs.Json;
import play.mvc.*;


public class JsonHandling extends Controller {
	
	
	
	
	public static Result genAuth(){
		String json = "";	
		try{
			
			Auth a = new Auth();
			AuthData ad = new AuthData();
			UUID idOne = UUID.randomUUID();
			ad.sessionid = idOne.toString();
			a.data = ad;
			JSONSerializer aser = new JSONSerializer();
			json = aser.exclude("*.class").serialize(a);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return ok(Json.parse(json));
	}
	
	public static Result genMessage(){
		String json = "";	
		ObjectNode injson = Json.newObject();
		ObjectNode data = injson.objectNode();
		
		ObjectNode dat = Json.newObject();
		ArrayNode channel = data.arrayNode();
		channel.add(1);
		channel.add(2);
		injson.put("type", "message");
		dat.put("message", "Hello Chris!");
		dat.put("type", "text");
		dat.putArray("channels").addAll(channel);
		data.putAll(dat);
		injson.putObject("data").putAll(data);
		Object [] test = null;
		try{
			Message m  = new Message();
			MessageData md = new MessageData();
			md.message = injson.findPath("message").asText();
			md.type = injson.findPath("type").asText();
		
			test = injson.findValues("channels").toArray() ;
			
			//md.channels.add(Integer.parseInt(test.toString()));
			md.channels.add(2);
			m.data = md;
			JSONSerializer aser = new JSONSerializer();
			json = aser.exclude("*.class").serialize(m);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return ok(test[0].toString());
	}

	
	

}
