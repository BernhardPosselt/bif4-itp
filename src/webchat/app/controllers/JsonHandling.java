package controllers;



import java.util.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;



import jsonmodelsin.InMessage;
import jsonmodelsin.InMessageData;
import jsonmodelsout.Auth;
import jsonmodelsout.AuthData;
import jsonmodelsout.Message;
import jsonmodelsout.MessageData;







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
		JsonNode inmessage = buildinmessage();
		InMessage im = new InMessage();
		ObjectNode jnode = Json.newObject();
		ObjectNode data = jnode.objectNode();
		ObjectNode channel = data.objectNode();
		JsonNode mdata = channel.objectNode();
		try{
			im = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class);
			MessageData m = new MessageData();
			m.message = im.data.message;
			m.type = im.data.type;
			JSONSerializer aser = new JSONSerializer().include("*.channel");
			json = aser.exclude("*.class").serialize(m);
			jnode.put("type", im.type);
			mdata = Json.parse(json);
			int messageid = 3;
			channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class));
			for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator.hasNext();){
				data.putObject(String.valueOf(iterator.next())).putAll(channel);		
			}
			jnode.putObject("data").putAll(data);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return ok(jnode);
	}
	
	public static JsonNode buildinmessage()
	{
		ObjectNode injson = Json.newObject();
		ObjectNode data = injson.objectNode();
		ObjectNode dat = Json.newObject();
		ArrayNode channel = data.arrayNode();
		channel.add(0);
		channel.add(1);
		injson.put("type", "message");
		dat.put("message", "Hello Chris!");
		dat.put("type", "text");
		dat.putArray("channels").addAll(channel);
		data.putAll(dat);
		injson.putObject("data").putAll(data);
		return injson;
	}

	
	

}
