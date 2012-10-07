package websocket.json.out;

import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import play.libs.Json;
import websocket.message.IOutMessage;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Group extends IOutMessage {
	public String type;
	public GroupData data = new GroupData();
	public String action;
	
	public Group(){
		this.type = "group";
	}
	
	public static JsonNode geninitGroup(){
		String json = "";
		Group group = new Group();
	
		try{
			for (Iterator<Groups> iterator = Groups.find.all().iterator(); iterator.hasNext();)
			{
				models.Groups groups= new models.Groups();
				groups = iterator.next();
				GroupData gdata = new GroupData();
				gdata.name = groups.name;
				group.action = "create";
				group.data = gdata;
				
			}
			JSONSerializer gser = new JSONSerializer().include("*.actions", "*.data");
			json = gser.exclude("*.class").serialize(group);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
}
