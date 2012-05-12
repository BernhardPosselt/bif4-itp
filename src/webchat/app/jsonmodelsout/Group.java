package jsonmodelsout;

import java.util.Iterator;

import jsonmodelsin.InMessage;

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
		/*try{
			for (Iterator iterator = Groups.find)
			ObjectNode data = jnode.objectNode();
			JsonNode gdata = data.objectNode();
			GroupData gd = new GroupData();
			
			gd = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class); //Deserialize inmessage to Inmessage.class
			MessageData md = new MessageData();
			md.message = im.data.message;
			md.type = im.data.type;	
			md.user_id = userid;
			JSONSerializer aser = new JSONSerializer().include("*.channel"); //Serialize MessageData
			json = aser.exclude("*.class").serialize(md);
			jnode.put("type", im.type);	//put type to jnode
			mdata = Json.parse(json);	//serialized MessageData put to JsonNode
			channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class)); //put MessageData Node as Object to a Message
			for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator.hasNext();){
				data.putObject(String.valueOf(iterator.next())).putAll(channel);		
			}
			jnode.putObject("data").putAll(data); //put the DataObject Node to the jnode
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}*/
		return jnode;
	}
}
