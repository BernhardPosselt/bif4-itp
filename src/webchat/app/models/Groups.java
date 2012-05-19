package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.api.libs.Crypto;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import scala.reflect.generic.Trees.This;

@Entity
public class Groups extends Model {

	@Id
	public int id;
	
	@Required
	public String name;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date modified;

	
	@ManyToMany(mappedBy="groups")
	public List<Channel> channels;
	
	@ManyToMany(mappedBy="groups")
	public List<User> users;
	
	public static Finder<Integer,Groups> find = new Finder<Integer,Groups>(
			Integer.class, Groups.class
	);
    
    public static List<Groups> getUserGroups(int userid)
    {
		List<Groups> tmp = new ArrayList<Groups>();
        tmp =  find.where().eq("users.id", userid).findList();
        
        return tmp;
    }

}
