package websocket.json.in;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

public class InInviteUser extends IInMessage {
	public InInviteUserData data;

	@Override
	public boolean canHandle(String type) {
		if (type.equals("inviteuser"))
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
