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
	
	
	public List<Integer> mods = new ArrayList<Integer>();
	
	public List<Integer> getMods(){
		return mods;
	}
	
	public void setMods(int mod){
		this.mods.add(mod);
	}
	
	
	public List<Integer> readonly = new ArrayList<Integer>();
	
	public List<Integer> getReadonly(){
		return readonly;
	}
	
	public void setReadonly(int rouser){
		readonly.add(rouser);
	}
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> users = new ArrayList<User>();
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(User user) {
		this.users.add(user);
	}

	@ManyToMany(cascade=CascadeType.ALL)
	public List<Groups> groups = new ArrayList<Groups>();
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Groups group) {
		this.groups.add(group);
	}
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<File> files = new ArrayList<File>();
	
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(File file) {
		this.files.add(file);
	}

	public static Finder<Integer,Channel> find = new Finder<Integer,Channel>(
			Integer.class, Channel.class
	);
	
	public static Channel getbyId (int id){
		return find.byId(id);
	}
	
    public static List<Channel> findAll(){
        return find.all();
    }
	
	public static List<Channel> getUserChannels(int userid)
    {
		List<Channel> tmp = new ArrayList<Channel>();
		String query = "find channel where archived = false and (users.id =:userid or is_public=true)";
        tmp =  find.setQuery(query).setParameter("userid", userid).findList();
        return tmp;
    }
	
	public static List<Channel> getUserallChannels(int userid)
    {
		List<Channel> tmp = new ArrayList<Channel>();
		String query = "find channel where archived = false and (users.id =:userid or is_public=true)";
        tmp =  find.setQuery(query).setParameter("userid", userid).findList();
        for (Groups group : models.User.getGroupsForUser(userid)){
        	for (models.Channel chan : group.channels){
        		if (!tmp.contains(chan))
        			tmp.add(chan);
        	}
        }
        return tmp;
    }

	public static List<Channel> getFileChannels(int fileid)
    {
		List<Channel> tmp = new ArrayList<Channel>();
        tmp =  find.where().eq("files.id", fileid).findList();
        return tmp;
    }
	
	public static List<Channel> getAllPublicChannel()
    {
		List<Channel> tmp = new ArrayList<Channel>();
        tmp =  find.where().eq("is_public", true).findList();
        return tmp;
    }
	
	public static List<Integer> getChannelUsers(int channelid) {
		List<Integer> users = new ArrayList<Integer>();
		for (Iterator<User> iterator= find.byId(channelid).users.iterator(); iterator.hasNext();){
			users.add(iterator.next().id);
		}
		return users;
	}
	
	public static List<Integer> getallChannelUsers(int channelid) {
		List<Integer> users = new ArrayList<Integer>();
		for (Iterator<User> iterator= find.byId(channelid).users.iterator(); iterator.hasNext();){
			users.add(iterator.next().id);
		}
		for (Groups group : models.Channel.getGroupsForChannel(channelid)){
        	for (models.User usr : group.users){
        		if (!users.contains(usr.id))
        			users.add(usr.id);
        	}
        }
		return users;
	}
	
	public static List<User> getUsersForChannel(int channelid)
    {
    	List<User> users = new ArrayList<User>();
    	Channel tmp = new Channel();
    	tmp = find.byId(channelid);
    	for (Iterator<User> iterator= find.byId(channelid).users.iterator(); iterator.hasNext();){
			users.add(iterator.next());
		}
    	return users;
    }
    
    public static List<User> getUsersNotForChannel(int channelid)
    {
    	List<User> users = new ArrayList<User>();
    	users = User.find.all();
    	Channel tmp = new Channel();
    	tmp = find.byId(channelid);
    	for (Iterator<User> iterator= find.byId(channelid).users.iterator(); iterator.hasNext();){
			users.remove(iterator.next());
		}
    	return users;
    }
    
    public static List<Groups> getGroupsForChannel(int channelid)
    {
    	List<Groups> groups = new ArrayList<Groups>();
    	Channel tmp = new Channel();
    	tmp = find.byId(channelid);
    	for (Iterator<Groups> iterator= find.byId(channelid).groups.iterator(); iterator.hasNext();){
			groups.add(iterator.next());
		}
    	return groups;
    }
    
    public static List<Groups> getGroupsNotForChannel(int channelid)
    {
    	List<Groups> groups = new ArrayList<Groups>();
    	groups = Groups.find.all();
    	Channel tmp = new Channel();
    	tmp = find.byId(channelid);
    	for (Iterator<Groups> iterator= find.byId(channelid).groups.iterator(); iterator.hasNext();){
			groups.remove(iterator.next());
		}
    	return groups;
    }
	
}
