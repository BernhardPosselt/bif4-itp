package models;

import javax.persistence.*;

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
	public String type;
	
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
}
