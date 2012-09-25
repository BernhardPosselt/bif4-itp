package websocket;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.json.out.ActiveUser;
import websocket.json.out.Channel;
import websocket.json.out.Group;
import websocket.json.out.User;

public class WebsocketInit {
	public static void createInit(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid){
		models.User.setUseronline(userid);
    	out.write(Group.geninitGroup());
    	out.write(Channel.geninitChannel(userid));
  	 	out.write(User.geninitUser(userid));
  	 	out.write(ActiveUser.genActiveUser(userid));
  	 	WebsocketNotifier.notifyAllMembers(User.genUserchanged(userid, "update"));
	}
}
