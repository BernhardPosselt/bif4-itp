package websocket.json.out;

import java.util.Date;

import websocket.Interfaces.IMessageData;

public class GroupData implements IMessageData {
	public int id;
	public String name;
	public Date modified = new Date();
}
