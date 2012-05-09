package websockets;

import java.util.*;

import json_models.*;

import models.Channel;
import models.User;

import org.apache.commons.lang.UnhandledException;
import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class Channelverwaltung {
	
	public static void join(WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) throws Exception{
    
        
        // For each event received on the socket,
        in.onMessage(new Callback<JsonNode>() {
           public void invoke(JsonNode event) {
               
               // Send a Talk message to the room.
        	   
               String join = Auth.genAuth();
               out.write(Json.parse(join));
               
           } 
        });
        
        // When the socket is closed.
        in.onClose(new Callback0() {
           public void invoke() {
               
               // Send a Quit message to the room.
        	   String errorString = Auth.genAuth();
        	   Channel channel = new Channel();
              // notifyAll(errorString, channel);
               
           }
        });
	}
    

	public void onReceive(Object message) throws Exception {
	
	if(message instanceof Join) {
	    
	    // Received a Join message
	    Join join = (Join)message;
	    
	    // Check if this username is free.
	    /*if(users.contains(join.username)) {
	        join.channel.write("This username is already used");
	    } else {
	        members.put(join.username, join.channel);
	        notifyAll("join", join.username, "has entered the room");
	        join.channel.write("OK");
	    }*/
	    
	} else if(message instanceof Talk)  {
	    
	    // Received a Talk message
	    Talk talk = (Talk)message;
	    
	    //notifyAll("talk", talk.username, talk.text);
	    
	} else if(message instanceof Quit)  {
	    
	    // Received a Quit message
	    Quit quit = (Quit)message;
	    
	    members.remove(quit.username);
	    
	    //notifyAll("quit", quit.username, "has leaved the room");
	
	} else {
	    throw new UnhandledException(null);
	}
	
	}
	
	Map<User, WebSocket.Out<JsonNode>> members = new HashMap<User, WebSocket.Out<JsonNode>>();
	
	//Send a Json event to all members
	public void notifyAll(JsonNode inmessage) {
		for(WebSocket.Out<JsonNode> channel: members.values()) {
			List<JsonNode> help = new ArrayList<JsonNode>();
			
			
			List<Channel> mychannels = null; 
		    //JsonNode event = Message.genMessage(text, channels);
		    //channel.write(event);
		}
	}
	
	//-- Messages
	
	public static class Join {
	
		final String username;
		final WebSocket.Out<String> channel;
		
		public Join(String username, WebSocket.Out<String> channel) {
		    this.username = username;
		    this.channel = channel;
		}
	
	}
	
	public static class Talk {
	
		final String username;
		final String text;
		
		public Talk(String username, String text) {
		    this.username = username;
		    this.text = text;
		}
	
	}
	
	public static class Quit {
	
		final String username;
		
		public Quit(String username) {
		    this.username = username;
		}
	}
}

