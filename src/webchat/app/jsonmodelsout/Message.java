package jsonmodelsout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jsonmodelsin.InMessage;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

import models.Channel;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Message{
	public final String type;
	public MessageData data;
	
	public Message(){
		this.type = "message";
		this.data = new MessageData();
	}
	
	public static JsonNode genMessage(JsonNode inmessage)
	{	String json = "";
		int messageid = 0;
		InMessage im = new InMessage();  //InMessage Object 
		ObjectNode jnode = Json.newObject(); //initalize Objectnodes
		ObjectNode data = jnode.objectNode();
		ObjectNode channel = data.objectNode();
		JsonNode mdata = channel.objectNode();
		try{
			im = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class); //Deserialize inmessage to Inmessage.class
			MessageData m = new MessageData();
			m.message = im.data.message;
			m.type = im.data.type;	
			JSONSerializer aser = new JSONSerializer().include("*.channels"); //Serialize MessageData
			json = aser.exclude("*.class").serialize(m);
			jnode.put("type", im.type);	//put type to jnode
			mdata = Json.parse(json);	//serialized MessageData put to JsonNode
			channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class)); //put MessageData Node as Object to a Message
			for (Iterator<Integer> iterator = im.data.channels.iterator(); iterator.hasNext();){ 
				data.putObject(String.valueOf(iterator.next())).putAll(channel);	//put Message to every Channel from the inmessage	
			}
			jnode.putObject("data").putAll(data); //put the DataObject Node to the jnode
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
	
	public static JsonNode genmultipleMessage(JsonNode inmessage) //JsonNode-Generation for sending more messages at one time
	{	String json = "";
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
			JSONSerializer aser = new JSONSerializer().include("*.channels");
			json = aser.exclude("*.class").serialize(m);
			jnode.put("type", im.type);
			mdata = Json.parse(json);
			for (int messageid = 0; messageid <4; messageid ++)
			{
				channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class));
				for (Iterator<Integer> iterator = im.data.channels.iterator(); iterator.hasNext();){
					data.putObject(String.valueOf(iterator.next())).putAll(channel);		
				}
			}
			jnode.putObject("data").putAll(data);
		} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
}
