package websocket.json.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;
import websocket.Interfaces.IOutMessage;
import websocket.message.JsonBinder;
import websocket.message.WebSocketNotifier;

public class JoinMessage{

	static HashMap<Integer,ArrayList<Integer>> joinMessages = new HashMap<Integer,ArrayList<Integer>>();
	
	
	public static void sendMessage(IOutMessage outmessage) {
		JsonNode outjson = JsonBinder.bindtoJson(outmessage);
		WebSocketNotifier.notifyAllMembers(outjson);	
	}

	public static IOutMessage genOutMessage(int userid, int channelid, String action) {
		Message outmessage = null;
		try {
			ArrayList<Integer> mlist = null;
			if (joinMessages.get(userid) != null)
				mlist = joinMessages.get(userid);
			else
				mlist = new ArrayList<Integer>();
			for (Iterator<models.Message> miter = models.Message.getallChannelMessages(channelid).iterator(); miter.hasNext(); )
			{
				models.Message dbmessage = (models.Message) miter.next();
				//if (!mlist.contains(dbmessage.id))
				//{
					mlist.add(dbmessage.id);
					
					outmessage = new Message();
					MessageData mdata = new MessageData();
					mdata.date = dbmessage.date;
					mdata.message = StringEscapeUtils.escapeHtml(dbmessage.message);
					
					if (dbmessage.type.equals("text"))
						mdata.message = mdata.message.replaceAll("\n", "<br/>");
					mdata.id = dbmessage.id;
					mdata.type = dbmessage.type;
					mdata.owner_id = dbmessage.owner_id.id;
					mdata.modified = dbmessage.modified;
					mdata.channel_id = dbmessage.channel_id.id;
					outmessage.data = mdata;
					outmessage.action = action;		
					JoinMessage.sendMessage(outmessage);
				//}
			}
			joinMessages.put(userid, mlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outmessage;
	}
}
