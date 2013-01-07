package websocket.json.in;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InInviteGroup implements IInMessage{
	public String type;
	public InInviteGroupData data = new InInviteGroupData();
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("invitegroup"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteGroup();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "update";
		return myroutine;
	}
	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel dbchan = null;
		try{
			InInviteGroup ingroup = (InInviteGroup) inmessage;
			models.Groups dbgroup = models.Groups.getbyId(ingroup.data.groups);
			dbchan = models.Channel.getbyId(ingroup.data.id);
			if (ingroup.data.value == true){
				if (!dbchan.groups.contains(dbgroup)){
					List<models.User> allusers = dbchan.users;
					for (models.Groups groups : dbchan.groups){
						for (models.User usr : groups.users){
							if (!allusers.contains(usr))
								allusers.add(usr);
						}
					}
					for (models.User groupusr : dbgroup.users){
						if (!allusers.contains(groupusr)){
							websocket.json.out.Channel mychan = new websocket.json.out.Channel();
							mychan.sendMessagetoUser(mychan.genOutMessage(dbchan, userid, "create"), groupusr.id);
						}
					}
					dbchan.groups.add(dbgroup);
					dbchan.saveManyToManyAssociations("groups");
				}
			}
			else{
				dbchan.groups.remove(dbgroup);
				dbchan.saveManyToManyAssociations("groups");
				List<models.User> allusers = dbchan.users;
				for (models.Groups groups : dbchan.groups){
					for (models.User usr : groups.users){
						if (!allusers.contains(usr))
							allusers.add(usr);
					}
				}
				for (models.User groupusr : dbgroup.users){
					if (!allusers.contains(groupusr)){
						websocket.json.out.Channel mychan = new websocket.json.out.Channel();
						mychan.sendMessagetoUser(mychan.genOutMessage(dbchan, userid, "delete"), groupusr.id);
					}
				}
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbchan;
	}
}
