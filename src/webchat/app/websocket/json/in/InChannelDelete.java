package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InChannelDelete extends IInMessage{
	
	public InChannelDeleteData data;
	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("channeldelete"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage =  new InChannelDelete();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.model = new models.Channel();
		myroutine.dbaction = "delete";
		myroutine.sender = new Notifyall();
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelDelete();
	}
	
	/*public static void deletechannel (JsonNode inmessage){
		try{
			InChannelDelete inchan = new InChannelDelete();
			inchan = new JSONDeserializer<InChannelDelete>().deserialize(
					inmessage.toString(), InChannelDelete.class);
			models.Channel chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.channel);
			chan.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/
}
