package websocket.json.out;

import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import play.libs.Json;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Group {
	public String type;
	public Map<Integer,GroupData> data = new HashMap<Integer,GroupData>();
	public Boolean init;
	public Map<Integer,String> actions = new HashMap<Integer,String>();
	
	public Group(){
		this.type = "group";
	}
	
	public static JsonNode geninitGroup(){
		String json = "",action = "create";
		Group group = new Group();
		group.init = true;
		try{
			for (Iterator<Groups> iterator = Groups.find.all().iterator(); iterator.hasNext();)
			{
				models.Groups groups= new models.Groups();
				groups = iterator.next();
				GroupData gdata = new GroupData();
				gdata.modified = groups.modified;
				gdata.name = groups.name;
				group.actions.put(groups.id, action);
				group.data.put(groups.id, gdata);
				
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
