package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InChannelTopic implements IInMessage{
	public String type;
	public InChannelTopicData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("channeltopic"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine =new WorkRoutine();
		myroutine.inmessage = new InChannelTopic();
		myroutine.outmessage = new websocket.json.out.Channel();
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelTopic();
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel chan = null;
		try{
			InChannelTopic inchan = (InChannelTopic) inmessage;
			chan = Channel.find.byId(inchan.data.id);
			chan.topic = inchan.data.topic;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}	
		return chan;
	}
}

