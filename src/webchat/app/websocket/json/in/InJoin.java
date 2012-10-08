package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import websocket.message.IInMessage;
import websocket.message.IOutMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InJoin extends IInMessage {
	public InJoinData data;
	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("join"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InJoin();
		myroutine.model=null;
		myroutine.outmessage = null;
		myroutine.dbaction = "";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InJoin();
	}

	
	/*public static int getchannel(JsonNode injoin){
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
	}*/
}
