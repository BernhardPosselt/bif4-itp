package websocket.json.out;

import java.util.*;

public class UserData {
	public String username;
	public Boolean online;
	public String prename;
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
