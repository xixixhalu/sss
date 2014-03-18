package controllers;

import play.mvc.*;

public class Utility extends Controller {
	public static Result getHeader(){
    	return ok(views.html.header.render());
    }
    
    public static Result getFooter(){
    	return ok(views.html.footer.render());
    }
}