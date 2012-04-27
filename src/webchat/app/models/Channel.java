package models;


import javax.persistence.*;
import javax.validation.Constraint;

import java.util.*;

import play.db.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

public class Channel extends Model {

	@Id
	int id;
	
	@Constraints.Required
	String name;
	
	@Constraints.Required
	String topic;
	
	@Constraints.Required
	Boolean read;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> users;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<File> groups;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Group> files;
}
