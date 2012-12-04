package websocket.json.in;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InProfileUpdate implements IInMessage {
	public String type;
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
		myroutine.outmessage = new websocket.json.out.User();
		myroutine.action = "update";
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InProfileUpdate();
	}
	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.User user = null;
		try{
			InProfileUpdate inprofileupdate = new InProfileUpdate();
			inprofileupdate = new JSONDeserializer<InProfileUpdate>().deserialize(
					inmessage.toString(), InProfileUpdate.class);
			user = new models.User();
			user = models.User.find.byId(userid);
			user.firstname = inprofileupdate.data.firstname;
			user.lastname = inprofileupdate.data.lastname;
			for (Iterator<models.User> useriter = models.User.find.all().iterator(); useriter.hasNext();)	{
				if (useriter.next().username.equals(inprofileupdate.data.username)){
					return null;
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
		return user;
	}
}
