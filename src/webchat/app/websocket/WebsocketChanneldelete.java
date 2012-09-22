package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChannelDelete;
import websocket.json.out.Channel;

public class WebsocketChanneldelete {
	public static void createChanneldelete(JsonNode inmessage){
		int channelid = inmessage.findPath("channel").asInt();
    	WebsocketNotifier.notifyAllMembers(Channel.genChannel("delete", channelid));
    	InChannelDelete.deletechannel(inmessage);
	}
}
