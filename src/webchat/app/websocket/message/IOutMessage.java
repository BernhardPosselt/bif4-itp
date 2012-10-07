package websocket.message;

import websocket.json.out.ChannelData;


public abstract class IOutMessage extends IMessage{
	public IMessageData data;
	public String action;	
}
