package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InChannelDelete implements IInMessage{
	
	public String type;
	public InChannelDeleteData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("channeldelete"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage =  new InChannelDelete();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "delete";
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelDelete();
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel chan = null;
		try{
			InChannelDelete inchan = (InChannelDelete) inmessage;
			chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.id);
			chan.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
		return chan;
	}
}
