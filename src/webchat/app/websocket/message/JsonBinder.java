package websocket.message;


import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import websocket.Interfaces.IInMessage;
import websocket.Interfaces.IOutMessage;

import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class JsonBinder {
	
	public static JsonNode bindtoJson(IOutMessage outmessage){
		String json = "";
		try {
			JSONSerializer myser = new JSONSerializer().include("*.data", "*.files", "*.users", "*.groups", "*.mod", ".readonly");
			json = myser.exclude("*.class").serialize(outmessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}

	public static IInMessage bindfromJson(JsonNode inmessage, IInMessage inmess){
		inmess =  (IInMessage) new JSONDeserializer<>().deserialize(
				inmessage.toString(), inmess.getClass());
		return inmess;
	}
}
