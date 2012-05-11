package websockets;

import java.nio.channels.MembershipKey;
import java.util.*;

import jsonmodelsout.*;

import models.Channel;
import models.User;
import jsonmodelsin.*;
import jsonmodelsout.*;

import org.apache.commons.lang.UnhandledException;
import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class Channelverwaltung {
	
	public static Map<Integer, WebSocket.Out<JsonNode>> members = new HashMap<Integer, WebSocket.Out<JsonNode>>();
	
	public static void join(WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) throws Exception{
    
        // For each event received on the socket,
        in.onMessage(new Callback<JsonNode>() {
           public void invoke(JsonNode event) {
               
               // Send a Talk message to the room.
        	   
        	   try {
				onReceive(event,out);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
           } 
        });
        
        // When the socket is closed.
        in.onClose(new Callback0() {
           public void invoke() {
               
               // Send a Quit message to the room.
        	   JsonNode errorNode = Auth.genAuth();
        	   notifyAllMembers(errorNode);
               
           }
        });
	}
    

	public static void onReceive(JsonNode inmessage, WebSocket.Out<JsonNode> out) throws Exception {
	
		String type = inmessage.findPath("type").asText();
		if(type.equals("message")) {
			notifyAllMembers(Message.genMessage(inmessage));
		}
		else if (type.equals("ping")){
			
		}
		else if (type.equals("auth")){
			
		}
		else{
			//Fehler
		}
	
	}
	
	
	
	//Send a Json event to all members
	public static void notifyAllMembers(JsonNode inmessage) {
		for(WebSocket.Out<JsonNode> channel: members.values()) {
			channel.write(inmessage);		
		}
	}
}

