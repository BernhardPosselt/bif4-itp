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

        if(user != null) //User logged in
        {
            Logger.info("User with ID " + user + " already logged in - Redirect to Index Site");
            return redirect(routes.Application.index());
        }
        else  //User not logged in
        {
            Logger.warn("User not logged in - Show empty login form");
            return ok(login.render(form(Login.class), ""));
        }
    }

    public static Result logout()
    {
        Logger.info("User with ID " + session("userid") + " logged out - Redirect to Login Form");
        session().clear();
        return redirect(routes.LoginController.login());
    }

    public static Result submit()
    {
        Form<Login> form = form(Login.class).bindFromRequest();

        //Authentication
        if(!User.authenticate(form.field("username").value(), form.field("password").value()))
        {
            form.reject("username", "Username or Password wrong!");
        }

        //Error
        if(form.hasErrors())
        {
              Logger.error("Login not successful - Wrong username or password");
              return badRequest(login.render(form, ""));
        }
        else //Authentication successful
        {
              //Get UserID by Username
              session("userid", String.valueOf(User.getUserID(form.get().getUsername())));

              Logger.info("User with ID " + session("userid") + " logged in - Redirect to Index Site");
              return redirect(routes.Application.index());
        }

    }
}
