package websocket.json.out;

import java.util.Date;
import java.util.List;

import websocket.message.IMessageData;

import models.Channel;

public class MessageData extends IMessageData{
	public int id;
	public int user_id;
	public String content;
	public String type;
	public Date date;
	public Date modified;	
}

