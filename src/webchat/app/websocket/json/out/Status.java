package websocket.json.out;

import org.codehaus.jackson.JsonNode;
import java.util.Map;

import play.libs.Json;
import websocket.message.IOutMessage;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Status extends IOutMessage{
	public String type;
	public StatusData data;

	public Status() {
		this.type = "status";
		this.data = new StatusData();
	}

	public static JsonNode genStatus(String level, String msg) {
		String json = "";
		try {

			Status s = new Status();
			s.data.level = level;
			s.data.msg = msg;
			JSONSerializer sser = new JSONSerializer();
			json = sser.exclude("*.class").serialize(s);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}

}
