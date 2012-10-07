package websocket.json.in;

import java.util.*;

import models.Channel;
import models.File;

import org.codehaus.jackson.JsonNode;

import websocket.message.IInMessage;
import websocket.message.Notifyall;
import websocket.message.WorkRoutine;

import flexjson.JSONDeserializer;

public class InFileDelete extends IInMessage {
	public InFileDeleteData data;
	
	@Override
	public boolean canHandle(String type) {
		if (type.equals("filedelete"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InFileDelete();
		myroutine.outmessage=new websocket.json.out.Channel();
		myroutine.model = new models.File();
		myroutine.dbaction = "delete";
		myroutine.sender = new Notifyall();
		return null;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InFileDelete();
	}
	
	/*public static List<Integer> filedelete (JsonNode inmessage){
		List<Integer> channellist = new ArrayList<Integer>();
		try{
			InFileDelete infile = new InFileDelete();
			infile = new JSONDeserializer<InFileDelete>().deserialize(
					inmessage.toString(), InFileDelete.class);
			
			models.File dbfile = new models.File();
			dbfile = File.find.byId(infile.data.file);
			for (Iterator<models.Channel> chaniter = models.Channel.getFileChannels(dbfile.id).iterator(); chaniter.hasNext();){
				models.Channel chan = new Channel();
				chan  = chaniter.next();
				chan.files.remove(dbfile);
				channellist.add(chan.id);
				chan.update();
			}
			java.io.File dest = new java.io.File(play.Play.application().path().toString() + "/files/" + dbfile.filename);
			dest.delete();
			dbfile.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channellist;
	}*/
	
}
