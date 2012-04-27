package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

public class Group extends Model {

	@Id
	public int id;
	
	@Required
	public String name;
	
	@ManyToMany(mappedBy="groups")
	public List<Channel> channels;
}
