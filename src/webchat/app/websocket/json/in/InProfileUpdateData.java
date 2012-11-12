package websocket.json.in;

import websocket.Interfaces.IMessageData;

public class InProfileUpdateData implements IMessageData{
	public int id;
	public String username;
	public String password;
	public String email;
	public String firstname;
	public String lastname;	
}
