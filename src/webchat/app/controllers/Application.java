package controllers;

import java.util.Date;

import play.mvc.*;
import views.html.*;
import websocket.WebsocketManager;

//import java.nio.channels.MembershipKey;
import play.mvc.Controller;

import org.codehaus.jackson.JsonNode;

import models.*;

public class Application extends Controller {
  
	 static int userid;

    /**
     * Displays the index page
     * @return
     */
	 public static Result index() {

         if(session("userid") != null) //User logged in
         {
             userid = Integer.parseInt(session("userid"));
             return ok(index.render(User.getUsername(userid)));
         }
         else //User doesn't logged in
         {
             return redirect(routes.LoginController.login());
         }
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
            user1.setPassword("test");
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
	  	   
	  	    Groups group = new Groups();
	  	    group.modified = new Date();
	  	    group.name = "Group1";
	  	    group.users.add(user);
	  	    group.channels.add(channel);
	  	    group.save();
	  	    group.saveManyToManyAssociations("channels");
	  	    group.saveManyToManyAssociations("users");
	  	    return ok(index.render("testdata"));
	 }
	 
	 
	 public static WebSocket<JsonNode> websocket() {
         int userId;
         if(session("userid") != null){
             userId = Integer.parseInt(session("userid"));
            return WebsocketManager.getWebsocket(userId);
	    }
	    return null;
     }
}