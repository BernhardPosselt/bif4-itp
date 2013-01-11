package websocket.json.in;

import websocket.Interfaces.IMessageData;

public class InMessageData implements IMessageData {
	public String message;
	public String type;
	public int channel_id;
}
