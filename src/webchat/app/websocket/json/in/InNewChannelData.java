package websocket.json.in;

import websocket.Interfaces.IMessageData;

public class InNewChannelData implements IMessageData{
	public String name;
	public String topic;
	public Boolean is_public;
}
