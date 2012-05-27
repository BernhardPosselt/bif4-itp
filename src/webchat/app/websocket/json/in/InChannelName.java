package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InChannelName {
	public String type;
	public InChannelNameData data;
	
	public static int changechannelname (JsonNode inmessage){
		int channelid = 0;
		try{
			InChannelName inchan = new InChannelName();
			inchan = new JSONDeserializer<InChannelName>().deserialize(
					inmessage.toString(), InChannelName.class);
			models.Channel chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.channel);
			channelid = inchan.data.channel;
			chan.name = inchan.data.name;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channelid;
	}
}
