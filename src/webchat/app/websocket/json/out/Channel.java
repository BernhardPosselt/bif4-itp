package websocket.json.out;

import java.util.*;


import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import websocket.json.in.InMessage;
import websocket.message.IOutMessage;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Channel extends IOutMessage {
	public String type;
	public ChannelData data = new ChannelData();
	public String action;

	public Channel(){
		this.type = "channel";
	}
	
	/*public static JsonNode geninitChannel(int userid){
		String json = "",action = "create";
		try {
			Channel channel = new Channel();
			channel.init = true;
			for (Iterator<models.Channel> iterator = models.Channel.getUserChannels(userid).iterator(); iterator.hasNext();){
				ChannelData cdata = new ChannelData();
				models.Channel c = new models.Channel();
				c = iterator.next();
				for (Iterator<models.File> itfile = c.files.iterator(); itfile.hasNext();){
					cdata.files.add(itfile.next().id);
				}
				for (Iterator<models.Groups> itgroup = c.groups.iterator(); itgroup.hasNext();){
					cdata.groups.add(itgroup.next().id);
				}
				for (Iterator<models.User> ituser = c.users.iterator(); ituser.hasNext();){
					cdata.users.add(ituser.next().id);
				}
				cdata.name = c.name;
				cdata.topic = c.topic;
				cdata.modified = new Date();
				channel.actions.put(c.id, action);
				channel.data.put(c.id, cdata);
			}
	
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions", "*.files", "*.users", "*.groups");
			json = aser.exclude("*.class").serialize(channel);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}
	
	public static JsonNode genChannel(String action, int channelid){
		String json = "";
		try {
			models.Channel mchan = new models.Channel();
			mchan = models.Channel.find.byId(channelid);
			
			Channel channel = new Channel();
			ChannelData cdata = new ChannelData();
			channel.actions.put(channelid, action);
			channel.init = false;
			
			cdata.name = mchan.name;
			cdata.topic = mchan.topic;
			cdata.modified = new Date();
			
			for (Iterator<models.File> itfile = mchan.files.iterator(); itfile.hasNext();){
				cdata.files.add(itfile.next().id);
			}
			for (Iterator<models.Groups> itgroup = mchan.groups.iterator(); itgroup.hasNext();){
				cdata.groups.add(itgroup.next().id);
			}
			for (Iterator<models.User> ituser = mchan.users.iterator(); ituser.hasNext();){
				cdata.users.add(ituser.next().id);
			}
			channel.data.put(channelid, cdata);
			
			// Generate the Json Message
			JSONSerializer aser = new JSONSerializer().include("*.data",
					"*.actions", "*.files", "*.users", "*.groups");
			json = aser.exclude("*.class").serialize(channel);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json.parse(json);
	}*/
}