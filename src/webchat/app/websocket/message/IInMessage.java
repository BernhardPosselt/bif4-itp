package websocket.message;

import org.codehaus.jackson.JsonNode;


public abstract class IInMessage extends IMessage {
	public abstract boolean canHandle(JsonNode inmessage);
	public abstract WorkRoutine getWorkRoutine();
}
