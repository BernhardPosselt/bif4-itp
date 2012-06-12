package models;


import javax.persistence.*;
import javax.validation.Constraint;

import org.apache.commons.lang.UnhandledException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;

import flexjson.JSON;
import models.*;
import java.util.*;

import play.db.ebean.Model;
import play.data.validation.*;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;


@Entity
public class Channel extends Model {

	@Id
	public int id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String topic;
	
	@Constraints.Required
	public Boolean is_public;
	
	@Constraints.Required
	public Boolean archived;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(User user) {
		this.users.add(user);
	}

	@ManyToMany(cascade=CascadeType.ALL)
	public List<Groups> groups;
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Groups group) {
		this.groups.add(group);
	}
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Message> messages;
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(Message message) {
		this.messages.add(message);
	}
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<File> files;
	
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(File file) {
		this.files.add(file);
	}

	public static Finder<Integer,Channel> find = new Finder<Integer,Channel>(
			Integer.class, Channel.class
	);
	
	public static List<Channel> getUserChannels(int userid)
    {
		List<Channel> tmp = new ArrayList<Channel>();
		String query = "find channel where archived = false and (users.id =:userid or is_public=true)";
        tmp =  find.setQuery(query).setParameter("userid", userid).findList();
        return tmp;
    }

	public static List<Channel> getFileChannels(int fileid)
    {
		List<Channel> tmp = new ArrayList<Channel>();
        tmp =  find.where().eq("files.id", fileid).findList();
        return tmp;
    }
	
	public static List<Integer> getChannelUsers(int channelid) {
		List<Integer> users = new ArrayList<Integer>();
		for (Iterator<User> iterator= find.byId(channelid).users.iterator(); iterator.hasNext();){
			users.add(iterator.next().id);
		}
		return users;
	}
	
}
