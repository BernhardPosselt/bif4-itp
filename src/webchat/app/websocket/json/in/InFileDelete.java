package websocket.json.in;

import java.util.*;

import models.Channel;
import models.File;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;


import websocket.Interfaces.IInMessage;
import websocket.message.WorkRoutine;


public class InFileDelete implements IInMessage {
	public String type;
	public InFileDeleteData data;
	
	@Override
	public boolean canHandle(JsonNode inmessage) {
		if (inmessage.findPath("type").asText().equals("filedelete"))
			return true;
		else
			return false;
	}
	@Override
	public WorkRoutine getWorkRoutine() {
		WorkRoutine myroutine=new WorkRoutine();
		myroutine.inmessage = new InFileDelete();
		myroutine.outmessage=new websocket.json.out.Channel();
		myroutine.action = "update";
		return myroutine;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new InFileDelete();
	}
	@Override
	public Model savetoDB(IInMessage inmessage, int userid) {
		models.File dbfile = null;
		try{
			InFileDelete infile = (InFileDelete) inmessage;
			dbfile = new models.File();
			dbfile = File.find.byId(infile.data.id);
			for (Iterator<models.Channel> chaniter = models.Channel.getFileChannels(dbfile.id).iterator(); chaniter.hasNext();){
				models.Channel chan = new Channel();
				chan  = chaniter.next();
				chan.files.remove(dbfile);
				chan.update();
			}
			java.io.File dest = new java.io.File(play.Play.application().path().toString() + "/files/" + dbfile.filename);
			dest.delete();
			dbfile.deleted = true;
			dbfile.update();
		}catch (Exception e){
			e.printStackTrace();
		}
		return dbfile;
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
