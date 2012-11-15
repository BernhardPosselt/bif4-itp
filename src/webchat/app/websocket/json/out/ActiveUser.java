package websocket.json.out;



import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;

public class ActiveUser implements IOutMessage  {
	public String type;
	public String action;
	public ActiveUserData data = new ActiveUserData();
	
	public ActiveUser(){
		this.type = "activeuser";
		this.action = "create";
	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);
	}

	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid) {
		ActiveUser acuser = null;
		try {
			acuser = new ActiveUser();
			acuser.data.id = userid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acuser;
	}
}
