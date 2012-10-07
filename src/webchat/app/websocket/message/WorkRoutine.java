package websocket.message;

import play.db.ebean.Model;

public class WorkRoutine {
	public IInMessage inmessage;
	public IOutMessage outmessage;
	public Model model;
	public String dbaction;
	public IMessageSender sender;
	//public ArrayList<Integer> acl;
}
