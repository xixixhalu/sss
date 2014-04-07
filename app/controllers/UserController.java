package controllers;

import play.data.Form;
import play.mvc.*;
import views.html.*;
import play.*;
import models.User;
import controllers.forms.UserLoginForm;

public class UserController extends Controller {
	
	public static Result retrieveLoginPage() {
		Form<UserLoginForm> form = Form.form(UserLoginForm.class);
		return ok(views.html.admin_index.render(form));
    }
	
	public static Result Login() {
		try{
			Form<UserLoginForm> filledForm = Form.form(UserLoginForm.class).bindFromRequest();
			UserLoginForm userLoginForm = filledForm.get();
			String name = userLoginForm.username;
			String password = userLoginForm.password;
			
			User user = User.findByName(name);
			play.Logger.info(name + ", " + password);
			play.Logger.info(user.getName() + ", " + user.getPassword());
			if(user.getPassword().equals(password))
			{
				session("user", name);
				return redirect(routes.CourseController.retrieveCourses());
			}
			else
				return badRequest(views.html.error.render("Cannot find user information"));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Cannot find user information"));
		}
	}
	
	public static Result Logout()
	{
		session().clear();
	    flash("success", "You've been logged out");
    	return redirect(routes.UserController.retrieveLoginPage());
	}
}