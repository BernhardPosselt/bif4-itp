package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChannelTopic;
import websocket.json.out.Channel;
import websocket.message.IMessageSender;

public class WebsocketChanneltopic implements IMessageSender {
	/*public static void createChanneltopic(JsonNode inmessage){
		int channelid = InChannelTopic.savetopicchange(inmessage);
    	WebsocketNotifier.notifyAllMembers(Channel.genChannel("update", channelid));
	}*/

	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
		
	}
}
