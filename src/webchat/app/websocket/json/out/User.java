package websocket.json.out;

import java.util.*;

import org.codehaus.jackson.JsonNode;


import play.db.ebean.Model;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;


public class User implements IOutMessage{
	public String type;
	public UserData data = new UserData();
	public String action;
	
	public User(){
		type = "user";
	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);		
	}
	
	@Override
	public IOutMessage genOutMessage(Model dbmodel) {
		User outuser = null;
		try {
			outuser = new User();	
			models.User dbuser = (models.User) dbmodel;
			
			UserData udata = new UserData();
			udata.email = dbuser.email;
			udata.lastname = dbuser.lastname;
			udata.username = dbuser.username;
			udata.firstname = dbuser.firstname;
			udata.status = dbuser.status;
			udata.modified = new Date();
		
			for (Iterator<models.Groups> itgroup = dbuser.groups.iterator(); itgroup.hasNext();){
				udata.groups.add(itgroup.next().id);
			}
			
			outuser.data = udata;
			outuser.action = "create";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outuser;
	}
}