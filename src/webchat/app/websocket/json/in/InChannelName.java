package websocket.json.in;


import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;


public class InChannelName extends IInMessage{
	public InChannelNameData data;
	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("channelname"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InChannelName();
		myroutine.outmessage = new websocket.json.out.Channel();
		myroutine.model = new models.Channel();
		myroutine.dbaction = "update";
		myroutine.sender = new Notifyall();
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InChannelName();
	}
	
	
	
	/*public static int changechannelname (JsonNode inmessage){
		int channelid = 0;
		try{
			InChannelName inchan = new InChannelName();
			inchan = new JSONDeserializer<InChannelName>().deserialize(
					inmessage.toString(), InChannelName.class);
			models.Channel chan = new models.Channel();
			chan = Channel.find.byId(inchan.data.channel);
			channelid = inchan.data.channel;
			for (Iterator<Channel> channeliter = Channel.find.all().iterator(); channeliter.hasNext();){
				if (channeliter.next().name.equals(inchan.data.name.trim()))
					return -1;
			}
			chan.name = inchan.data.name;
			chan.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channelid;
	}*/
}
