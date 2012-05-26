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
	public Map<Integer,InInviteData> data = new HashMap<Integer,InInviteData>();
	
	
	public static void invite(JsonNode inmessage){
		try{
			InInvite ininv = new InInvite();
			ininv = new JSONDeserializer<InInvite>().deserialize(
					inmessage.toString(), InInvite.class);
			Channel chan = new Channel();
			int cid;
			for (Map.Entry<Integer, InInviteData> entry:ininv.data.entrySet()){
				cid = (Integer)entry.getKey();
				InInviteData invdata = new InInviteData();
				invdata = (InInviteData)entry.getValue();
				chan = models.Channel.find.byId(cid);
				for (Iterator<Integer> useriter = invdata.users.iterator(); useriter.hasNext();){
					User user = new User();
					user = User.find.byId(useriter.next());
					chan.users.add(user);
				}
				for (Iterator<Integer> groupiter = invdata.groups.iterator(); groupiter.hasNext();){
					Groups group = new Groups();
					group = Groups.find.byId(groupiter.next());
					chan.groups.add(group);
				}
			}
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

}
