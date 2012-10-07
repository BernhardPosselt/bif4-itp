package websocket;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InFileDelete;
import websocket.json.out.Channel;
import websocket.message.IMessageSender;

public class WebsocketFiledelete implements IMessageSender {
	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
		
	}
}
