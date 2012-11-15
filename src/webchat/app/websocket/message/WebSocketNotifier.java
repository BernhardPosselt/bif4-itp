package websocket.message;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.WebsocketManager;

public class WebSocketNotifier {
	public static void notifyAllMembers(JsonNode inmessage) {
		WebSocket.Out<JsonNode> out = null;
		for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
			out = (WebSocket.Out<JsonNode>)entry.getKey();
			out.write(inmessage);
		}
	}
		
	public static void sendMessagetoUser(List<Integer> userlist, JsonNode outmessage){
		WebSocket.Out<JsonNode> out = null;
		for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
			if (userlist.contains(entry.getValue())){
				out = (WebSocket.Out<JsonNode>)entry.getKey();
				out.write(outmessage);
			}	
		}
	}
}
