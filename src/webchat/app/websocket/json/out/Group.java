package websocket.json.out;

import java.util.Iterator;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import play.libs.Json;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Group {
	public String type;
	public GroupData data;
	
	public Group(){
		this.type = "group";
		this.data = new GroupData();
	}
	
	public JsonNode genGroup(int userid){
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
		return jnode;
	}
}
