package json_models;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;

import models.Channel;
import flexjson.JSONException;
import flexjson.JSONSerializer;


public class Message{
	public final String type;
	public MessageData data;
	
	public Message(){
		this.type = "message";
	}
	
	public static JsonNode genMessage(JsonNode inmessage)
	{
		String json = "";
		try{
			
			Message m = new Message();
			List<Channel> channels = new ArrayList<Channel>();
			m.data.message = inmessage.findPath("message").asText();
			//m.data.channels = channels;
			
			JSONSerializer mser = new JSONSerializer();
			json = mser.exclude(".class").serialize(m);
		}catch (JSONException e){
			e.printStackTrace();
		}
		return Json.parse(json);
	}
}
