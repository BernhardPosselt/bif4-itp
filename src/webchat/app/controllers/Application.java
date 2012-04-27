package controllers;

import play.*;

import play.mvc.*;
import views.html.*;
import java.util.*;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render());
  }

  public List<String> getList(int test)
  {
      List<String> mylist = new ArrayList<String>();
      mylist.add("hallo");
      return mylist;
  }

}