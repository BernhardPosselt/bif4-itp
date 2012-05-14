<<<<<<< HEAD
package jsonmodelsout;

import java.io.PrintStream;
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
	
	public static JsonNode genMessage(JsonNode inmessage, int userid)
	{	String json = "";
		int messageid = 5;
		InMessage im = new InMessage();  //InMessage Object 
		ObjectNode jnode = Json.newObject(); //initalize Objectnodes
		ObjectNode data = jnode.objectNode();
		ObjectNode channel = data.objectNode();
		JsonNode mdata = channel.objectNode();
		try{
			im = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class); //Deserialize inmessage to Inmessage.class
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
		}
		return jnode;
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
			MessageData md = new MessageData();
			md.message = im.data.message;
			md.type = im.data.type;
			JSONSerializer aser = new JSONSerializer().include("*.channel");
			json = aser.exclude("*.class").serialize(md);
			jnode.put("type", im.type);
			mdata = Json.parse(json);
			for (int messageid = 0; messageid <4; messageid ++)
			{
				channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class));
				for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator.hasNext();){
					data.putObject(String.valueOf(iterator.next())).putAll(channel);		
				}
			}
			jnode.putObject("data").putAll(data);
		} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return jnode;
	}
}
=======
package websocket.json.out;

import java.util.Iterator;

import websocket.json.in.InMessage;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

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
	
	public static JsonNode genMessage(JsonNode inmessage, int userid)
	{	String json = "";
		int messageid = 5;
		InMessage im = new InMessage();  //InMessage Object 
		ObjectNode jnode = Json.newObject(); //initalize Objectnodes
		ObjectNode data = jnode.objectNode();
		ObjectNode channel = data.objectNode();
		JsonNode mdata = channel.objectNode();
		try{
			im = new JSONDeserializer<InMessage>().deserialize(inmessage.toString(),InMessage.class); //Deserialize inmessage to Inmessage.class
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
		}
		return jnode;
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
			MessageData md = new MessageData();
			md.message = im.data.message;
			md.type = im.data.type;
			JSONSerializer aser = new JSONSerializer().include("*.channel");
			json = aser.exclude("*.class").serialize(md);
			jnode.put("type", im.type);
			mdata = Json.parse(json);
			for (int messageid = 0; messageid <4; messageid ++)
			{
				channel.putObject(String.valueOf(messageid)).putAll(Json.fromJson(mdata,ObjectNode.class));
				for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator.hasNext();){
					data.putObject(String.valueOf(iterator.next())).putAll(channel);		
				}
			}
			jnode.putObject("data").putAll(data);
		} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return jnode;
	}
}
>>>>>>> 8093ba07979d1814f2c729adc9561e1afbc5c03e
