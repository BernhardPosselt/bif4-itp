package models;

import javax.persistence.*;
import javax.validation.Constraint;

import java.util.*;

import play.db.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

/**
 * Created with IntelliJ IDEA. Scheiﬂ auf IntelliJ by Christpoh Lindmaier^^
 * User: Daniel
 * Date: 24.04.12
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class User extends Model {
	
	@Id
	int id;
	
	@Constraints.Required
	String username;
	
	@Constraints.Required
	Boolean online;
	
	@Constraints.Required
	String prename;
	
	@Constraints.Required
	String lastname;
	
	@Constraints.Required
	String email;

	@ManyToMany(mappedBy="users")
	public List<Channel> channels;
}
