package controllers;



import play.libs.Json;
import models.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Time;
import java.util.*;

import json_models.Auth;
import json_models.Message;
import json_models.Ping;

import org.codehaus.jackson.node.*;
import org.codehaus.jackson.*;





import flexjson.*;
import play.mvc.*;

public class JsonHandling extends Controller {
	
	public static Result genMessage()
	{
		String msg = "", type = "";
		List<Channel> channels = null;
		String json = "";
		try{
			Message m = new Message();
			m.data.message = msg;
			m.data.type = type;
			m.data.channels = channels;
			
			JSONSerializer mser = new JSONSerializer();
			json = mser.exclude(".class").serialize(m);
		}catch (JSONException e){
			e.printStackTrace();
		}
		return ok(json);
	}
	
	public static String genStatus(String level, String msg)
	{
		String json = "";
		try{
			
			models.Status s = new models.Status();
			s.data.level = level;
			s.data.msg = msg;
			JSONSerializer sser = new JSONSerializer();
			json = sser.exclude("*.class").serialize(s);
			
		 } catch (JSONException e) {	 
			 e.printStackTrace();
		 }
		return json;
	}
	
	public static String genPing(){
		String json = "";
		try{
			
			Ping p = new Ping();
			JSONSerializer sser = new JSONSerializer();
			json = sser.exclude("*.class").serialize(p);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return json;
	}
	
	public static String genAuth(String sessionid){
		String json = "";
		try{
			
			Auth a = new Auth();
			a.data.sessionid = sessionid;
			JSONSerializer aser = new JSONSerializer();
			json = aser.exclude("*.class").serialize(a);
			} 
		catch (JSONException e) {	 
			 e.printStackTrace();
		}
		return json;
	}

}
