package controllers;

import play.mvc.*;
import views.html.*;
import websockets.Channelverwaltung;

import org.codehaus.jackson.JsonNode;

import models.*;

public class Application extends Controller {
  
	 public static Result index() 
	 {
		    return ok(index.render());
	 }
	 
	 public static Result filltestdata()
	 {
		   User user = new User();
	        user.setUsername("Glembo");
            user.setPassword("test");
	        user.setEmail("a.b@aon.at");
	        user.setLastname("Huber");
	        user.setPrename("Ernst");
	        user.setOnline(false);
	        user.save();
	        
	        User user1 = new User();
	        user1.setUsername("MasterLindi");
            user1.setPassword("test");
	        user1.setEmail("christoph.lindmaier@gmx.at");
	        user1.setLastname("Lindmaier");
	        user1.setPrename("Christoph");
	        user1.setOnline(false);
	        user1.save();
	         
	        Channel channel = new Channel();
	  	    channel.name = "Channel 1";
	  	    channel.topic = "Webengineering";
	  	    channel.isread = false;
	  	    channel.save();
	  	   
	  	    Channel channel1 = new Channel();
	  	    channel1.name = "Channel2";
	  	    channel1.topic = "Softwareengineering";
	  	    channel1.isread = true;
	  	    channel1.save();
	  	    
	  	    channel1.setUsers(user);
	  	    channel1.saveManyToManyAssociations("users");
	  	   
	  	    channel.setUsers(user); 
	  	    channel.setUsers(user1);
	  	    channel.saveManyToManyAssociations("users");
	  	    return ok(index.render());
	  	    
	 }
	 
	 public static Result chatRoom(String username) {
	        if(username == null || username.trim().equals("")) {
	            flash("error", "Please choose a valid username.");
	            return redirect(routes.Application.index());
	        }
	        return ok(index.render());
	    }
	 
	 public static WebSocket<JsonNode> chat() {
	        return new WebSocket<JsonNode>() {
	            
	            // Called when the Websocket Handshake is done.
	            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
	                
	                // Join the channel.
	                try { 
	                    Channelverwaltung.join(in, out);
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        };
	    }
	

}