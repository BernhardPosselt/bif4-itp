package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Groups extends Model {

	@Id
	public int id;
	
	@Required
	public String name;
	
	@ManyToMany(mappedBy="groups")
	public List<Channel> channels;
}
