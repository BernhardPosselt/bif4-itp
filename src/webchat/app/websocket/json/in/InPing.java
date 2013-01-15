package websocket.json.in;

import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

public class InPing implements IInMessage {
  
	Timer timer = new Timer();
	int usid = 0;
	public String type;
	public InPingData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("ping")){
			return true;
		}
		else
			return false;
	}

	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InPing();
		myroutine.action = "";
		myroutine.dbmodel = null;
		myroutine.outmessage = null;
		return myroutine;
	}

	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		usid = userid;
		models.User dbuser = models.User.getbyId(usid);
		if (dbuser.status.equals("idle")){
			dbuser.status= "online";
			dbuser.save();
			websocket.json.out.User wsuser = new websocket.json.out.User();
			wsuser.sendMessage(wsuser.genOutMessage(dbuser, usid, "update"));
		}
		timer.cancel();
		timer = new Timer();
	    timer.schedule(new IdleTask(), 60*1000);
		return null;
	}
	
	class IdleTask extends TimerTask {
	    public void run() {
	    	models.User dbuser = models.User.getbyId(usid);
			dbuser.status= "idle";
			dbuser.save();
			websocket.json.out.User wsuser = new websocket.json.out.User();
			wsuser.sendMessage(wsuser.genOutMessage(dbuser, usid, "update"));
	    }
	}

}
