package websocket;

//import java.nio.channels.MembershipKey;
import java.util.*;

import org.codehaus.jackson.JsonNode;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Http.Session;
import play.mvc.WebSocket;
import websocket.json.in.InChanneltopic;
import websocket.json.in.InInvite;
import websocket.json.in.InJoin;
import websocket.json.in.InNewChannel;
import websocket.json.out.ActiveUser;
import websocket.json.out.Channel;
import websocket.json.out.File;
import websocket.json.out.Group;
import websocket.json.out.Message;
import websocket.json.out.Status;
import websocket.json.out.User;

public class WebsocketManager {

    public static Map<WebSocket.Out<JsonNode>,Integer> members = new HashMap<WebSocket.Out<JsonNode>, Integer>();

    /**
     * Creates a new Websocket class and puts it in the map
     * @return the newly intialized websocket
     */
    public static WebSocket<JsonNode> getWebsocket(final int userId){
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
                    	models.User.setUseroffline(userId);
                    	notifyAllMembers(User.genUserchanged(userId, "update"));
                    	members.remove(userId);
                        out.write(Status.genStatus("ok", "WebSocket Closed"));
                     
                    }
                });
	        };
        };
    }

    public static void onReceive(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid) throws Exception {
    	if(!members.containsKey(userid))
    		members.put(out, userid);
        String type = inmessage.findPath("type").asText();
        if(type.equals("message")) {
            notifyAllMembers(Message.genMessage(inmessage, userid));
        }
        else if (type.equals("init")){
        	models.User.setUseronline(userid);
        	out.write(Group.geninitGroup(userid));
        	out.write(Channel.geninitChannel(userid));
      	 	out.write(User.geninitUser(userid));
      	 	out.write(ActiveUser.genActiveUser(userid));
      	 	notifyAllMembers(User.genUserchanged(userid, "update"));
        }
        else if (type.equals("join")){
        	int channelid = InJoin.getchannel(inmessage);
        	//notifyAllMembers(File.genjoinFile(userid, "create", true, channelid));
        	out.write(Message.genjoinMessage(channelid));
        	
        }
        else if (type.equals("invite")){
        	int channelid  = inmessage.findPath("channel").asInt();
        	List<Integer> olduser = models.Channel.getChannelUsers(channelid);
        	List<Integer>users  = InInvite.invite(inmessage);   	
        	sendinviteMessagetoUser(olduser, channelid, "update");
        	sendinviteMessagetoUser(users, channelid, "create");
        }
        else if (type.equals("newchannel")){
        	Boolean is_public = inmessage.findPath("is_public").asBoolean();
        	int channelid = InNewChannel.createnewchannel(inmessage, userid);
        	if (is_public == false)
        			out.write(Channel.genChannel("create", channelid));
        	else
        		notifyAllMembers(Channel.genChannel("create", channelid));
        }
        else if (type.equals("channeltopic")){
        	int channelid = InChanneltopic.savetopicchange(inmessage);
        	notifyAllMembers(Channel.genChannel("update", channelid));
        }
        else{
            //Fehler
        }

    }


    //Send a Json event to all members
    public static void notifyAllMembers(JsonNode inmessage) {
    	WebSocket.Out<JsonNode> out = null;
        for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: members.entrySet()) {
            out = (WebSocket.Out<JsonNode>)entry.getKey();
            out.write(inmessage);
        }
    }
    
    public static void sendinviteMessagetoUser(List<Integer>users, int channelid, String action){
    	WebSocket.Out<JsonNode> out = null;
        for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: members.entrySet()) {
            if (users.contains(entry.getValue())){
            	out = (WebSocket.Out<JsonNode>)entry.getKey();
            	out.write(Channel.genChannel(action, channelid));
            }	
        }
    }
}