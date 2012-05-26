package websocket.json.in;

import java.util.Iterator;
import java.util.Map;

import models.Channel;
import models.Groups;
import models.User;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InNewChannel {
	public String type;
	public InNewChannelData data;
	
	public static void createnewchannel(JsonNode inmessage){
		try{
			InNewChannel innewchan = new InNewChannel();
			innewchan = new JSONDeserializer<InNewChannel>().deserialize(
					inmessage.toString(), InNewChannel.class);
			Channel chan = new Channel();
			
			chan.name = innewchan.data.name;
			chan.topic = innewchan.data.topic;
			chan.priv = innewchan.data.priv;
			
			for (Iterator<Integer> useriter = innewchan.data.users.iterator(); useriter.hasNext();){
				User user = new User();
				user = User.find.byId(useriter.next());
				chan.users.add(user);
			}
			for (Iterator<Integer> groupiter = innewchan.data.groups.iterator(); groupiter.hasNext();){
				Groups group = new Groups();
				group = Groups.find.byId(groupiter.next());
				chan.groups.add(group);
			}
			
			chan.save();
			chan.saveManyToManyAssociations("users");
			chan.saveManyToManyAssociations("groups");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
