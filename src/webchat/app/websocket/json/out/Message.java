package websocket.json.out;

import java.util.*;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

public class Message implements IOutMessage {
	public String type;
	public MessageData data = new MessageData();
	public String action;
	
	public Message() {
		this.type = "message";
		this.action = "create";

	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);	
	}

	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid) {
		Message outmessage = null;
		try {
			outmessage = new Message();
			models.Message dbmessage = (models.Message) dbmodel;

			MessageData mdata = new MessageData();
			mdata.date = new Date();
			mdata.message = StringEscapeUtils.escapeHtml(dbmessage.message);
			
			if (dbmessage.type.equals("text"))
				mdata.message = mdata.message.replaceAll("\n", "<br/>");
			mdata.id = dbmessage.id;
			mdata.type = dbmessage.type;
			mdata.owner_id = userid;
			mdata.channel_id = dbmessage.channel_id.id;
			outmessage.data = mdata;
			outmessage.action = "create";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outmessage;
	}
}
