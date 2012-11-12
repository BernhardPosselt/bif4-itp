package websocket.json.in;


import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;


public class InChannelName implements IInMessage{
	public String type;
	public InChannelNameData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("channelname"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InChannelName();
		myroutine.outmessage = new websocket.json.out.Channel();
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelName();
	}
	
	@Override
	public Model savetoDB(IInMessage inmessage) {
		models.Channel chan = null;
		try{
			InChannelName inchan = (InChannelName) inmessage;
			chan = new models.Channel();
			chan = models.Channel.find.byId(inchan.data.id);
			for (Iterator<models.Channel> channeliter = models.Channel.find.all().iterator(); channeliter.hasNext();){
				if (channeliter.next().name.equals(inchan.data.name.trim()))
					return null;
			}
			chan.name = inchan.data.name;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return chan;
	}
}
