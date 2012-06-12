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
	
	public static int createnewchannel(JsonNode inmessage, int userid){
		int channelid = 0;
		try{
			InNewChannel innewchan = new InNewChannel();
			innewchan = new JSONDeserializer<InNewChannel>().deserialize(
					inmessage.toString(), InNewChannel.class);
			for (Iterator<Channel> channeliter = Channel.find.all().iterator(); channeliter.hasNext();){
				if (channeliter.next().name.equals(innewchan.data.name.trim()))
					return -1;
			}
			Channel chan = new Channel();
			
			chan.name = innewchan.data.name;
			chan.topic = innewchan.data.topic;
			chan.is_public = innewchan.data.is_public;
			chan.archived = false;
			if (chan.is_public == true){
				for (Iterator<models.User> useriter = models.User.find.all().iterator(); useriter.hasNext();){
					chan.setUsers(useriter.next());
				}
				for (Iterator<Groups> groupiter = models.Groups.find.all().iterator(); groupiter.hasNext();){
					chan.setGroups(groupiter.next());
				}
			}
			else
				chan.setUsers(models.User.find.byId(userid));
			
			chan.save();
			chan.saveManyToManyAssociations("users");
			chan.saveManyToManyAssociations("groups");
			channelid = chan.id;
		}catch (Exception e){
			e.printStackTrace();
		}
		return channelid;
	}
}
