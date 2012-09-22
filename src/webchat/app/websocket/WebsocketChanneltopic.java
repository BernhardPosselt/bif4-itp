package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChanneltopic;
import websocket.json.out.Channel;

public class WebsocketChanneltopic {
	public static void createChanneltopic(JsonNode inmessage){
		int channelid = InChanneltopic.savetopicchange(inmessage);
    	WebsocketNotifier.notifyAllMembers(Channel.genChannel("update", channelid));
	}
}
