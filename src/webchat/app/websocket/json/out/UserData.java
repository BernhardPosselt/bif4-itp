package websocket.json.out;

import java.util.*;

import websocket.message.IMessageData;

public class UserData extends IMessageData{
	public int id;
	public String username;
	public String status;
	public String firstname;
	public String lastname;
	public String email;
	public List<Integer> groups = new ArrayList<Integer>();
	public Date modified  = new Date();
	
	public List<Integer> getGroups() {
		return groups;
	}
	public void setGroups(int groupid) {
		this.groups.add(groupid);
	}
	
	
}
