package websocket.json.out;

import java.util.*;

import websocket.Interfaces.IMessageData;

public class FileData implements IMessageData{
	public int id;
	public String name;
	public String mimetype;
	public double size;
	public int owner_id;
	public Date modified = new Date();
}
