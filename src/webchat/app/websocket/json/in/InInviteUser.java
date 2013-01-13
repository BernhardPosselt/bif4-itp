package websocket.json.in;

import java.util.Iterator;


import models.Groups;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;
import websocket.message.WorkRoutine;

public class InInviteUser implements IInMessage {
	public String type;
	public InInviteUserData data;

	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("inviteuser"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteUser();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "update";
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel dbchan = null;
	    boolean hilf = false;
		try{
			InInviteUser inuser = (InInviteUser) inmessage;
			models.User dbuser = models.User.getbyId(inuser.data.users);
			dbchan = models.Channel.getbyId(inuser.data.id);
			if (inuser.data.value == true){
				if (!dbchan.users.contains(dbuser)){
					for(Iterator<models.Groups> groupiter = Groups.getChannelGroups(dbchan.id).iterator(); groupiter.hasNext();){
						Groups group = groupiter.next();
						for (Iterator<models.User> useriter = models.Groups.getUsersForGroup(group.id).iterator(); useriter.hasNext();){
							if (useriter.next().id == dbuser.id){
								hilf = true;
							}
						}
					}
					dbchan.users.add(dbuser);
					dbchan.saveManyToManyAssociations("users");
					if (hilf == false){
						websocket.json.out.Channel mychan = new websocket.json.out.Channel();
						WebSocketNotifier.sendMessagetoUser(JsonBinder.bindtoJson(mychan.genOutMessage(dbchan, userid, "create")), dbuser.id);
					}
				}
			}
			else{
				dbchan.users.remove(dbuser);
				dbchan.saveManyToManyAssociations("users");
				for(Iterator<models.Groups> groupiter = Groups.getChannelGroups(dbchan.id).iterator(); groupiter.hasNext();){
					Groups group = groupiter.next();
					for (Iterator<models.User> useriter = models.Groups.getUsersForGroup(group.id).iterator(); useriter.hasNext();){
						if (useriter.next().id == dbuser.id){
							hilf = true;
						}
					}
				}
				if (hilf == false){
					websocket.json.out.Channel mychan = new websocket.json.out.Channel();
					WebSocketNotifier.sendMessagetoUser(JsonBinder.bindtoJson(mychan.genOutMessage(dbchan, userid, "delete")), dbuser.id);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbchan;
	}
}
