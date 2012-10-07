package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChannelDelete;
import websocket.json.out.Channel;
import websocket.message.IMessageSender;

public class WebsocketChanneldelete implements IMessageSender {

	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
		
	}
}
