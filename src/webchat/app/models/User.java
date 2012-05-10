package models;

import javax.persistence.*;
import javax.validation.Constraint;

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
	private String password;
	
	public void setPassword(String newpassword) {
		password = Crypto.sign(newpassword);
	}
	
	public Boolean checkPassword(String newpassword) {
		if(password == Crypto.sign(newpassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Constraints.Required
	public String email;	
	
	@Constraints.Required
	public String firstname;
	
	@Constraints.Required
	public String lastname;
	
	@Constraints.Required
	public Boolean online;
	
	@Constraints.Required
	public Boolean admin;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date lastlogin;

	@ManyToMany(mappedBy="users")
	public List<Channel> channels;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Groups> groups;
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Groups group) {
		this.groups.add(group);
	}
	
	public static Finder<Integer,User> find = new Finder<Integer,User>(
			Integer.class, User.class
	);

    public static boolean authenticate(String username, String password)
    {
        User tmp = find.where().eq("username", username).eq("password", password).findUnique();

        if(tmp == null)
            return false;
        return true;
    }

}

	
