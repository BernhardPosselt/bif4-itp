package websocket.json.in;

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
		return myroutine;
	}
	@Override
	public Model savetoDB(IInMessage inmessage) {
		models.Groups dbgroup = null;
		try{
			InInviteGroup ingroup = (InInviteGroup) inmessage;
			dbgroup = models.Groups.getbyId(ingroup.data.groups);
			models.Channel dbchan = models.Channel.getbyId(ingroup.data.id);
			dbchan.groups.add(dbgroup);
			dbchan.saveManyToManyAssociations("groups");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbgroup;
	}
}
