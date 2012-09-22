package websocket;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.json.out.Channel;

public class WebsocketNotifier {
	 public static void notifyAllMembers(JsonNode inmessage) {
	    	WebSocket.Out<JsonNode> out = null;
	        for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
	            out = (WebSocket.Out<JsonNode>)entry.getKey();
	            out.write(inmessage);
	        }
	    }
	    
	    public static void sendMessagetoUser(List<Integer>users, int channelid, String action){
	    	WebSocket.Out<JsonNode> out = null;
	        for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: WebsocketManager.members.entrySet()) {
	            if (users.contains(entry.getValue())){
	            	out = (WebSocket.Out<JsonNode>)entry.getKey();
	            	out.write(Channel.genChannel(action, channelid));
	            }	
	        }
	    }
}
