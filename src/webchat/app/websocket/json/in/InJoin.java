package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InJoin {
	public String type;
	public InJoinData data;

	
	public static int getchannel(JsonNode injoin){
		int channel = 0;
		try{
			InJoin inj = new InJoin();
			inj = new JSONDeserializer<InJoin>().deserialize(
					injoin.toString(), InJoin.class);
			channel = inj.data.channel;
		}catch (Exception e){
			e.printStackTrace();
		}
		return channel;
	}
}
