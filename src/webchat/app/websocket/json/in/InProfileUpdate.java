package websocket.json.in;

import models.Channel;
import models.User;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InProfileUpdate {
	public String type;
	public InProfileUpdateData data;
	
	public static void updateprofile(JsonNode inmessage, int userid){
		try{
			InProfileUpdate inprofileupdate = new InProfileUpdate();
			inprofileupdate = new JSONDeserializer<InProfileUpdate>().deserialize(
					inmessage.toString(), InProfileUpdate.class);
			models.User user = new models.User();
			user = User.find.byId(userid);
			user.firstname = inprofileupdate.data.prename;
			user.lastname = inprofileupdate.data.lastname;
			user.username = inprofileupdate.data.username;
			user.setPassword(inprofileupdate.data.password);
			user.email = inprofileupdate.data.email;
			user.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
