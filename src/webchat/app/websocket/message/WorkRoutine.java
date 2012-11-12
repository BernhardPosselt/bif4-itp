package websocket.message;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.Interfaces.IOutMessage;

public class WorkRoutine {
	public IInMessage inmessage;
	public IOutMessage outmessage;
	public Model dbmodel;
	public String action;
}
