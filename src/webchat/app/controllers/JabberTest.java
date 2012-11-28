package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

import play.mvc.*;

public class JabberTest extends Controller {
	
	public static Result index() throws XMPPException {
		
		//Connect
		ConnectionConfiguration config = new ConnectionConfiguration("204.62.14.78", 5222);
		Connection conn = new XMPPConnection(config);
		try {
			conn.connect();
		} catch (XMPPException e) {
		    System.out.println("Error connecting to server, Error: " + e);
		}
		
		//Login
		try {
			conn.login("webchat", "test");
		} catch (XMPPException e2) {
		    System.out.println("Error logging in, Error: " + e2);
		}
		
		//Create Chatroom
		MultiUserChat muc = new MultiUserChat(conn,"testraum@conference.webchat");
		muc.create("testraum");
		
		Form form = muc.getConfigurationForm();
		Form submitForm = form.createAnswerForm();
		
	    for (Iterator fields = form.getFields(); fields.hasNext();) {
	    	FormField field = (FormField) fields.next();
	    	if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
	    		submitForm.setDefaultAnswer(field.getVariable());
	    	}
	    }

	    List owners = new ArrayList();
	    owners.add("webchat@webchat");
	    submitForm.setAnswer("muc#roomconfig_roomowners", owners);
	    muc.sendConfigurationForm(submitForm);
	    
	    muc.invite("glembo@webchat/Smack", "testeinladung");
	    
	    muc.sendMessage("Servas burschn!");
	    muc.addMessageListener(new PacketListener() {
	    	@Override
			public void processPacket(Packet packet) {
				if (packet instanceof Message) {
					System.out.println("Received message: " + (packet != null ? ((Message)packet).getFrom() + " " + ((Message)packet).getBody() : "NULL"));
				} else if (packet instanceof Presence) {
					System.out.println(packet);
				}
			}
    	});
	    
	    /*
		//Send Message
		ChatManager cm = conn.getChatManager();	
		Chat c = cm.createChat("glembo@webchat", new MessageListener() {
			@Override
			public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
				System.out.println("Received message: " + (message != null ? message.getBody() : "NULL"));
				System.out.println(message);
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
						System.out.println(message);
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
 */
		
		return ok();
    }
}
