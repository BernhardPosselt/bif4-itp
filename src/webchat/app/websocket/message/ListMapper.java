package websocket.message;

import java.util.HashMap;

import play.db.ebean.Model;

public class ListMapper {
	
	public static HashMap<String,Model> modelmap = new HashMap<String,Model>();
	
	public static Model getmapModel(String fieldname){
		return modelmap.get(fieldname);
	}
	
	public static void registerMap(String fieldname, Model inmodel){
		modelmap.put(fieldname, inmodel);
    }
}
