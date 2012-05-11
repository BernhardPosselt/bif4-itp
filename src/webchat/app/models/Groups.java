package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Groups extends Model {

	@Id
	public int id;
	
	@Required
	public String name;
	
	@ManyToMany(mappedBy="groups")
	public List<Channel> channels;
	
	@ManyToMany(mappedBy="groups")
	public List<User> users;
	
	public static Finder<Integer,Groups> find = new Finder<Integer,Groups>(
			Integer.class, Groups.class
	);
}
