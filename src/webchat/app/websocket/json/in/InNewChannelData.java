package websocket.json.in;

import java.util.*;

import websocket.message.IMessageData;

public class InNewChannelData extends IMessageData{
	public String name;
	public String topic;
	public Boolean is_public;
}
