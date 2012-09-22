package websocket;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

import models.Groups;
import websocket.json.in.InKick;

public class WebsocketKick {
	public static void createKick(JsonNode inmessage, WebSocket.Out<JsonNode>out, int userid){
		int channelid  = inmessage.findPath("channel").asInt();
    	List<Integer>users  = InKick.kick(inmessage); 
      	List<Integer> stayusers = models.Channel.getChannelUsers(channelid);
      	for (Iterator<models.User> useriter = models.User.getChannelGroupUser(Groups.getChannelGroups(channelid)).iterator(); useriter.hasNext();){
      		int stayuser = useriter.next().id;
      		if (!stayusers.contains(stayuser))
      			stayusers.add(stayuser);
      	}
    	WebsocketNotifier.sendMessagetoUser(stayusers, channelid, "update");
    	WebsocketNotifier.sendMessagetoUser(users, channelid, "delete");
	}
}
