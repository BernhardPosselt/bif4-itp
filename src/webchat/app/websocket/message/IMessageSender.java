package websocket.message;

import org.codehaus.jackson.JsonNode;

public interface IMessageSender {
	
	public abstract void sendMessage(JsonNode outmessage);
}
