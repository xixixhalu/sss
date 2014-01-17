package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    ////////////大大大大是
        //return ok(index.render("Your new application is ready."));
    	return redirect(routes.CourseController.retrieveCourses());
    }

}
