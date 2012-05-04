package models;


import javax.persistence.*;
import javax.validation.Constraint;

import java.util.*;

import play.db.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

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
