package websocket.json.out;

import play.libs.Json;
import websocket.message.WebSocketNotifier;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Status{
	public String type;
	public StatusData data;

	public Status() {
		this.type = "status";
		this.data = new StatusData();
	}

	public static void genStatustoall(String level, String msg) {
		try {
			Status outstatus = new Status();
			outstatus.data.level = level;
			outstatus.data.msg = msg;
			JSONSerializer sser = new JSONSerializer();
			String outmessage = sser.exclude("*.class").serialize(outstatus);
			WebSocketNotifier.notifyAllMembers(Json.parse(outmessage));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void genStatus(String level, String msg, int userid) {
		try {
			Status outstatus = new Status();
			outstatus.data.level = level;
			outstatus.data.msg = msg;
			JSONSerializer sser = new JSONSerializer();
			String outmessage = sser.exclude("*.class").serialize(outstatus);
			WebSocketNotifier.sendMessagetoUser(Json.parse(outmessage), userid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
