package controllers;

import models.Degree;
import play.data.Form;
import play.mvc.*;
import controllers.algorithm.req_and_course.*;
import controllers.forms.DegreeForm;
import controllers.forms.SrEditForm;
import controllers.forms.WantForm;
import models.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class StudyPlanController2 extends Controller {	
	
	public static Result retrieveDegrees() {
		try{
			return ok(views.html.index.render(Degree.getAll()));
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
						true, true, true, false, false);
				json.put(course.toJson(cw).getString("id"), course.toJson(cw));
			}
			
			return ok(views.html.stu_course.render(degree, json.toString()));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Cannot retrieve course list"));
		}
	}
	
	public static Result assignSemester(){
		Form<WantForm> filledForm = Form.form(WantForm.class).bindFromRequest();
		
		try{
			WantForm form = filledForm.get();
			return ok(views.html.stu_semester.render(form.wantTakeCourses));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}
	}
	
	public static void generateReq(Integer id){
	}
	
}