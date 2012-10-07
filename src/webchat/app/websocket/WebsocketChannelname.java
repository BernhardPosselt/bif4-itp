package websocket;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

import websocket.json.in.InChannelName;
import websocket.json.out.Channel;
import websocket.json.out.Status;
import websocket.message.IMessageSender;

public class WebsocketChannelname implements IMessageSender {
	
	/*public static void createChannelname(JsonNode inmessage, WebSocket.Out<JsonNode> out, int userid){
		int channelid = InChannelName.changechannelname(inmessage);
    	if (channelid == -1)
    		out.write(Status.genStatus("fail", "Could not change Channelname; Channelname already exists!"));
    	else
    		WebsocketNotifier.notifyAllMembers(Channel.genChannel("update", channelid));
	}*/
	
	@Override
	public void sendMessage(JsonNode outmessage) {
		WebsocketNotifier.notifyAllMembers(outmessage);
	}
}
