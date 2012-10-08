package websocket.json.out;

import java.util.*;

import javax.swing.text.html.HTML;

import websocket.json.in.InMessage;
import websocket.message.IOutMessage;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.apache.commons.lang.StringEscapeUtils;

import models.*;
import play.libs.Json;

import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Message extends IOutMessage {
	public String type;
	public MessageData data = new MessageData();
	public String action;
	
	public Message() {
		this.type = "message";
		this.action = "create";

	}

	/*public static JsonNode genMessage(JsonNode inmessage, int userid) {
		String json = "";
		try {
			// Map the incomeJsonMessage to a model
			InMessage im = new InMessage();
			im = new JSONDeserializer<InMessage>().deserialize(
					inmessage.toString(), InMessage.class);
			models.Message dbmessage = new models.Message();
	
			// Create DB Message
			dbmessage.message = StringEscapeUtils.escapeSql(im.data.message);
			dbmessage.date = new Date();
			dbmessage.modified = new Date();
			dbmessage.type = im.data.type;
			dbmessage.user_id = models.User.find.byId(userid);
		
			// Save the Message to the DB
			dbmessage.save();
		

			// Create JsonMessage
			Message m = new Message();
			MessageData md = new MessageData();
			md.date = new Date();
		
			md.message = StringEscapeUtils.escapeHtml(im.data.message);
			if (im.data.type.equals("text"))
				md.message = md.message.replaceAll("\n", "<br/>");
			md.type = im.data.type;
			md.user_id = userid;

			// Put the Messages in the map
			Map<Integer, MessageData> mdata = new LinkedHashMap<Integer, MessageData>();
			mdata.put(dbmessage.id, md);

			// Fill the Action Dict; always create by messages
			m.action = "create";

			
	
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions");
			json = aser.exclude("*.class").serialize(m);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}

	// JsonNode-Generation for sending more messages at one time
	public static JsonNode genjoinMessage(int channelid) {
		String json = "";
		try {
			Message m = new Message();
			Map<Integer, MessageData> mdata = new LinkedHashMap<Integer, MessageData>();
		
			for (Iterator<models.Message> miter = models.Message.getallChannelMessages(channelid).iterator(); miter.hasNext();){
				models.Message dbmessage = new models.Message();
				dbmessage = miter.next();
				MessageData md = new MessageData();
				md.date = dbmessage.date;
				md.message = StringEscapeUtils.escapeHtml(dbmessage.message);
				
				md.type = dbmessage.type;
				md.user_id = dbmessage.user_id.id;
				mdata.put(dbmessage.id, md);
				m.action = "create";
				
			}
			
			
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions");
			json = aser.exclude("*.class").serialize(m);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}*/
}
