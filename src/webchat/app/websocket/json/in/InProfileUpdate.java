package websocket.json.in;

import java.util.Iterator;
import java.util.Map;

import models.Channel;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

import websocket.WebsocketManager;
import websocket.json.out.Status;
import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InProfileUpdate extends IInMessage {

	public InProfileUpdateData data;
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("profileupdate"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine = new WorkRoutine();
		myroutine.inmessage = new InProfileUpdate();
		myroutine.model = new models.User();
		myroutine.outmessage = new websocket.json.out.User();
		myroutine.dbaction = "update";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InProfileUpdate();
	}
	
	/*public static JsonNode updateprofile(JsonNode inmessage, int userid){
		JsonNode error = null;
		try{
			InProfileUpdate inprofileupdate = new InProfileUpdate();
			inprofileupdate = new JSONDeserializer<InProfileUpdate>().deserialize(
					inmessage.toString(), InProfileUpdate.class);
			models.User user = new models.User();
			user = User.find.byId(userid);
			user.firstname = inprofileupdate.data.prename;
			user.lastname = inprofileupdate.data.lastname;
			for (Iterator<models.User> useriter = models.User.find.all().iterator(); useriter.hasNext();)	{
				if (useriter.next().username.equals(inprofileupdate.data.username)){
					error = Status.genStatus("fail", "Could not change Username; Username already exists!");
					return error;
				}	
			}
			user.username = inprofileupdate.data.username;
			if (!inprofileupdate.data.password.isEmpty())
				user.setPassword(inprofileupdate.data.password);
			
			user.email = inprofileupdate.data.email;
			user.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return error;
	}*/

}
