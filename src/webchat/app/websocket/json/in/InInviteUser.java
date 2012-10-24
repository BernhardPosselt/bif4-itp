package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

public class InInviteUser extends IInMessage {
	public InInviteUserData data;

	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("inviteuser"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteUser();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.model = new models.Channel();
		myroutine.dbaction = "update";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
}
