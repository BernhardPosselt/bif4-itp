package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
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
		try{
			InInviteUser inuser = (InInviteUser) inmessage;
			models.User dbuser = models.User.getbyId(inuser.data.users);
			dbchan = models.Channel.getbyId(inuser.data.id);
			if (inuser.data.value == true && !dbchan.users.contains(dbuser))
				dbchan.users.add(dbuser);
			else
				dbchan.users.remove(dbuser);
			dbchan.saveManyToManyAssociations("users");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbchan;
	}
}
