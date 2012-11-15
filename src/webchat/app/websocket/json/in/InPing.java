package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InPing implements IInMessage {

	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("ping")){
			return true;
		}
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = null;
		myroutine.action = "";
		myroutine.dbmodel = null;
		myroutine.outmessage = null;
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
