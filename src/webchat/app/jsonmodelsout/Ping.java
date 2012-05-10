package jsonmodelsout;

import flexjson.JSONException;
import flexjson.JSONSerializer;

public class Ping {
	public final String type;
	public final String[] data;
	
	public Ping(){
		this.type = "ping";
		this.data = null;
	}

}

