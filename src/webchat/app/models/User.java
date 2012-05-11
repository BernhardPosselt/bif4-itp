package models;

import javax.persistence.*;

import java.util.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class User extends Model {
	
	@Id
    public int id;
	
	@Constraints.Required
    public String username;

    @Constraints.Required
    public String password;

    public Boolean online;
	
	@Constraints.Required
    public String prename;
	
	@Constraints.Required
    public String lastname;
	
	@Constraints.Required
    public String email;

	@ManyToMany(mappedBy="users")
	public List<Channel> channels;
	
	public static Finder<Integer,User> find = new Finder<Integer,User>(
			Integer.class, User.class
	);

    public static String getUsername(int id)
    {
        return find.byId(id).username;
    }

    public static boolean authenticate(String name, String pw)
    {
        User tmp = find.where().eq("username", name).eq("password", pw).findUnique();

        if(tmp == null)
            return false;
        return true;
    }

}

	
