package controllers;

import java.util.Iterator;

import play.Logger;
import play.mvc.*;
import play.data.*;

import models.*;

import views.html.*;

public class AdminController extends Controller {
	
	public static Result index() {
    	int uid = check();
    	if(uid != -1)
    	{
    		Logger.info("Admin Page for User with ID " + uid + " loaded - Admin Form filled with data");
        	return ok(admin.render(User.findAll(), Channel.findAll(), Groups.findAll(), File.findAll(), User.getUsername(Integer.parseInt(session("userid")))));
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
	
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
	        	Logger.error("Error while editing the existing User " + userForm.get().id);
	            return badRequest(edituser.render(id, userForm, User.getUsername(Integer.parseInt(session("userid")))));
	        }
	        userForm.get().update(id.intValue());
	        flash("success", "User " + userForm.get().username + " has been updated");
        	Logger.info("User " + userForm.get().username + " with ID " + userForm.get().id + " has been updated");
	        return index();
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
            	Logger.error("Error while creating a new User");
                return badRequest(createuser.render(userForm, User.getUsername(Integer.parseInt(session("userid")))));
            }
            userForm.get().save();
            for(Iterator<models.Channel> chaniter = models.Channel.getAllPublicChannel().iterator(); chaniter.hasNext();){
            	models.Channel channel= new models.Channel();
            	channel = chaniter.next();
            	channel.users.add(userForm.get());
            	channel.saveManyToManyAssociations("users");
            }
            flash("success", "User " + userForm.get().username + " has been created");
        	Logger.info("User " + userForm.get().username + " with ID " + userForm.get().id + " has been created");
            return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result deleteuser(Long id) {
    	
    	String user = session("userid");
    	int uid = check();
    	if(uid != -1)
    	{
    		if(Integer.parseInt(user) == id.intValue())
    		{
    			Logger.error("User " + id + " tried to delete himself, canceled");
                return index();
    		}
    		else
    		{
		        User.find.ref(id.intValue()).delete();
		        flash("success", "User " + id + " has been deleted");
		        Logger.info("User " + id + " has been deleted");
		        return index();
    		}
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result editchannel(Long id) {
        Form<Channel> channelForm = form(Channel.class).fill(Channel.find.byId(id.intValue()));
        return ok(editchannel.render(id, channelForm, User.getUsername(Integer.parseInt(session("userid")))));
    }
    
    public static Result updatechannel(Long id) {
    	
    	int uid = check();
    	if(uid != -1)
    	{
	        Form<Channel> channelForm = form(Channel.class).bindFromRequest();
	        if(channelForm.hasErrors()) {
	        	Logger.error("Error while editing the existing Channel " + channelForm.get().id);
	            return badRequest(editchannel.render(id, channelForm, User.getUsername(Integer.parseInt(session("userid")))));
	        }
	        channelForm.get().update(id.intValue());
	        flash("success", "Channel " + channelForm.get().name + " has been updated");
        	Logger.info("Channel " + channelForm.get().name + " with ID " + channelForm.get().id + " has been updated");
	        return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result createchannel() {
		Form<Channel> channelForm = form(Channel.class);
	    return ok(createchannel.render(channelForm, User.getUsername(Integer.parseInt(session("userid")))));
	}
    
    public static Result savechannel() {
    	
    	int uid = check();
    	if(uid != -1)
    	{
    		Form<Channel> channelForm = form(Channel.class).bindFromRequest();
            if(channelForm.hasErrors()) 
            {
            	Logger.error("Error while creating a new Channel");
                return badRequest(createchannel.render(channelForm, User.getUsername(Integer.parseInt(session("userid")))));
            }
            channelForm.get().save();
            flash("success", "Channel " + channelForm.get().name + " has been created");
        	Logger.info("Channel " + channelForm.get().name + " with ID " + channelForm.get().id + " has been created");
            return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
public static Result deletechannel(Long id) {
    	
    	String user = session("userid");
    	int uid = check();
    	if(uid != -1)
    	{
	        Channel.find.ref(id.intValue()).delete();
	        flash("success", "Channel " + id + " has been deleted");
	        Logger.info("Channel " + id + " has been deleted");
	        return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }

public static Result deletechanneluser(Long channelid, Long userid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		User tmp = User.find.byId(userid.intValue());
		Channel tmp2 = Channel.find.byId(channelid.intValue());
		if(tmp.channels.contains(tmp2))
		{
			tmp.channels.remove(tmp2);
			tmp.save();
	        flash("success", "User " + userid + " with Channel " + channelid +  " has been deleted");
	        Logger.info("User " + userid + " with Channel " + channelid +  " has been deleted");
	        return edituser((long) uid);
		}
		return edituser((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result addchanneluser(Long channelid, Long userid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		User tmp = User.find.byId(userid.intValue());
		Channel tmp2 = Channel.find.byId(channelid.intValue());
		if(!tmp.channels.contains(tmp2))
		{
			tmp.channels.add(tmp2);
			tmp.save();
	        flash("success", "User " + userid + " with Channel " + channelid +  " has been created");
	        Logger.info("User " + userid + " with Channel " + channelid +  " has been created");
	        return edituser((long) uid);
		}
		return edituser((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result deletegroupuser(Long groupid, Long userid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		User tmp = User.find.byId(userid.intValue());
		Groups tmp2 = Groups.find.byId(groupid.intValue());
		if(tmp.groups.contains(tmp2))
		{
			tmp.groups.remove(tmp2);
			tmp.save();
	        flash("success", "User " + userid + " with Group " + groupid +  " has been deleted");
	        Logger.info("User " + userid + " with Group " + groupid +  " has been deleted");
	        return edituser((long) uid);
		}
		return edituser((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result addgroupuser(Long groupid, Long userid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		User tmp = User.find.byId(userid.intValue());
		Groups tmp2 = Groups.find.byId(groupid.intValue());
		if(!tmp.groups.contains(tmp2))
		{
			tmp.groups.add(tmp2);
			tmp.save();
	        flash("success", "User " + userid + " with Group " + groupid +  " has been created");
	        Logger.info("User " + userid + " with Group " + groupid +  " has been created");
	        return edituser((long) uid);
		}
		return edituser((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result deletechannelgroup(Long channelid, Long groupid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Groups tmp = Groups.find.byId(groupid.intValue());
		Channel tmp2 = Channel.find.byId(channelid.intValue());
		if(tmp2.groups.contains(tmp))
		{
			tmp2.groups.remove(tmp);
			tmp2.save();
	        flash("success", "Group " + groupid + " with Channel " + channelid +  " has been deleted");
	        Logger.info("Group " + groupid + " with Channel " + channelid +  " has been deleted");
	        return editgroup((long) uid);
		}
		return editgroup((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result addchannelgroup(Long channelid, Long groupid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Groups tmp = Groups.find.byId(groupid.intValue());
		Channel tmp2 = Channel.find.byId(channelid.intValue());
		if(!tmp2.groups.contains(tmp))
		{
			tmp2.groups.add(tmp);
			tmp2.save();
	        flash("success", "Group " + groupid + " with Channel " + channelid +  " has been created");
	        Logger.info("Group " + groupid + " with Channel " + channelid +  " has been created");
	        return editgroup((long) uid);
		}
		return editgroup((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result deleteusergroup(Long userid, Long groupid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Groups tmp = Groups.find.byId(groupid.intValue());
		User tmp2 = User.find.byId(userid.intValue());
		if(tmp2.groups.contains(tmp))
		{
			tmp2.groups.remove(tmp);
			tmp2.save();
	        flash("success", "Group " + groupid + " with User " + userid +  " has been deleted");
	        Logger.info("Group " + groupid + " with User " + userid +  " has been deleted");
	        return editgroup((long) uid);
		}
		return editgroup((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result addusergroup(Long userid, Long groupid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Groups tmp = Groups.find.byId(groupid.intValue());
		User tmp2 = User.find.byId(userid.intValue());
		if(!tmp2.groups.contains(tmp))
		{
			tmp2.groups.add(tmp);
			tmp2.save();
	        flash("success", "Group " + groupid + " with User " + userid +  " has been created");
	        Logger.info("Group " + groupid + " with User " + userid +  " has been created");
	        return editgroup((long) uid);
		}
		return editgroup((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result deleteuserchannel(Long userid, Long channelid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Channel tmp = Channel.find.byId(channelid.intValue());
		User tmp2 = User.find.byId(userid.intValue());
		if(tmp2.channels.contains(tmp))
		{
			tmp2.channels.remove(tmp);
			tmp2.save();
	        flash("success", "Channel " + channelid + " with User " + userid +  " has been deleted");
	        Logger.info("Channel " + channelid + " with User " + userid +  " has been deleted");
	        return editchannel((long) uid);
		}
		return editchannel((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result adduserchannel(Long userid, Long channelid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Channel tmp = Channel.find.byId(channelid.intValue());
		User tmp2 = User.find.byId(userid.intValue());
		if(!tmp2.channels.contains(tmp))
		{
			tmp2.channels.add(tmp);
			tmp2.save();
	        flash("success", "Channel " + channelid + " with User " + userid +  " has been created");
	        Logger.info("Channel " + channelid + " with User " + userid +  " has been created");
	        return editchannel((long) uid);
		}
		return editchannel((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result deletegroupchannel(Long groupid, Long channelid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Channel tmp = Channel.find.byId(channelid.intValue());
		Groups tmp2 = Groups.find.byId(groupid.intValue());
		if(tmp.groups.contains(tmp2))
		{
			tmp.groups.remove(tmp2);
			tmp.save();
	        flash("success", "Channel " + channelid + " with Group " + groupid +  " has been deleted");
	        Logger.info("Channel " + channelid + " with Group " + groupid +  " has been deleted");
	        return editchannel((long) uid);
		}
		return editchannel((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}

public static Result addgroupchannel(Long groupid, Long channelid) {
	
	String user = session("userid");
	int uid = check();
	if(uid != -1)
	{
		Channel tmp = Channel.find.byId(channelid.intValue());
		Groups tmp2 = Groups.find.byId(groupid.intValue());
		if(!tmp.groups.contains(tmp2))
		{
			tmp.groups.add(tmp2);
			tmp.save();
	        flash("success", "Channel " + channelid + " with Group " + groupid +  " has been created");
	        Logger.info("Channel " + channelid + " with Group " + groupid +  " has been created");
	        return editchannel((long) uid);
		}
		return editchannel((long) uid);
	}
	else
	{
		return redirect(routes.Application.index());
	}
}
    
    public static Result editgroup(Long id) {
        Form<Groups> groupForm = form(Groups.class).fill(Groups.find.byId(id.intValue()));
        return ok(editgroup.render(id, groupForm, User.getUsername(Integer.parseInt(session("userid")))));
    }
    
    public static Result updategroup(Long id) {
    	
    	int uid = check();
    	if(uid != -1)
    	{
	        Form<Groups> groupForm = form(Groups.class).bindFromRequest();
	        if(groupForm.hasErrors()) {
	        	Logger.error("Error while editing the existing Group " + groupForm.get().id);
	            return badRequest(editgroup.render(id, groupForm, User.getUsername(Integer.parseInt(session("userid")))));
	        }
	        groupForm.get().update(id.intValue());
	        flash("success", "Group " + groupForm.get().name + " has been updated");
        	Logger.info("Group " + groupForm.get().name + " with ID " + groupForm.get().id + " has been updated");
	        return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result creategroup() {
		Form<Groups> groupForm = form(Groups.class);
	    return ok(creategroup.render(groupForm, User.getUsername(Integer.parseInt(session("userid")))));
	}
    
    public static Result savegroup() {
    	
    	int uid = check();
    	if(uid != -1)
    	{
    		Form<Groups> groupForm = form(Groups.class).bindFromRequest();
            if(groupForm.hasErrors()) 
            {
            	Logger.error("Error while creating a new Group");
                return badRequest(creategroup.render(groupForm, User.getUsername(Integer.parseInt(session("userid")))));
            }
            groupForm.get().save();
            flash("success", "Group " + groupForm.get().name + " has been created");
        	Logger.info("Group " + groupForm.get().name + " with ID " + groupForm.get().id + " has been created");
            return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result deletegroup(Long id) {
    	
    	String user = session("userid");
    	int uid = check();
    	if(uid != -1)
    	{
	        Groups.find.ref(id.intValue()).delete();
	        flash("success", "Group " + id + " has been deleted");
	        Logger.info("Group " + id + " has been deleted");
	        return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
    
public static Result deletefile(Long id) {
    	
    	String user = session("userid");
    	int uid = check();
    	if(uid != -1)
    	{
	        File.find.ref(id.intValue()).delete();
	        flash("success", "File " + id + " has been deleted");
	        Logger.info("File " + id + " has been deleted");
	        return index();
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	}
    }
}
