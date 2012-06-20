package websocket.json.in;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Channel;
import models.Groups;
import models.User;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InKick {
	public String type;
	public InKickData data;
	
	public static List<Integer> kick(JsonNode inmessage){
		List<Integer> users = new ArrayList<Integer>();
		try{
			InKick inkick = new InKick();
			inkick = new JSONDeserializer<InKick>().deserialize(
					inmessage.toString(), InKick.class);
			Channel chan = new Channel();
		
			chan = models.Channel.find.byId(inkick.data.channel);
	
			for (Iterator<Integer> useriter = inkick.data.users.iterator(); useriter.hasNext();){
				User user = new User();
				user = User.find.byId(useriter.next());
				if (chan.users.contains(user)){
					chan.users.remove(user);
					if (!models.User.getChannelGroupUser(chan.groups).contains(user))
						users.add(user.id);
				}
			}
			for (Iterator<Integer> groupiter = inkick.data.groups.iterator(); groupiter.hasNext();){
				Groups group = new Groups();
				group = Groups.find.byId(groupiter.next());
				if (chan.groups.contains(group)){
					for (Iterator<models.User> useriter = models.Groups.find.byId(group.id).users.iterator(); useriter.hasNext();){
						models.User user = new models.User();
						user = useriter.next();
						users.add(user.id);
						if (!models.Channel.getChannelUsers(chan.id).contains(user.id))
						{	for (Iterator<models.Groups> griter = models.Groups.getChannelGroups(chan.id).iterator(); griter.hasNext();){
								Groups helpgroup = new Groups();
								helpgroup = griter.next();
								if (!helpgroup.equals(group)){
									if(helpgroup.users.contains(user))
										users.remove(Integer.valueOf(user.id));	
								}
							}	
						}
						else
							users.remove(Integer.valueOf(user.id));
					}
					chan.groups.remove(group);
				}		
			}
			
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return users;
	}
}
