package websocket;

import chatbot.ChatbotManager;
import org.codehaus.jackson.JsonNode;

import websocket.json.out.Message;

public class WebsocketMessage {

	public static void createMessage(JsonNode inmessage, int userid){
		WebsocketNotifier.notifyAllMembers(Message.genMessage(inmessage, userid));
	}
}
