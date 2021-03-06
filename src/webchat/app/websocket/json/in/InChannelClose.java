package websocket.json.in;

import websocket.Interfaces.IInMessage;
import websocket.message.AccessControl;
import websocket.message.WorkRoutine;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;




public class InChannelClose implements IInMessage {

	public String type;
	public InChannelCloseData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("channelclose"))
			return true;
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InChannelClose();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.action = "delete";
		return myroutine;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelClose();
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Channel chan = null;
		try{
			InChannelClose inchan = (InChannelClose)inmessage;
			if (AccessControl.IsAdmin(userid) || AccessControl.IsMod(userid, inchan.data.id))
			{
				chan = new models.Channel();
				chan = models.Channel.find.byId(inchan.data.id);
				chan.archived = true;
				chan.update();
			}
			else
				websocket.json.out.Status.genStatus("error", "Not allowed to close channel!", userid);
		}catch (Exception e){
			e.printStackTrace();
		}
		return chan;
	}
}
