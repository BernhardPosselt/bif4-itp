package models;

import javax.persistence.*;
import javax.validation.Constraint;




import java.util.*;

import play.Logger;
import play.Logger.*;
import play.api.libs.Crypto;
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
        if(newpassword != null)
        {
		    password = Crypto.sign(newpassword);
        }
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

	public String status;
	
	public Boolean active;

	public Boolean admin;
	
		
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date lastlogin;

	@ManyToMany(mappedBy="users", cascade=CascadeType.ALL)
	public List<Channel> channels;
	
	@ManyToMany(mappedBy="mods", cascade=CascadeType.ALL)
	public List<Channel> modchannels;
	
	@ManyToMany(mappedBy="readonly", cascade=CascadeType.ALL)
	public List<Channel> readonlychannels;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Groups> groups;
	
	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Groups group) {
		this.groups.add(group);
	}
	
	public static String getPassword(int id)
	{
		return find.byId(id).password;
	}
	
	public static Finder<Integer,User> find = new Finder<Integer,User>(
			Integer.class, User.class
	);
	
	public static User getbyId (int id){
		return find.byId(id);
	}

	public static String getUsername(int id)
    {		boolean help = false;
			for (Iterator<User> iter=findAll().iterator(); iter.hasNext();){
				User user=iter.next();
				if (user.id == id){
					help = true;
					break;
				}
			}
			if (help == true)
				return find.byId(id).username; 	
			else
				return "nouserfound";
    }
	
	public static boolean getActive(int id)
    {
        return find.byId(id).active;
    }

	public  void setActive(String act)
	{
    	if(act.equals("true"))
    	{
    		active = true;
    	}
    	else
    	{
    		active = false;
    	}
	}
	
	public  void setAdmin(String adm)
	{
    	if(adm.equals("true"))
    	{
    		admin = true;
    	}
    	else
    	{
    		admin = false;
    	}
	}
	
	public static void setActive(int id)
	{
	
	}
	
    public static boolean authenticate(String name, String pw)
    {
        User tmp = find.where().eq("username", name).eq("password", Crypto.sign(pw)).findUnique();

        if(tmp == null){
            return false;
        } else {
            return true;            
        }            
            
    }

    public static int getUserID(String name)
    {
        User tmp = find.where().ieq("username", name).findUnique();
        if(tmp != null)
            return tmp.id;

        return -1;
    }
    
    public static void setUseronline(int userid){
    	User user = new User();
    	user = find.byId(userid);
    	user.status = "online";
    	user.lastlogin = new Date();
    	user.update();
    }
    
    public static void setUseroffline(int userid){
    	User user = new User();
    	user = find.byId(userid);
    	user.status = "offline";
    	user.update();
    }
    
    public static List<User> getonlineUsers (){
    	List<User> users = new ArrayList<User>();
    	users = find.where().eq("online", true).findList();
    	return users;
    }

    public static List<User> findAll(){
        return find.all();
    }
    
    public static List<User> getChannelGroupUser(List<Groups> groups)
    {
    	List<User> users = new ArrayList<User>();
		for (Iterator<Groups> groupiter = groups.iterator(); groupiter.hasNext();){
			 List<User> tmp = new ArrayList<User>();
		     tmp =  find.where().eq("groups.id", groupiter.next().id).findList();
		     users.addAll(tmp);
		}
        return users;
    }
    
    public static List<Channel> getChannelsForUser(int userid)
    {
    	List<Channel> channels = new ArrayList<Channel>();
    	User tmp = new User();
    	tmp = find.byId(userid);
    	if(tmp != null)
    	{
    		for (Iterator<Channel> iterator= find.byId(userid).channels.iterator(); iterator.hasNext();){
    			channels.add(iterator.next());
    		}
    	}
    	return channels;
    }
    
    public static List<Channel> getChannelsNotForUser(int userid)
    {
    	List<Channel> channels = new ArrayList<Channel>();
    	channels = Channel.find.all();
    	User tmp = new User();
    	tmp = find.byId(userid);
    	if(tmp != null)
    	{
    		for (Iterator<Channel> iterator= find.byId(userid).channels.iterator(); iterator.hasNext();){
    			channels.remove(iterator.next());
    		}
    	}
    	return channels;
    }
    
    public static List<Groups> getGroupsForUser(int userid)
    {
    	List<Groups> groups = new ArrayList<Groups>();
    	User tmp = new User();
    	tmp = find.byId(userid);
    	if(tmp != null)
    	{
    		for (Iterator<Groups> iterator= find.byId(userid).groups.iterator(); iterator.hasNext();){
    			groups.add(iterator.next());
    		}
    	}
    	return groups;
    }
    
    public static List<Groups> getGroupsNotForUser(int userid)
    {
    	List<Groups> groups = new ArrayList<Groups>();
    	groups = Groups.find.all();
    	User tmp = new User();
    	tmp = find.byId(userid);
    	if(tmp != null)
    	{
    		for (Iterator<Groups> iterator= find.byId(userid).groups.iterator(); iterator.hasNext();){
    			groups.remove(iterator.next());
    		}
    	}
    	return groups;
    }
 
}

	
