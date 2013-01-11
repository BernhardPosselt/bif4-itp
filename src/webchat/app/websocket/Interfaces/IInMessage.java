package websocket.Interfaces;

import org.codehaus.jackson.JsonNode;
import websocket.message.WorkRoutine;
import play.db.ebean.*;

public interface IInMessage extends IMessage {
	public abstract boolean canHandle(JsonNode inmessage);
	public abstract WorkRoutine getWorkRoutine();
	public abstract Model savetoDB(IInMessage inmessage, int userid);
}
