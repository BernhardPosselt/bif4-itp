package controllers;

import org.jivesoftware.smack.*;
import play.mvc.*;
import play.mvc.Controller;

public class JabberTest extends Controller {
	
	public static Result index() {
		
		//Connect
		ConnectionConfiguration config = new ConnectionConfiguration("localhost", 5222);
		Connection conn = new XMPPConnection(config);
		try {
			conn.connect();
		} catch (XMPPException e) {
		    System.out.println("Error connecting to server, Error: " + e);
		}
		
		//Login
		try {
			conn.login("markus", "markus");
		} catch (XMPPException e2) {
		    System.out.println("Error logging in, Error: " + e2);
		}
		
		//Send Message
		ChatManager cm = conn.getChatManager();	
		Chat c = cm.createChat("glembo@webchat", new MessageListener() {
			@Override
			public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
		        System.out.println("Received message: " + message);
			}
		});
		try {
		    c.sendMessage("Zeas!");
		}
		catch (XMPPException e3) {
		    System.out.println("Error delivering message, Error: " + e3);
		}
		
		//Listen (Receiving)
		conn.getChatManager().addChatListener(new ChatManagerListener()
		{
			public void chatCreated(final Chat chat, final boolean createdLocally)
		    {
				chat.addMessageListener(new MessageListener()
				{
					public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message)
					{
						System.out.println("Received message: " + (message != null ? message.getBody() : "NULL"));
					}
				});
		    }
		});
		
		//Listen for 20 seconds
		final long start = System.nanoTime();
		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e4) {
				System.out.println("Error: " + e4);
				e4.printStackTrace();
			}
		}		
		
		//Disconnect
		conn.disconnect();

//		AccountManager am = new AccountManager(conn);
//		am.createAccount("testuser", "test");
//		
//		System.out.println(String.valueOf(conn.isAuthenticated()));
		
//		websocket.json.out.Message m = new websocket.json.out.Message();
//		ObjectNode data = injson.objectNode();
//		ObjectNode injson = Json.newObject();
//		ObjectNode dat = Json.newObject();
//		ArrayNode channel = data.arrayNode();
//		channel.add(1);
//		channel.add(2);
//		injson.put("type", "message");
//		dat.put("message", String.valueOf(conn.isAuthenticated()));
//		dat.put("type", "text");
//		dat.putArray("channel").addAll(channel);
//		data.putAll(dat);
//		injson.putObject("data").putAll(data);
//		WebsocketNotifier.notifyAllMembers(m.genMessage(injson, 1));
		
		return ok();
    }
}
