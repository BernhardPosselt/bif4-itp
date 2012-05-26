package websocket.json.in;

import models.Channel;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InChanneltopic {
	public String type;
	public InChanneltopicData data;
	
	
	public static int savetopicchange (JsonNode inmessage){
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
	}

}

