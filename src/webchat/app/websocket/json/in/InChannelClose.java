package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InChannelClose {
	public String type;
	public InChannelCloseData data;
	
	public static int closechannel (JsonNode inmessage){
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
	}
}
