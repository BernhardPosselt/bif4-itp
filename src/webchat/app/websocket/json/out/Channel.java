package websocket.json.out;

import java.util.*;

import org.codehaus.jackson.JsonNode;



import play.db.ebean.Model;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;



public class Channel implements IOutMessage {
	public String type;
	public ChannelData data = new ChannelData();
	public String action;

	public Channel(){
		this.type = "channel";
	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		Channel chn = (Channel) outmessage;
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.sendMessagetoUser(models.Channel.getallChannelUsers(chn.data.id),outjson);
	}
	
	public void sendMessagetoUser(IOutMessage outmessage, int userid){
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		List<Integer> userlist = new ArrayList<Integer>();
		userlist.add(userid);
		WebSocketNotifier.sendMessagetoUser(userlist,outjson);
	}

	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid, String action) {
		Channel outchan = null;
		try {
			models.Channel dbchan = (models.Channel)dbmodel;		
			outchan = new Channel();
			ChannelData chandata = new ChannelData();
			outchan.action = action;
			
			chandata.id = dbchan.id;
			chandata.name = dbchan.name;
			chandata.topic = dbchan.topic;
				
			for (Iterator<models.File> itfile = dbchan.files.iterator(); itfile.hasNext();){
				chandata.files.add(itfile.next().id);
			}
			for (Iterator<models.Groups> itgroup = dbchan.groups.iterator(); itgroup.hasNext();){
				chandata.groups.add(itgroup.next().id);
			}
			for (Iterator<models.User> ituser = dbchan.users.iterator(); ituser.hasNext();){
				chandata.users.add(ituser.next().id);
			}
			outchan.data = chandata;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outchan;
	}
}