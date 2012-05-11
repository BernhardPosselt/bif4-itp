package controllers;

import play.*;

import play.mvc.*;
import views.html.*;
import websockets.Channelverwaltung;

//import java.nio.channels.MembershipKey;
import java.util.*;
import play.mvc.Controller;

import org.codehaus.jackson.JsonNode;

import models.*;

public class Application extends Controller {
  
	 static int userid;
	 public static Result index() 
	 {

         userid = Integer.parseInt(session("userid"));

         return ok(index.render(User.getUsername(Integer.parseInt(session("userid")))));
	 }
	 
	 public static Result filltestdata()
	 {
		   User user = new User();
	        user.username = "Glembo";
            user.setPassword("test");
	        user.email = "a.b@aon.at";

	        user.lastname = "Huber";
	        user.firstname = "Ernst";
	        user.online = false;
	        user.save();
	        
	        User user1 = new User();
	        user1.username = "MasterLindi";
            user.setPassword("test");
	        user1.email = "christoph.lindmaier@gmx.at";
	        user1.lastname = "Lindmaier";
	        user1.firstname = "Christoph";
	        user1.online = false;
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
	  	    return ok(index.render("testdata"));
	  	    
	 }
	 
	 
	 public static WebSocket<JsonNode> chat() {
	        return new WebSocket<JsonNode>() {
	            // Called when the Websocket Handshake is done.
	            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
	                
	                try { 
	                	
	                    Channelverwaltung.join(in, out, userid);
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        };
	    }
	

}