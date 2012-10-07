package websocket.json.in;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

public class InInviteGroup extends IInMessage{
	public InInviteGroupData data = new InInviteGroupData();

	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("invitegroup"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteGroup();
		myroutine.model = new models.Channel();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.dbaction = "update";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
}
