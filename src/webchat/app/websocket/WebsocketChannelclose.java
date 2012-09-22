package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChannelClose;
import websocket.json.out.Channel;

public class WebsocketChannelclose {
	public static void createChannelclose(JsonNode inmessage){
		int channelid = InChannelClose.closechannel(inmessage);
    	WebsocketNotifier.notifyAllMembers(Channel.genChannel("delete", channelid));
	}
}
