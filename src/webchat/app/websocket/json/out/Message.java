package websocket.json.out;

import java.util.*;

import chatbot.ChatbotManager;
import chatbot.ChatbotResult;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;

import flexjson.JSONSerializer;

import play.db.ebean.Model;
import play.libs.Json;

public class Message implements IOutMessage {
	public String type;
	public MessageData data = new MessageData();
	public String action;
    private ChatbotManager cbm = new ChatbotManager();
	
	public Message() {
		this.type = "message";
		this.action = "create";

	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);	
	}
	
	public static JsonNode genJabberMessage(String content, int userid, int channelid)
	{
		String json = "";
		try{
			models.Message dbmessage = new models.Message();


			
			// Create DB Message
			dbmessage.message = StringEscapeUtils.escapeSql(content);
			dbmessage.date = new Date();
			dbmessage.modified = new Date();
			dbmessage.type = "text";
			dbmessage.owner_id = models.User.find.byId(userid);
			dbmessage.channel_id = models.Channel.find.byId(channelid);
			
			// Save the Message to the DB
			dbmessage.save();
			dbmessage.saveManyToManyAssociations("channels");
	
			// Create JsonMessage
			Message m = new Message();
			MessageData md = new MessageData();
			
			md.date = new Date();
		
			md.message = StringEscapeUtils.escapeHtml(dbmessage.message);
			md.message = md.message.replaceAll("\n", "<br/>");
			md.modified = new Date();
			md.type = dbmessage.type;
		
			md.owner_id = userid;
	
			m.action = "create";
			m.data = md;
			
			
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions");
			json = aser.exclude("*.class").serialize(m);
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return Json.parse(json);
	}
	

	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid, String action) {
		Message outmessage = null;
		try {
			outmessage = new Message();
			models.Message dbmessage = (models.Message) dbmodel;

            ChatbotResult result = cbm.executePlugin(dbmessage.message);

			MessageData mdata = new MessageData();
			mdata.date = dbmessage.date;

            if(result.isSuccess())
            {
                mdata.message = result.getOut();
            }
            else
            {
			    mdata.message = StringEscapeUtils.escapeHtml(dbmessage.message);
            }
			
			if (dbmessage.type.equals("text"))
				mdata.message = mdata.message.replaceAll("\n", "<br/>");
			mdata.id = dbmessage.id;
			mdata.type = dbmessage.type;
			mdata.owner_id = userid;
			mdata.modified = dbmessage.modified;
			mdata.channel_id = dbmessage.channel_id.id;
			outmessage.data = mdata;
			outmessage.action = action;
			//Send to MUC
			//MucChannel.sendMucMessage(md.message, chanid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outmessage;
	}
}
