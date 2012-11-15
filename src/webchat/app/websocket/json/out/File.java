package websocket.json.out;


import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;

public class File implements IOutMessage{
	public String type;
	public String action;
	public FileData data = new FileData();
	
	public File(){
		this.type = "file";
	}

	@Override
	public void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);
	}

	@Override
	public IOutMessage genOutMessage(Model dbmodel, int userid) {
		File outfile = null;
		try{
			outfile = new File();
			models.File dbfile = (models.File) dbmodel;
			FileData fdata = new FileData();
			fdata.modified = dbfile.date;
			fdata.name = dbfile.name;
			fdata.owner_id = dbfile.owner_id.id;
			fdata.size = dbfile.size;
			fdata.mimetype = dbfile.mimetype;
			outfile.action = "create";
			outfile.data = fdata;	
			} 
		catch (Exception e) {	 
			 e.printStackTrace();
		}
		return outfile;
	}
}
