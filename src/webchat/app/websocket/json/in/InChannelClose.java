package websocket.json.in;

import models.Channel;
import play.db.ebean.*;
import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InChannelClose extends IInMessage {

	public InChannelCloseData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("channelclose"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InChannelClose();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.model = new models.Channel();
		myroutine.sender = new Notifyall();
		myroutine.dbaction = "update";
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelClose();
	}
	
	/*public static int closechannel (JsonNode inmessage){
		int channelid = 0;
		try{
			InChannelClose inchan = new InChannelClose();
			inchan = new JSONDeserializer<InChannelClose>().deserialize(
					inmessage.toString(), InChannelClose.class);
			models.Channel chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.channel);
			chan.archived = true;
			channelid = chan.id;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channelid;
	}*/
}
