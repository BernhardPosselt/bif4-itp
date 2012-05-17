package models;

import javax.persistence.*;
import javax.validation.Constraint;

import org.apache.commons.lang.UnhandledException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

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
	public String content;
	
	@Constraints.Required
	public String type;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date date;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date modified;
	
	@ManyToOne
	public User user;
	
	@ManyToMany(mappedBy="messages")
	public List<Channel> channels;
	
	public static Finder<Integer,Message> find = new Finder<Integer,Message>(
			Integer.class, Message.class
	);
	
	public static List<Message> getallChannelMessages (int channelid){
		List<Message> mlist = new ArrayList<Message>();
		mlist = find.where().eq("channels.id", channelid).findList();
		return mlist;
	}
}
