package websocket;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import websocket.json.in.InFileDelete;
import websocket.json.out.Channel;

public class WebsocketFiledelete {
	public static void createFiledelete(JsonNode inmessage){
		List<Integer> channels = InFileDelete.filedelete(inmessage);
    	for (Iterator<Integer> iterator = channels.iterator(); iterator.hasNext();)
    		WebsocketNotifier.notifyAllMembers(Channel.genChannel("update", iterator.next()));
	}
}
