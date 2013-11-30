package controllers;

import models.Course;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class CourseController extends Controller {

	static Form<Course> courseForm = Form.form(Course.class);
	
    public static Result retrieveCourses() {
        return ok(views.html.course_list.render(Course.find()));
    }
    
    public static Result deleteCourse(Integer id){
    	Course.delete(id);
    	return redirect(routes.CourseController.retrieveCourses());
    }
    
    public static Result editCourse(Integer id){
    	courseForm.fill(Course.find(id));
    	return ok(views.html.course_info.render(Course.find(id), courseForm));
    }
    
    public static Result createCourse(){
    	return ok(views.html.course_new.render());
    }
    
    public static Result addCourse(){
    	Course course = Form.form(Course.class).bindFromRequest().get();
    	course.save();
    	return redirect(routes.CourseController.retrieveCourses());
    }
    
    /*
    public static Result updateCourse(){
    	Form<Course> filledForm = courseForm.bindFromRequest();
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong");
    	} 
    	else {
    		Course.update(filledForm.get());
    		return ok(views.html.course_list.render(Course.find()));
    	}
    }*/

}