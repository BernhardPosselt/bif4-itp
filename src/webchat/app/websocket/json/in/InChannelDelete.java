package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InChannelDelete {
	public String type;
	public InChannelDeleteData data;
	
	public static void deletechannel (JsonNode inmessage){
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
	}
}
