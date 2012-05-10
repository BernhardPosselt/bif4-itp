package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;

import views.html.*;

import models.*;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 10.05.12
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
public class LoginHandling extends Controller {


    public static Result login()
    {
        return ok(login.render(form(Login.class)));
    }

    public static Result submit()
    {
        Form<Login> form = form(Login.class).bindFromRequest();

        if(!User.authenticate(form.field("username").value(), form.field("password").value()))
            form.reject("username", "Username or Password wrong!");

        if(form.hasErrors())
        {
              return badRequest(login.render(form));
        }
        else
        {
              User tmp = User.find.where().eq("username", form.get().getUsername()).findUnique();
              session("username", tmp.getUsername());
              session("userid", String.valueOf(tmp.getId()));
              return redirect(routes.Application.index());
        }

    }
}
