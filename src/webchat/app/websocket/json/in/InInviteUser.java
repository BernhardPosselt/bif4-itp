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
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage) {
		models.User dbuser = null;
		try{
			InInviteUser inuser = (InInviteUser) inmessage;
			dbuser = models.User.getbyId(inuser.data.users);
			models.Channel dbchan = models.Channel.getbyId(inuser.data.id);
			dbchan.users.add(dbuser);
			dbchan.saveManyToManyAssociations("users");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbuser;
	}
}
