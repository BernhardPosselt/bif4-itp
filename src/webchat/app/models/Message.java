package models;

import javax.persistence.*;
import javax.validation.Constraint;

import org.apache.commons.lang.UnhandledException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.avaje.ebean.validation.Length;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;

import flexjson.JSON;

import java.util.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;

@Entity
public class Message extends Model {
	
	@Id
	public int id;
	
	@Constraints.Required
	@Column(columnDefinition = "LONGTEXT")
	public String message;
	
	@Constraints.Required
	public String type;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date date = new Date();
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date modified = new Date();
	
	@ManyToOne
	public User owner_id;
	
	@ManyToOne
	public Channel channel_id;
	
	public static Finder<Integer,Message> find = new Finder<Integer,Message>(
			Integer.class, Message.class
	);
	
	public static Message getbyId (int id){
		return find.byId(id);
	}
	
	public static List<Message> getallChannelMessages (int channelid){
		List<Message> mlist = new ArrayList<Message>();
		mlist = find.where().eq("channels.id", channelid).where().orderBy().asc("modified").findList();
		return mlist;
	}
	
	public static List<Message> getallUserMessages (int userid){
		List<Message> mlist = new ArrayList<Message>();
		mlist = find.where().eq("user.id", userid).findList();
		return mlist;
	}
}
