package websocket.json.out;

import java.util.*;

import websocket.Interfaces.IMessageData;

public class ChannelData implements IMessageData {
	public int id;
	public String name;
	public String topic;
	public List<Integer> files = new ArrayList<Integer>();
	public List<Integer> users = new ArrayList<Integer>();
	public List<Integer> groups = new ArrayList<Integer>();
	public final Date modified = new Date();
	public List<Integer> mod = new ArrayList<Integer>();
	public List<Integer> readonly = new ArrayList<Integer>();
}
