package websocket;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import models.Groups;
import websocket.json.in.InInvite;

public class WebsocketInvite {
	public static void createInvite(JsonNode inmessage){
		int channelid  = inmessage.findPath("channel").asInt();
		List<Integer> oldusers = models.Channel.getChannelUsers(channelid);
		for (Iterator<models.User> useriter = models.User.getChannelGroupUser(Groups.getChannelGroups(channelid)).iterator(); useriter.hasNext();){
	  		int olduser = useriter.next().id;
	  		if (!oldusers.contains(olduser))
	  			oldusers.add(olduser);
	  	}
		List<Integer>users  = InInvite.invite(inmessage);   	
		WebsocketNotifier.sendMessagetoUser(oldusers, channelid, "update");
		WebsocketNotifier.sendMessagetoUser(users, channelid, "create");
	}
	
}
