package controllers;


import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.*;
import play.libs.Json;
import play.mvc.*;
import websocket.json.out.*;
import websocket.json.in.*;


public class JsonTestController extends Controller {

	
	public static Result genAuth(){
		String json = "";	
		try{
			JsonNode inmessage = buildinmessage();
			Message m = new Message();	
			m.init = true;
			MessageData md = new MessageData();
			InMessage im = new InMessage();
			im = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class);
			md.date = new Date();
			md.message = im.data.message;
			md.modified = new Date();
			md.type = im.data.type;
			md.user_id = 1;
			List<Integer> messageid = new ArrayList<Integer>();
			messageid.add(1);
			messageid.add(2);	
			Map<Integer,MessageData> mdata = new HashMap<Integer, MessageData>();
			for (Iterator<Integer> iterator = messageid.iterator(); iterator.hasNext();){
				int value = iterator.next();
				mdata.put(value, md);
				m.actions.put(value,"create");
			}
			
			for (Iterator<Integer> iterator = im.data.channels.iterator(); iterator.hasNext();){
				m.data.put(iterator.next(), mdata);
			}	
			JSONSerializer aser = new JSONSerializer().include("*.data", "*.actions");
			json = aser.exclude("*.class").serialize(m);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return ok(Json.parse(json));
	}
	
	public static Result genMessage(){
		JsonNode json = null;
		try{
			json = Message.genMessage(buildinmessage(), 2);
		}
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return ok(json);
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
		channel.add(1);
		channel.add(2);
		injson.put("type", "message");
		dat.put("message", "Hello Chris!");
		dat.put("type", "text");
		dat.putArray("channels").addAll(channel);
		data.putAll(dat);
		injson.putObject("data").putAll(data);
		return injson;
	}

	
	

}
