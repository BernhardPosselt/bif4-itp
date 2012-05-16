package controllers;

import play.Logger;
import play.mvc.*;
import play.data.*;

import models.*;

import views.html.*;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 10.05.12
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class ProfileController extends Controller {

    public static Result submit()
    {
        Form<User> form = form(User.class).bindFromRequest();
        String pw;

        if(!form.field("password").value().equals(form.field("repeatpassword").value()))
        {
            form.reject("repeatpassword","Passwords not the same!");
        }

        if(form.field("password").value().equals("") && form.field("repeatpassword").value().equals(""))
        {
            pw = null;
        }
        else
        {
            pw = form.field("password").value();
        }

        if(form.hasErrors())
        {
            Logger.error("Profile can't be saved");
            return badRequest(profile.render(form, User.getUsername(Integer.parseInt(session("userid")))));
        }
        else
        {
            User newuser = form.get();
            int userid = Integer.parseInt(session("userid"));
            User olduser = User.find.byId(userid);

            olduser.username = newuser.username;
            olduser.setPassword(pw);
            olduser.firstname = newuser.firstname;
            olduser.lastname = newuser.lastname;
            olduser.email = newuser.email;

            olduser.save();
            Logger.info("Profile for User with ID " + userid + " saved - Redirect to Index Site");
            return redirect(routes.Application.index());
        }
    }

    public static Result edit()
    {
        String user = session("userid");

        if(user != null)
        {
            int userid = Integer.parseInt(user);

            User tmp = User.find.ref(userid);
            Logger.info("Edit Profile for User with ID " + userid + " - Profile Form filled with userdata");
            return ok(profile.render(form(User.class).fill(tmp), User.getUsername(Integer.parseInt(session("userid")))));
        }
        else
        {
            Logger.warn("Edit Profile not available, because User isn't logged in - Redirect to Login Form");
            return redirect(routes.LoginController.login());
        }
    }


}
