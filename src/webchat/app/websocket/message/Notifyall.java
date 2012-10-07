package websocket.message;

import org.codehaus.jackson.JsonNode;

import websocket.WebsocketNotifier;

public class Notifyall implements IMessageSender {

	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
	}
}
