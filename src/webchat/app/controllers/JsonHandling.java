package controllers;



import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;



import jsonmodelsin.InMessage;
import jsonmodelsin.InMessageData;
import jsonmodelsout.Auth;
import jsonmodelsout.AuthData;
import jsonmodelsout.GroupData;
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
			UUID idOne = UUID.randomUUID();
			
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
	
	public static Result genGroup(){
		int userid = 1;
		String json = "";
		ObjectNode jnode = Json.newObject(); //initalize Objectnodes
		ObjectNode data = jnode.objectNode();
		try{
			for (Iterator<Groups> iterator = Groups.getUserGroups(userid).iterator(); iterator.hasNext();)
			{
				Groups group = new Groups();
				group = iterator.next();
				
				JsonNode gdata = data.objectNode();
				GroupData gd = new GroupData();
				gd.name = group.name;
				gd.modified = group.modified;
				JSONSerializer gser = new JSONSerializer().include();
				json = gser.exclude("*.class").serialize(gd);
				gdata = Json.parse(json);
				data.putObject(String.valueOf(group.id)).putAll(Json.fromJson(gdata,ObjectNode.class));
				
			}
			jnode.put("type", "group");
			jnode.putObject("data").putAll(data); //put the DataObject Node to the jnode
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
