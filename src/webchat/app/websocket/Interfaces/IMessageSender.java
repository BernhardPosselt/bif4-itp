package websocket.Interfaces;

import java.util.List;

import org.codehaus.jackson.JsonNode;

public interface IMessageSender {
	
	public abstract void sendMessagetoAll(JsonNode outmessage);
	public abstract void sendMessagetoUsers (JsonNode outmessage, List<models.User> ulist);
}
