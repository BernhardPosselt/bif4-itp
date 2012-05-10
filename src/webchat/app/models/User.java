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
    private int id;
	
	@Constraints.Required
    private String username;

    @Constraints.Required
    private String password;
	
	@Constraints.Required
    private Boolean online;
	
	@Constraints.Required
    private String prename;
	
	@Constraints.Required
    private String lastname;
	
	@Constraints.Required
    private String email;

	@ManyToMany(mappedBy="users")
	public List<Channel> channels;
	
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

	
