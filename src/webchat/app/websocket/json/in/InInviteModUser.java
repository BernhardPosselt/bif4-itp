package websocket.json.in;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InInviteModUser implements IInMessage {
	public String type;
	public InInviteModUserData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("moduser"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InInviteModUser();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "update";
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel dbchan = null;
		try{
			InInviteModUser inmoduser = (InInviteModUser) inmessage;
			models.User dbuser = models.User.getbyId(inmoduser.data.users);
			dbchan = models.Channel.getbyId(inmoduser.data.id);
			if (inmoduser.data.value == true){
				if (!dbchan.mods.contains(dbuser)){
					dbchan.mods.add(dbuser);
				}
			}
			else{
				if (dbchan.mods.contains(dbuser))
						dbchan.mods.remove(dbuser);
			}
			dbchan.save();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbchan;
	}

}
