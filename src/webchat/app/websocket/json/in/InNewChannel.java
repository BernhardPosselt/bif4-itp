package websocket.json.in;



import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;


public class InNewChannel implements IInMessage {
	public String type;
	public InNewChannelData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("newchannel"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InNewChannel();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "create";
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InNewChannel();
	}
	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel chan = null;
		try{
			InNewChannel innewchan = (InNewChannel) inmessage;
			for (Iterator<models.Channel> channeliter = models.Channel.find.all().iterator(); channeliter.hasNext();){
				models.Channel hilfchan = channeliter.next();
				if (hilfchan.name.equals(innewchan.data.name)){
					websocket.json.out.Status.genStatus("error", "Channelname already exists", userid);
					return null;
				}
			}
			
			chan = new models.Channel();
			chan.name = innewchan.data.name;
			chan.topic = innewchan.data.topic;
			chan.is_public = innewchan.data.is_public;
			chan.archived = false;
			chan.setMods(userid);
			
			if (chan.is_public == true){
				for (Iterator<models.User> useriter = models.User.find.all().iterator(); useriter.hasNext();){
					chan.setUsers(useriter.next());
				}
				for (Iterator<models.Groups> groupiter = models.Groups.find.all().iterator(); groupiter.hasNext();){
					chan.setGroups(groupiter.next());
				}
			}
			else
				chan.setUsers(models.User.getbyId(userid));
						
			chan.save();
		}catch (Exception e){
			e.printStackTrace();
		}	
		return chan;
	}
}
