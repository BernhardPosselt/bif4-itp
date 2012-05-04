package models;

import javax.persistence.*;

import java.util.*;

import play.db.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class File extends Model {

	@Id
	public int id;
	
	@Constraints.Required
	public String type;
	
	@Constraints.Required
	public double size;
	
	@Constraints.Required
	public int owner_id;
	
	@Formats.DateTime(pattern = "dd-MM-yyyy")
	public Date timestamp;
	
	@ManyToMany(mappedBy="files")
	public List<Channel> channels;
}
