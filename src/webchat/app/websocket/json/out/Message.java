package websocket.json.out;

import java.util.*;

import websocket.json.in.InMessage;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import models.*;
import play.libs.Json;

import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Message {
	public final String type;
	public Map<Integer, Map<Integer, MessageData>> data = new HashMap<Integer, Map<Integer, MessageData>>();
	public Boolean init;
	public Map<Integer, String> actions = new HashMap<Integer, String>();

	public Message() {
		this.type = "message";

	}

	public static JsonNode genMessage(JsonNode inmessage, int userid) {
		String json = "";
		try {
			// Map the incomeJsonMessage to a model
			InMessage im = new InMessage();
			im = new JSONDeserializer<InMessage>().deserialize(
					inmessage.toString(), InMessage.class);
			models.Message dbmessage = new models.Message();

			// Create DB Message
			dbmessage.content = im.data.message;
			dbmessage.date = new Date();
			dbmessage.modified = new Date();
			dbmessage.type = im.data.type;
			dbmessage.user = models.User.find.byId(userid);
			for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator
					.hasNext();) {
				dbmessage.channels.add(models.Channel.find.byId(iterator.next()));
			}
			// Save the Message to the DB
			dbmessage.save();
			dbmessage.saveManyToManyAssociations("channels");

			// Create JsonMessage
			Message m = new Message();
			MessageData md = new MessageData();
			m.init = true;
			md.date = new Date();
			md.message = im.data.message;
			md.modified = new Date();
			md.type = im.data.type;
			md.user_id = userid;

			// Put the Messages in the map
			Map<Integer, MessageData> mdata = new HashMap<Integer, MessageData>();
			mdata.put(dbmessage.id, md);

			// Fill the Action Dict; always create by messages
			m.actions.put(dbmessage.id, "create");

			// Put the Messages in the channels
			for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator
					.hasNext();) {
				m.data.put(iterator.next(), mdata);
			}
	
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
	public static JsonNode genmultipleMessage(JsonNode inmessage) {
		String json = "";
		InMessage im = new InMessage();
		ObjectNode jnode = Json.newObject();
		ObjectNode data = jnode.objectNode();
		ObjectNode channel = data.objectNode();
		JsonNode mdata = channel.objectNode();
		try {
			im = new JSONDeserializer<InMessage>().deserialize(
					inmessage.toString(), InMessage.class);
			MessageData md = new MessageData();
			md.message = im.data.message;
			md.type = im.data.type;
			JSONSerializer aser = new JSONSerializer().include("*.channel");
			json = aser.exclude("*.class").serialize(md);
			jnode.put("type", im.type);
			mdata = Json.parse(json);
			for (int messageid = 0; messageid < 4; messageid++) {
				channel.putObject(String.valueOf(messageid)).putAll(
						Json.fromJson(mdata, ObjectNode.class));
				for (Iterator<Integer> iterator = im.data.channel.iterator(); iterator
						.hasNext();) {
					data.putObject(String.valueOf(iterator.next())).putAll(
							channel);
				}
			}
			jnode.putObject("data").putAll(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jnode;
	}
}
