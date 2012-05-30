package websocket.json.out;

import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import flexjson.JSONException;
import flexjson.JSONSerializer;

public class File {
	public final String type;
	public Boolean init;
	public Map<Integer,String> actions = new HashMap<Integer,String>();
	public Map<Integer,FileData> data = new HashMap<Integer,FileData>();
	
	public File(){
		this.type = "file";
	}
	
	public static JsonNode genjoinFile(int channelid){
		String json = "", action = "create";
		Boolean init = true;
		File file = new File();
		file.init = init;
		try{
			for (Iterator<models.File> iterator = models.File.getChannelFiles(channelid).iterator(); iterator.hasNext();)
			{
				models.File dbfile= new models.File();
				dbfile = iterator.next();
				FileData fdata = new FileData();
				fdata.modified = dbfile.date;
				fdata.name = dbfile.name;
				fdata.owner_id = dbfile.uid.id;
				fdata.size = dbfile.size;
				fdata.type = dbfile.type;
				file.actions.put(dbfile.id, action);
				file.data.put(dbfile.id, fdata);
				
			}
			JSONSerializer fser = new JSONSerializer().include("*.actions", "*.data");
			json = fser.exclude("*.class").serialize(file);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
	
	public static JsonNode gennewFile(models.File dbfile){
		String json = "";
		File file = new File();
		file.init = false;
		try{
			
			
			FileData fdata = new FileData();
			fdata.modified = dbfile.date;
			fdata.name = dbfile.name;
			fdata.owner_id = dbfile.uid.id;
			fdata.size = dbfile.size;
			fdata.type = dbfile.type;
			file.actions.put(dbfile.id, "create");
			file.data.put(dbfile.id, fdata);
				
			JSONSerializer fser = new JSONSerializer().include("*.actions", "*.data");
			json = fser.exclude("*.class").serialize(file);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return Json.parse(json);
	}
}
