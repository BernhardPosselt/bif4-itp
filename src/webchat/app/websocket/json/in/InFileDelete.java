package websocket.json.in;

import java.util.*;

import models.Channel;
import models.File;

import org.codehaus.jackson.JsonNode;

import flexjson.JSONDeserializer;

public class InFileDelete {
	public String type;
	public InFileDeleteData data;
	
	public static List<Integer> filedelete (JsonNode inmessage){
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
			dbfile.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
		return channellist;
	}
	
}
