package jabber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;


import controllers.Application;

public class MucChannel {

    public static void createMucChannel(String channelname, String channeltopic,  int userid, int channelid)
    {
    	try{
	    	if(!Application.mucchannels.containsKey(channelid))
			{
				//create MUC
				MultiUserChat muc = new MultiUserChat(Application.conn, channelname+"@conference.webchat");
				System.out.println("Channelname: " + channelname);
				muc.create(channelname);
				
				Application.mucchannels.put(channelid, muc);
				
				Form form = muc.getConfigurationForm();
				Form submitForm = form.createAnswerForm();
				
			    for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
			    	FormField field = (FormField) fields.next();
			    	if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
			    		submitForm.setDefaultAnswer(field.getVariable());
			    	}
			    }
	
			    List<String> owners = new ArrayList<String>();
			    owners.add("webchat@webchat");
			    submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			    muc.sendConfigurationForm(submitForm);
			    muc.invite(models.User.find.byId(userid).username + "@webchat/Smack", "Einladung in Channel " + channelname);
			    final int chanid = channelid;
			    muc.sendMessage("Willkommen: Topic lautet " + channeltopic + "!");
			    muc.addMessageListener(new PacketListener() {
			    	@Override
					public void processPacket(Packet packet) {
						if (packet instanceof Message) {
							String[] temp;
							temp = ((Message)packet).getFrom().split("/");
							if (!temp[1].equals(models.Channel.find.byId(chanid).name))
							{
								System.out.println("Received user: " + models.User.getUserID(temp[1]));
							
								websocket.message.WebSocketNotifier.notifyAllMembers(websocket.json.out.Message.genJabberMessage(((Message)packet).getBody() , models.User.getUserID(temp[1]), chanid));
							}
						} else if (packet instanceof Presence) {
							System.out.println(packet);
						}
					}
			    });
			}
			else
			{
				MultiUserChat muc = Application.mucchannels.get(channelid);
				muc.invite(models.User.find.byId(userid).username + "@webchat/Smack", "Einladung in Channel " + channelname);
				muc.sendMessage("Willkommen: Topic lautet" + channeltopic + "!");
			}
    	}catch(XMPPException exp){
    		exp.printStackTrace();
    	}
    }
    
    public static void sendMucMessage(String message, int chanid){
    	try{
    		if (Application.mucchannels.containsKey(chanid)){
				MultiUserChat muc = Application.mucchannels.get(chanid);
				muc.sendMessage(message);
    		}
		}
    	catch(XMPPException exp){
    		exp.printStackTrace();
    	}
    }
}
