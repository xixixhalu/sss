package controllers;

import models.Degree;
import play.data.Form;
import play.mvc.*;
import controllers.algorithm.req_and_course.*;
import controllers.forms.DegreeForm;
import controllers.forms.SrEditForm;
import models.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class StudyPlanController2 extends Controller {	
	
	public static Result retrieveDegrees() {
		try{
			Form<DegreeForm> form = Form.form(DegreeForm.class);
			return ok(views.html.index.render(Degree.getAll(), form));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve degree list"));
    	}
    }
	
	public static Result retrieveCourses() {
		Form<DegreeForm> filledForm = Form.form(DegreeForm.class).bindFromRequest();
		
		if(filledForm.hasErrors()) {
    		return badRequest("Not all mandatory fields correct or entered.");
    	}
		try{
			DegreeForm degreeForm = filledForm.get();
			Integer degreeId = degreeForm.degreeId;
			Degree degree = Degree.findById(degreeId);
			//get all courses JSON
			JSONObject json = new JSONObject();
			List<Course> courses = Course.getAll();
			for (Course course : courses) {
			
				CourseWrapper cw = new CourseWrapper(true, true, true, true,
						true, true, true, true, true);
				json.put(course.toJson(cw).getString("id"), course.toJson(cw).toString());

			}
			
			return ok(views.html.stu_course.render(degree, json.toString()));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Cannot retrieve course list"));
		}
	}
	
	public static void generateReq(Integer id){
	}
	
}