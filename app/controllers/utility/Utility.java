package controllers.utility;

import play.mvc.*;

public class Utility extends Controller {
	
	/**
	 * get the header of the admin side
	 * */
	public static Result getHeader(){
    	return ok(views.html.header.render());
    }
    
	/**
	 * get the footer of the admin side
	 * */
    public static Result getFooter(){
    	return ok(views.html.footer.render());
    }
}