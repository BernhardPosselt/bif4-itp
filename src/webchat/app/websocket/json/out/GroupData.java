package websocket.json.out;

import java.util.Date;

import websocket.message.IMessageData;

public class GroupData extends IMessageData {
	public int id;
	public String name;
	public Date modified = new Date();
}
