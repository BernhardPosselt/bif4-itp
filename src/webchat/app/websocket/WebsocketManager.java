package websocket;


import java.util.*;

import models.Groups;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import websocket.message.MessageFactory;
import websocket.message.MessageHandler;
import websocket.message.NotifyInit;
import websocket.message.WebSocketNotifier;


public class WebsocketManager {

    public static Map<WebSocket.Out<JsonNode>,Integer> members = new HashMap<WebSocket.Out<JsonNode>, Integer>();

    static boolean init = true;
    /**
     * Creates a new Websocket class and puts it in the map
     * @return the newly intialized websocket
     */
    public static WebSocket<JsonNode> getWebsocket(final int userId){
        return new WebSocket<JsonNode>() {

	        // Called when the Websocket Handshake is done.
	        public void onReady(WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out){
	        	if(!members.containsKey(userId))
            		members.put(out, userId);
	        	MessageFactory.registerMessage();
        		List<Integer> userlist = new ArrayList<Integer>();
        		userlist.add(userId);
        		models.User.setUseronline(userId);   
                websocket.json.out.User usrnot = new websocket.json.out.User();
                usrnot.sendMessage(usrnot.genOutMessage(models.User.getbyId(userId), userId, "update"));
        		NotifyInit.sendInit(userlist, userId);
        		init = false;
            
	        	// For each event received on the socket,
                in.onMessage(new Callback<JsonNode>() {
                    public void invoke(JsonNode event) {
                        // Send a Talk message to the room.
                        try {
                        	
                            MessageHandler.handleMessage(event,userId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                // When the socket is closed.
                in.onClose(new Callback0() {
                    public void invoke() {
                        // Send a Quit message to the room.
                    	Boolean hilf = true;
                    	members.remove(out);
                    	//out.write(Status.genStatus("ok", "WebSocket Closed"));
                        if (members.values().contains(userId)){
                        	hilf = false;
                        }
                    	if (hilf.equals(true)){
                    		models.User.setUseroffline(userId);
                    		websocket.json.out.User usrnot = new websocket.json.out.User();
                            usrnot.sendMessage(usrnot.genOutMessage(models.User.getbyId(userId), userId, "update"));	
                    	}
                    }
                });
	        };
        };
    }

    
  /*  public static void onReceive(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid) throws Exception {
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
        	out.write(File.genjoinFile(channelid));
        	out.write(Channel.genChannel("update", channelid));
        	out.write(Message.genjoinMessage(channelid));
        }
        else if (type.equals("invite")){
        	int channelid  = inmessage.findPath("channel").asInt();
        	List<Integer> oldusers = models.Channel.getChannelUsers(channelid);
        	for (Iterator<models.User> useriter = models.User.getChannelGroupUser(Groups.getChannelGroups(channelid)).iterator(); useriter.hasNext();){
          		int olduser = useriter.next().id;
          		if (!oldusers.contains(olduser))
          			oldusers.add(olduser);
          	}
        	List<Integer>users  = InInvite.invite(inmessage);   	
        	sendMessagetoUser(oldusers, channelid, "update");
        	sendMessagetoUser(users, channelid, "create");
        }
        else if (type.equals("newchannel")){
        	Boolean is_public = inmessage.findPath("is_public").asBoolean();
        	int channelid = InNewChannel.createnewchannel(inmessage, userid);
        	if (channelid == -1)
        		out.write(Status.genStatus("fail", "Could not create Channel; Channelname already exists!"));
        	else{
        		if (is_public == false)
        			out.write(Channel.genChannel("create", channelid));
        		else
        			notifyAllMembers(Channel.genChannel("create", channelid));
        	}
        
        }
        else if (type.equals("channeltopic")){
        	int channelid = InChanneltopic.savetopicchange(inmessage);
        	notifyAllMembers(Channel.genChannel("update", channelid));
        }
        else if (type.equals("kick")){
        	int channelid  = inmessage.findPath("channel").asInt();
        	List<Integer>users  = InKick.kick(inmessage); 
          	List<Integer> stayusers = models.Channel.getChannelUsers(channelid);
          	for (Iterator<models.User> useriter = models.User.getChannelGroupUser(Groups.getChannelGroups(channelid)).iterator(); useriter.hasNext();){
          		int stayuser = useriter.next().id;
          		if (!stayusers.contains(stayuser))
          			stayusers.add(stayuser);
          	}
        	sendMessagetoUser(stayusers, channelid, "update");
        	sendMessagetoUser(users, channelid, "delete");
        }
        else if (type.equals("channelname")){
        	int channelid = InChannelName.changechannelname(inmessage);
        	if (channelid == -1)
        		out.write(Status.genStatus("fail", "Could not change Channelname; Channelname already exists!"));
        	else
        		notifyAllMembers(Channel.genChannel("update", channelid));
        }
        else if (type.equals("channeldelete")){
        	int channelid = inmessage.findPath("channel").asInt();
        	notifyAllMembers(Channel.genChannel("delete", channelid));
        	InChannelDelete.deletechannel(inmessage);
        }
        else if (type.equals("channelclose")){
        	int channelid = InChannelClose.closechannel(inmessage);
        	notifyAllMembers(Channel.genChannel("delete", channelid));
        }
        else if (type.equals("profileupdate")){
        	JsonNode error = InProfileUpdate.updateprofile(inmessage, userid);
        	if (!error.isNull())
        		out.write(error);
        	else
        		notifyAllMembers(User.genUserchanged(userid, "update"));
        }
        else if (type.equals("filedelete")){
        	List<Integer> channels = InFileDelete.filedelete(inmessage);
        	for (Iterator<Integer> iterator = channels.iterator(); iterator.hasNext();)
        		notifyAllMembers(Channel.genChannel("update", iterator.next()));
        }
        else if (type.equals("ping")){
        	
        }
        else{
            out.write(Status.genStatus("critical", "Unknown type of InMessage!"));
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
    
    public static void sendMessagetoUser(List<Integer>users, int channelid, String action){
    	WebSocket.Out<JsonNode> out = null;
        for(Map.Entry<WebSocket.Out<JsonNode>, Integer> entry: members.entrySet()) {
            if (users.contains(entry.getValue())){
            	out = (WebSocket.Out<JsonNode>)entry.getKey();
            	out.write(Channel.genChannel(action, channelid));
            }	
        }
    }*/
}