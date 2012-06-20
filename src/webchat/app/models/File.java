package models;

import javax.persistence.*;

import java.sql.Blob;
import java.util.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class File extends Model {

	@Id
	public int id;
	
	@Constraints.Required
	public String name;

    @Constraints.Required
    public String filename;
	
	@Constraints.Required
	public String mimetype;
	
	@Constraints.Required
	public double size;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date date;
	
	@ManyToOne
	public User uid;
	
	@ManyToMany(mappedBy="files")
	public List<Channel> channels;
	
	public static Finder<Integer,File> find = new Finder<Integer,File>(
			Integer.class, File.class
	);
	
    public static List<File> findAll(){
        return find.all();
    }
	
	public static List<File> getChannelFiles (int channelid){
		List<File> files = new ArrayList<File>();
		files = find.where().eq("channels.id", channelid).orderBy().desc("date").findList();
		return files;
	}
	
}
