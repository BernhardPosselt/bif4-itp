package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InChannelTopic extends IInMessage{
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
		myroutine.model = new models.Channel();
		myroutine.dbaction = "update";
		myroutine.sender = new Notifyall();
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelTopic();
	}

	
	/*public static int savetopicchange (JsonNode inmessage){
		int channelid = 0;
		try{
			InChanneltopic inchan = new InChanneltopic();
			inchan = new JSONDeserializer<InChanneltopic>().deserialize(
					inmessage.toString(), InChanneltopic.class);
			models.Channel chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.channel);
			channelid = inchan.data.channel;
			chan.topic = inchan.data.topic;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channelid;
	}*/

}

