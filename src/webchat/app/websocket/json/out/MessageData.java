package websocket.json.out;

import java.util.Date;
import websocket.Interfaces.IMessageData;

public class MessageData implements IMessageData{
	public int id;
	public int owner_id;
	public int channel_id;
	public String message;
	public String type;
	public Date date;
	public Date modified;	
}

