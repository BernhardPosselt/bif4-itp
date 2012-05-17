package websocket;

//import java.nio.channels.MembershipKey;
import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import websocket.json.out.Auth;
import websocket.json.out.Channel;
import websocket.json.out.Group;
import websocket.json.out.Message;
import websocket.json.out.User;

public class WebsocketManager {

    public static Map<Integer, WebSocket.Out<JsonNode>> members = new HashMap<Integer, WebSocket.Out<JsonNode>>();

    /**
     * Creates a new Websocket class and puts it in the map
     * @return the newly intialized websocket
     */
    public static WebSocket getWebsocket(final int userId){
        return new WebSocket<JsonNode>() {

	        // Called when the Websocket Handshake is done.
	        public void onReady(WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out){
                
	        	// For each event received on the socket,
                in.onMessage(new Callback<JsonNode>() {
                    public void invoke(JsonNode event) {
                        // Send a Talk message to the room.
                        try {
                            onReceive(event, out, userId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                // When the socket is closed.
                in.onClose(new Callback0() {
                    public void invoke() {
                        // Send a Quit message to the room.
                        System.out.println("Socket closed");
                    }
                });
	        };
        };
    }

    public static void onReceive(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid) throws Exception {
    	if(!members.containsKey(userid))
    		members.put(userid, out);
        String type = inmessage.findPath("type").asText();
        if(type.equals("message")) {
            notifyAllMembers(Message.genMessage(inmessage, userid));
        }
        else if (type.equals("init")){
        	 String action = "create";
          	 Boolean init = true;
        	 notifyAllMembers(Group.genGroup(userid,action, init));
      		 notifyAllMembers(Channel.genChannel(userid,action, init));
      		 notifyAllMembers(User.genUser(userid,action, init));
        }
        else if (type.equals("auth")){

        }
        else{
            //Fehler
        }

    }



    //Send a Json event to all members
    public static void notifyAllMembers(JsonNode inmessage) {
        for(WebSocket.Out<JsonNode> socket: members.values()) {
            socket.write(inmessage);
        }
    }
}