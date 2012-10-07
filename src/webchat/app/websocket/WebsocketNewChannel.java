package websocket;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.json.in.InNewChannel;
import websocket.json.out.Channel;
import websocket.json.out.Status;

public class WebsocketNewChannel {
	public static void createNewChannel(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid){
		/*Boolean is_public = inmessage.findPath("is_public").asBoolean();
    	int channelid = InNewChannel.createnewchannel(inmessage, userid);
    	if (channelid == -1)
    		out.write(Status.genStatus("fail", "Could not create Channel; Channelname already exists!"));
    	else{
    		if (is_public == false)
    			out.write(Channel.genChannel("create", channelid));
    		else
    			WebsocketNotifier.notifyAllMembers(Channel.genChannel("create", channelid));
    	}*/
	}
}
