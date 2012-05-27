package websocket.json.in;

import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

import models.Channel;
import models.Groups;
import models.User;
import flexjson.JSONDeserializer;

public class InInvite {
	public String type;
	public InInviteData data;
	
	
	public static List<Integer> invite(JsonNode inmessage){
		List<Integer> users = new ArrayList<Integer>();
		try{
			InInvite ininv = new InInvite();
			ininv = new JSONDeserializer<InInvite>().deserialize(
					inmessage.toString(), InInvite.class);
			Channel chan = new Channel();
		
			chan = models.Channel.find.byId(ininv.data.channel);
			
			for (Iterator<Integer> groupiter = ininv.data.groups.iterator(); groupiter.hasNext();){
				Groups group = new Groups();
				group = Groups.find.byId(groupiter.next());
				if (!chan.groups.contains(group)){
					chan.groups.add(group);
					for (Iterator<models.User> useriter = group.users.iterator(); useriter.hasNext();){
						models.User user = new User();
						user = useriter.next();
						if (Channel.getChannelUsers(chan.id).contains(user.id))
							chan.users.remove(user.id);
						else
							users.add(user.id);
					}
				}		
			}
			
			for (Iterator<Integer> useriter = ininv.data.users.iterator(); useriter.hasNext();){
				User user = new User();
				user = User.find.byId(useriter.next());	
				if (!(User.getChannelGroupUser(Groups.getChannelGroups(chan.id)).contains(user)) && (!Channel.getChannelUsers(chan.id).contains(user.id))){
					chan.users.add(user);
					users.add(user.id);
				}
			}
			
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return users;
	}
	

}
