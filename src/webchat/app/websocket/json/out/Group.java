package websocket.json.out;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;


public class Group implements IOutMessage {
	public String type;
	public GroupData data = new GroupData();
	public String action;
	
	public Group(){
		this.type = "group";
	}
	

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);
	}


	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid, String action) {
		Group outgroup = null;
		try{
			outgroup = new Group();
			models.Groups dbgroup = (models.Groups) dbmodel;
			GroupData gdata = new GroupData();
			gdata.id = dbgroup.id;
			gdata.modified = dbgroup.modified;
			gdata.name = dbgroup.name;
			outgroup.action = action;
			outgroup.data = gdata;
		}catch(Exception e){
			e.printStackTrace();
		}
		return outgroup;
	}
}
