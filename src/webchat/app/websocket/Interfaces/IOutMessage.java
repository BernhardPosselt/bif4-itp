package websocket.Interfaces;

import play.db.ebean.*;

public interface IOutMessage extends IMessage{

	public abstract IOutMessage genOutMessage(Model dbmodel, int userid, String action);
	public abstract void sendMessage(IOutMessage outmessage);
}
