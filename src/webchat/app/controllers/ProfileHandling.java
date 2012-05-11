package controllers;

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
public class ProfileHandling extends Controller {

    public static Result submit()
    {
        Form<User> form = form(User.class).bindFromRequest();

        if(!form.field("password").value().equals(form.field("repeatpassword").value()))
        {
            form.reject("repeatpassword","Passwords not the same!");
        }

        if(form.hasErrors())
        {
            return badRequest(profile.render(form, User.getUsername(Integer.parseInt(session("userid")))));
        }
        else
        {
            User newuser = form.get();
            int userid = Integer.parseInt(session("userid"));
            User olduser = User.find.byId(userid);

            olduser.username = newuser.username;
            olduser.password = newuser.password;
            olduser.prename = newuser.prename;
            olduser.lastname = newuser.lastname;
            olduser.email = newuser.email;

            olduser.save();

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
            return ok(profile.render(form(User.class).fill(tmp), User.getUsername(Integer.parseInt(session("userid")))));
        }
        else
        {
            return redirect(routes.LoginHandling.login());
        }
    }


}
