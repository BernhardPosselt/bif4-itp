package controllers;

import play.Logger;
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
public class LoginController extends Controller {

    public static Result login()
    {
        String user = session("userid");
        if(user != null)
        {
            Logger.info("User with ID" + user + " already logged in");
            return redirect(routes.Application.index());
        }
        else
        {
            Logger.warn("User isn't logged in");
            return ok(login.render(form(Login.class), ""));
        }
    }

    public static Result logout()
    {
        Logger.info("User with ID " + session("userid") + " logged out.");
        session().clear();
        return redirect(routes.LoginController.login());
    }

    public static Result submit()
    {
        Form<Login> form = form(Login.class).bindFromRequest();

        if(!User.authenticate(form.field("username").value(), form.field("password").value()))
        {
            Logger.warn("Login with username " + form.field("username").value() + " and password " + form.field("password").value() + " wrong!");
            form.reject("username", "Username or Password wrong!");
        }

        if(form.hasErrors())
        {
              Logger.error("Login wrong!");
              return badRequest(login.render(form, ""));
        }
        else
        {

              User tmp = User.find.where().eq("username", form.get().getUsername()).findUnique();
              session("userid", String.valueOf(tmp.id));
              Logger.info("Login correct, User with ID " + tmp.id + " logged in!");
              return redirect(routes.Application.index());
        }

    }
}
