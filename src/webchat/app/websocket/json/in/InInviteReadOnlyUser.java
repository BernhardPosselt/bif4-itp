package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InInviteReadOnlyUser implements IInMessage {
	public String type;
	public InInviteReadOnlyUserData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("readonlyuser"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteReadOnlyUser();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "update";
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel dbchan = null;
		try{
			InInviteReadOnlyUser inmoduser = (InInviteReadOnlyUser) inmessage;
			models.User dbuser = models.User.getbyId(inmoduser.data.users);
			dbchan = models.Channel.getbyId(inmoduser.data.id);
			if (inmoduser.data.value == true){
				if (!dbchan.readonly.contains(dbuser)){
					dbchan.readonly.add(dbuser);
				}
			}
			else{
				if(dbchan.readonly.contains(dbuser))
					dbchan.readonly.remove(dbuser);
			}
			dbchan.save();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbchan;
	}

}
