package websocket.json.in;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InMessage implements IInMessage{
	public String type;
	public InMessageData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("message"))
			return true;
		else
			return false;
	}
	
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InMessage();
		myroutine.outmessage = new websocket.json.out.Message();
		myroutine.action = "create";
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InMessage();
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.Message dbmsg = null;
		try{
			InMessage inmsg = (InMessage)inmessage;
			dbmsg = new models.Message();
			dbmsg.channel_id = models.Channel.getbyId(inmsg.data.channel_id);
			dbmsg.date = new Date();
			dbmsg.modified = new Date();
			dbmsg.owner_id = models.User.getbyId(userid);
			dbmsg.type = inmsg.data.type;
			dbmsg.message = StringEscapeUtils.escapeSql(inmsg.data.message);
			dbmsg.save();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dbmsg;
	}
}
