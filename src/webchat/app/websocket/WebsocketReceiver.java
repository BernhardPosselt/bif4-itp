package websocket;

import java.util.Iterator;
import java.util.List;

import chatbot.ChatbotManager;
import models.Groups;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.json.out.Status;


public class WebsocketReceiver {



	public static void getType (JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid){
		if(!WebsocketManager.members.containsKey(userid))
    		WebsocketManager.members.put(out, userid);
        String type = inmessage.findPath("type").asText();
        if(type.equals("message")) {
            WebsocketMessage.createMessage(inmessage, userid);
        }
        else if (type.equals("init")){
        	WebsocketInit.createInit(inmessage, out, userid);
        }  
        else if (type.equals("join")){
        	WebsocketJoin.createJoin(inmessage, out);
        }
        else if (type.equals("invite")){
        	WebsocketInvite.createInvite(inmessage);
        }
        else if (type.equals("newchannel")){
        	WebsocketNewChannel.createNewChannel(inmessage, out, userid);
        }
        else if (type.equals("channeltopic")){
        	WebsocketChanneltopic.createChanneltopic(inmessage);
        }
        else if (type.equals("kick")){
        	WebsocketKick.createKick(inmessage, out, userid);
        }
        else if (type.equals("channelname")){
        	WebsocketChannelname.createChannelname(inmessage, out, userid);
        }
        else if (type.equals("channeldelete")){
        	WebsocketFiledelete.createFiledelete(inmessage);
        }
        else if (type.equals("channelclose")){
        	WebsocketChannelclose.createChannelclose(inmessage);
        }
        else if (type.equals("profileupdate")){
        	WebsocketProfileupdate.createProfileupdate(inmessage, out, userid);
        }
        else if (type.equals("filedelete")){
        	WebsocketFiledelete.createFiledelete(inmessage);
        }
        else if (type.equals("ping")){
        	
        }
        else{
            out.write(Status.genStatus("critical", "Unknown type of InMessage!"));
        }
	}
}
