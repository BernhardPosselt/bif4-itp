package websocket.json.in;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;

import websocket.WebsocketNotifier;
import websocket.json.out.Message;
import websocket.json.out.MessageData;
import websocket.message.IInMessage;
import websocket.message.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InJoin extends IInMessage {
	public InJoinData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("join")){
			joinMessage(inmessage.findPath("id").asInt());
			return true;
		}
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InJoin();
		myroutine.model= null;
		myroutine.outmessage = null;
		myroutine.dbaction = null;
		myroutine.sender = null;
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InJoin();
	}

	public static void joinMessage(int channelid) {
		Message m = new Message();
		WorkRoutine myroutine=new WorkRoutine();
		try {	
			for (Iterator<models.Message> miter = models.Message.getallChannelMessages(channelid).iterator(); miter.hasNext();){
				models.Message dbmessage = new models.Message();
				dbmessage = miter.next();
				MessageData md = new MessageData();
				md.date = dbmessage.date;
				md.message = StringEscapeUtils.escapeHtml(dbmessage.message);
				md.modified = dbmessage.modified;
				md.id = dbmessage.id;
				md.type = dbmessage.type;
				md.owner_id = dbmessage.owner_id.id;	
				md.channel_id = dbmessage.channel_id.id;
				m.data = md;
				myroutine.outmessage = m;
				JsonNode inmessage = JsonBinder.bindtoJson(myroutine);
				WebsocketNotifier.notifyAllMembers(inmessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
