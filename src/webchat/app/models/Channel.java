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

import jsonmodelsout.Auth;
import jsonmodelsout.Message;
import jsonmodelsout.Status;

import play.db.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Entity
public class Channel extends Model {

	@Id
	public int id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String topic;
	
	@Constraints.Required
	public Boolean isread;
	
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
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<File> files;

	public static Finder<Integer,Channel> find = new Finder<Integer,Channel>(
			Integer.class, Channel.class
	);
	
}
