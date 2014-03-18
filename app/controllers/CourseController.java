package controllers;

import models.Course;
import play.*;
import play.data.Form;
import play.mvc.*;
import controllers.forms.CourseEditForm;
import views.html.*;
import controllers.forms.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class CourseController extends Controller {

    public static Result retrieveCourses() {
    	try{
    		//StudyPlanController.generateReq();
    		return ok(views.html.course_list.render(Course.getAll()));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve course list"));
    	}
    }
   
    public static Result deleteCourse(Integer id){
    	try{
    		Course.delete(id);
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete course"));
    	}
    	return redirect(routes.CourseController.retrieveCourses());
    }
    
    public static Result requestEditCoursePage(Integer id){
    	try{
    		Form<CourseEditForm> form = Form.form(CourseEditForm.class);
    		return ok(views.html.course_edit.render(Course.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find course information"));
    	}
    }
    
    public static Result updateCourse(Integer id){
    	Form<CourseEditForm> filledForm = Form.form(CourseEditForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	
    	try{
    		
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
    	catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result requestCreateCoursePage(){
    	try{
	    	Form<CourseAddForm> form = Form.form(CourseAddForm.class);
	    	return ok(views.html.course_add.render(form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result addCourse(){
    	Form<CourseAddForm> filledForm = Form.form(CourseAddForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	try{
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
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result retrieveTestdata(Integer id) {
        
       String s= "{\"courses\":[" +
                    "{\"prifex\":\"CS"+id.toString()+"\",\"name\":\"Computer Science 101\"}," +
                    "{\"prifex\":\"CS102\",\"name\":\"Computer Science 102\"}," +
                    "{\"prifex\":\"CS103\",\"name\":\"Computer Science 103\"}" +
                    "]}";
        return ok(s);
    }
    
    public static Result retrieveWholeCourses() {
    	List<Course> list = Course.getAll();
    	
    	JSONArray carray = new JSONArray();
    	
    	for (int i = 0; i < list.size(); ++i) {
	    	JSONObject cjson = new JSONObject();
	    	cjson.put("id", list.get(i).getId());
	    	cjson.put("name", list.get(i).getPrefix() + String.valueOf(list.get(i).getNumber()));
	    	cjson.put("title", list.get(i).getTitle());
	    	carray.put(cjson);
    	}
    	
    	JSONObject cajson = new JSONObject();
    	cajson.put("courses", carray);
    	
    	return ok(cajson.toString());
    }
    
}