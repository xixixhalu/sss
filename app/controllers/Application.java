package controllers;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {

        //return ok(index.render("Your new application is ready."));
    	return redirect(routes.StudyPlanController.retrieveDegrees());
    }
    
    public static Result help() {
    	return ok(help.render());
    }

}
