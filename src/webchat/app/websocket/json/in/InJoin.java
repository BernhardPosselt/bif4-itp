package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;

import websocket.message.WorkRoutine;


public class InJoin implements IInMessage {
	public String type;
	public InJoinData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("join")){
			return true;
		}
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InJoin();
		myroutine.outmessage = null;
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InJoin();
	}

	@Override
	public Model savetoDB(IInMessage inmessage) {
		// Nothing to be done	
		return null;
	}
}
