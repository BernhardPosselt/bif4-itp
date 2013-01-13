package websocket.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.WebsocketManager;


public class WebSocketNotifier {
	public static void notifyAllMembers(JsonNode outmessage) {
		WebSocket.Out<JsonNode> out = null;
		for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
			out = (WebSocket.Out<JsonNode>)entry.getKey();
			out.write(outmessage);
		}
	}
		
	public static void sendMessagetoUsers(List<Integer> userlist, JsonNode outmessage){
		WebSocket.Out<JsonNode> out = null;
		for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
			if (userlist.contains(entry.getValue())){
				out = (WebSocket.Out<JsonNode>)entry.getKey();
				out.write(outmessage);
			}	
		}
	}
	
	public static void sendMessagetoUser(JsonNode outmessage, int userid){
		List<Integer> userlist = new ArrayList<Integer>();
		userlist.add(userid);
		WebSocketNotifier.sendMessagetoUsers(userlist,outmessage);
	}
}
