package websocket.json.out;

import java.util.*;

public class ChannelData {
	public String name;
	public String topic;
	public List<Integer> files = new ArrayList<Integer>();
	public List<Integer> users = new ArrayList<Integer>();
	public List<Integer> groups = new ArrayList<Integer>();
	public Date modified;
}
