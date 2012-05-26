package websocket.json.in;

import java.util.*;

public class InNewChannelData {
	public String name;
	public String topic;
	public List<Integer> groups = new ArrayList<Integer>();
	public List<Integer> users = new ArrayList<Integer>();
	public Boolean priv;
}
