package websocket;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;
import websocket.json.in.InJoin;
import websocket.json.out.Channel;
import websocket.json.out.File;
import websocket.json.out.Message;

public class WebsocketJoin {
	public static void createJoin(JsonNode inmessage, WebSocket.Out<JsonNode> out){
		int channelid = InJoin.getchannel(inmessage);
    	out.write(File.genjoinFile(channelid));
    	out.write(Channel.genChannel("update", channelid));
    	out.write(Message.genjoinMessage(channelid));
	}
}
