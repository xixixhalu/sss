package controllers;

import models.Course;
import play.*;
import play.data.Form;
import play.mvc.*;
import controllers.forms.CourseEditForm;
import views.html.*;
import controllers.forms.*;

public class CourseController extends Controller {

    public static Result retrieveCourses() {
        return ok(views.html.course_list.render(Course.getAll()));
    }
   
    public static Result deleteCourse(Integer id){
    	Course.delete(id);
    	return redirect(routes.CourseController.retrieveCourses());
    }
    
    public static Result requestEditCoursePage(Integer id){
    	Form<CourseEditForm> form = Form.form(CourseEditForm.class);
    	play.Logger.debug("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxcvbnhhkhkjhkjhkjhk");
    	return ok(views.html.course_edit.render(Course.findById(id), form));
    }
    
    public static Result updateCourse(Integer id){
    	Form<CourseEditForm> filledForm = Form.form(CourseEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong agian and agian!");
    	} 
    	else {
    		CourseEditForm courseForm = filledForm.get();
			Course course = Course.findById(id);
			course.setPrefix(courseForm.prefix);
			course.setTitle(courseForm.title);
			course.setNumber(courseForm.number);
			course.setCredit(courseForm.credit);
			course.setPrerequisite_ids(courseForm.prerequisite_ids);
			course.setCorequisite_ids(courseForm.corequisite_ids);
			course.setOncampus(courseForm.oncampus);
			course.setOnline(courseForm.online);
			course.update();
    		return redirect(routes.CourseController.retrieveCourses());
    	}
    }
    
    public static Result requestCreateCoursePage(){
    	Form<CourseAddForm> form = Form.form(CourseAddForm.class);
    	return ok(views.html.course_add.render(form));
    }
    
    public static Result addCourse(){
    	Form<CourseAddForm> filledForm = Form.form(CourseAddForm.class).bindFromRequest();
    	CourseAddForm courseForm = filledForm.get();
    	Course course = Course.createNewEntity();
    	course.setPrefix(courseForm.prefix);
		course.setTitle(courseForm.title);
		course.setNumber(courseForm.number);
		course.setCredit(courseForm.credit);
		course.setPrerequisite_ids(courseForm.prerequisite_ids);
		course.setCorequisite_ids(courseForm.corequisite_ids);
		course.setOncampus(courseForm.oncampus);
		course.setOnline(courseForm.online);
    	course.save();
    	return redirect(routes.CourseController.retrieveCourses());
    }
    
    public static Result retrieveTestdata(Integer id) {
        
       String s= "{\"courses\":[" +
                    "{\"prifex\":\"CS"+id.toString()+"\",\"name\":\"Computer Science 101\"}," +
                    "{\"prifex\":\"CS102\",\"name\":\"Computer Science 102\"}," +
                    "{\"prifex\":\"CS103\",\"name\":\"Computer Science 103\"}" +
                    "]}";
        return ok(s);
    }
           
}