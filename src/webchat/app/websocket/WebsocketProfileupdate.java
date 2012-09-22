package websocket;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

import websocket.json.in.InProfileUpdate;
import websocket.json.out.User;

public class WebsocketProfileupdate {
	public static void createProfileupdate (JsonNode inmessage, WebSocket.Out<JsonNode>out, int userid){
		JsonNode error = InProfileUpdate.updateprofile(inmessage, userid);
	if (!error.isNull())
		out.write(error);
	else
		WebsocketNotifier.notifyAllMembers(User.genUserchanged(userid, "update"));
	}
	
}
