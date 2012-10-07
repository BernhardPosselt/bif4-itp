package websocket.json.in;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

public class InMessage extends IInMessage{
	public InMessageData data;
	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("message"))
			return true;
		else
			return false;
	}
	
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InMessage();
		myroutine.model = new models.Message();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.dbaction = "create";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InMessage();
	}
	
	/*public InMessage(){
		this.type = "message";
	}*/
}
