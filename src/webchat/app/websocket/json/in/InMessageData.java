package websocket.json.in;

import java.util.ArrayList;
import java.util.List;

import websocket.message.IMessageData;

public class InMessageData extends IMessageData {
	public String message;
	public String type;
	public int channel_id;
}
