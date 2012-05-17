package websocket.json.in;

import java.util.*;

public class InInvite {
	public String type;
	public Map<Integer,InInviteData> data = new HashMap<Integer,InInviteData>();
	public Map<Integer,String> actions = new HashMap<Integer,String>();
}
