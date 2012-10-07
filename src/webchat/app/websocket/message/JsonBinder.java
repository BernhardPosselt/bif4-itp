package websocket.message;


import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import websocket.json.in.InChannelClose;
import websocket.json.out.ActiveUser;

import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class JsonBinder {
	
	public static JsonNode bindtoJson(WorkRoutine myroutine){
		String json = "";
		try {
			IOutMessage outmessage = myroutine.outmessage;
			JSONSerializer myser = new JSONSerializer().include("*.data", "*.files", "*.users", "*.groups");
			json = myser.exclude("*.class").serialize(outmessage);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}

	public static IInMessage bindfromJson(JsonNode inmessage, WorkRoutine myroutine){
		IInMessage inmess = myroutine.inmessage;
		inmess =  (IInMessage) new JSONDeserializer<>().deserialize(
				inmessage.toString(), inmess.getClass());
		return inmess;
	}
}
