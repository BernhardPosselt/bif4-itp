package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	
    public static List<Groups> findAll(){
        return find.all();
    }
    
	public static Groups getbyId (int id){
		return find.byId(id);
	}
    
	public static List<Groups> getChannelGroups(int channelid){
		List<Groups> tmp = new ArrayList<Groups>();
		tmp = find.where().eq("channels.id", channelid).findList();
		return tmp;
	}
	
	public static List<Channel> getChannelsForGroup(int groupid)
    {
    	List<Channel> channels = new ArrayList<Channel>();
    	Groups tmp = new Groups();
    	tmp = find.byId(groupid);
    	for (Iterator<Channel> iterator= find.byId(groupid).channels.iterator(); iterator.hasNext();){
			channels.add(iterator.next());
		}
    	return channels;
    }
    
    public static List<Channel> getChannelsNotForGroup(int groupid)
    {
    	List<Channel> channels = new ArrayList<Channel>();
    	channels = Channel.find.all();
    	Groups tmp = new Groups();
    	tmp = find.byId(groupid);
    	for (Iterator<Channel> iterator= find.byId(groupid).channels.iterator(); iterator.hasNext();){
			channels.remove(iterator.next());
		}
    	return channels;
    }
	
    public static List<User> getUsersForGroup(int groupid)
    {
    	List<User> users = new ArrayList<User>();
    	Groups tmp = new Groups();
    	tmp = find.byId(groupid);
    	for (Iterator<User> iterator= find.byId(groupid).users.iterator(); iterator.hasNext();){
			users.add(iterator.next());
		}
    	return users;
    }
    
    public static List<User> getUsersNotForGroup(int groupid)
    {
    	List<User> users = new ArrayList<User>();
    	users = User.find.all();
    	Groups tmp = new Groups();
    	tmp = find.byId(groupid);
    	for (Iterator<User> iterator= find.byId(groupid).users.iterator(); iterator.hasNext();){
			users.remove(iterator.next());
		}
    	return users;
    }

}
