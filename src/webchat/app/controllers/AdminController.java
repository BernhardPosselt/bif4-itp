package controllers;

import play.Logger;
import play.mvc.*;
import play.data.*;

import models.*;

import views.html.*;

public class AdminController extends Controller {
	
	public static Result index() {
        return GO_HOME;
    }
	
	public static Result GO_HOME = redirect(
	        routes.AdminController.list(0, "name", "asc", "")
	    );
	
	public static int check()
	{
		String user = session("userid");
    	
    	if(user != null)
        {
            int userid = Integer.parseInt(user);

            User tmp = User.find.ref(userid);
            if(tmp.admin == true)
            {
            	return userid;
            }
            else
            {
            	Logger.warn("Admin Page for User with ID " + userid + " not loaded, because User is not an Admin - Redirect to Index Form");
            	return -1;
            }
        }
        else
        {
            Logger.warn("Admin Page not available, because User isn't logged in - Redirect to Login Form");
            return -1;
        }
	}

    public static Result list(int page, String sortBy, String order, String filter)
    {
    	int uid = check();
    	if(uid != -1)
    	{
    		Logger.info("Admin Page for User with ID " + uid + " loaded - Admin Form filled with data");
        	return ok(admin.render(User.page(page, 10, sortBy, order, filter), User.getUsername(Integer.parseInt(session("userid"))), sortBy, order, filter));
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result edituser(Long id) {
        Form<User> userForm = form(User.class).fill(User.find.byId(id.intValue()));
        return ok(edituser.render(id, userForm, User.getUsername(Integer.parseInt(session("userid")))));
    }
    
    public static Result updateuser(Long id) {
    	
    	int uid = check();
    	if(uid != -1)
    	{
	        Form<User> userForm = form(User.class).bindFromRequest();
	        if(userForm.hasErrors()) {
	            return badRequest(edituser.render(id, userForm, User.getUsername(Integer.parseInt(session("userid")))));
	        }
	        userForm.get().update(id);
	        flash("success", "User " + userForm.get().username + " has been updated");
        	Logger.info("User " + userForm.get().username + " with ID " + userForm.get().id + " has been updated");
	        return GO_HOME;
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result createuser() {
		Form<User> userForm = form(User.class);
	    return ok(createuser.render(userForm, User.getUsername(Integer.parseInt(session("userid")))));
	}
    
    public static Result saveuser() {
    	
    	int uid = check();
    	if(uid != -1)
    	{
    		Form<User> userForm = form(User.class).bindFromRequest();
            if(userForm.hasErrors()) 
            {
            	//Logger.error("Do was er no net wos do fuer a Fehler einekommt");
                return badRequest(createuser.render(userForm, User.getUsername(Integer.parseInt(session("userid")))));
            }
            userForm.get().save();
            flash("success", "User " + userForm.get().username + " has been created");
        	Logger.info("User " + userForm.get().username + " with ID " + userForm.get().id + " has been created");
            return GO_HOME;
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result deleteuser(Long id) {
    	
    	int uid = check();
    	if(uid != -1)
    	{
	        User.find.ref(id.intValue()).delete();
	        flash("success", "User " + id + " has been deleted");
	        Logger.info("User " + id + " has been deleted");
	        return GO_HOME;
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
}