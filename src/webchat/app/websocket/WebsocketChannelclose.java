package websocket;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InChannelClose;
import websocket.json.out.Channel;
import websocket.message.IMessageSender;
import websocket.message.WorkRoutine;

public class WebsocketChannelclose implements IMessageSender {

	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
		
	}
}
