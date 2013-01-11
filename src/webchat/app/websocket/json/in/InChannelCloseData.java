package websocket.json.in;

import websocket.Interfaces.IMessageData;

public class InChannelCloseData implements IMessageData{
	public int id;
	public boolean archived;
}
