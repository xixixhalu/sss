package controllers;

import models.Degree;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import controllers.algorithm.req_and_course.*;
import controllers.forms.DegreeForm;
import controllers.forms.SrEditForm;
import controllers.forms.TakeForm;
import models.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		
		try{
			TakeForm form = filledForm.get();
			
			String wantTakeCourses = form.wantTakeCourses;
			String alreadyTakeCourses = form.alreadyTakeCourses;
			
			JSONArray wantCourses = new JSONArray(wantTakeCourses);
			JSONArray alreadyCourses = new JSONArray(alreadyTakeCourses);
			JSONObject json = new JSONObject();
			for (int i = 0; i < wantCourses.length(); i++) {
				JSONObject wantCourse = (JSONObject) wantCourses.get(i);
				int id = wantCourse.getInt("id");
				int sid = wantCourse.getInt("sid");
				int cid = wantCourse.getInt("cid");
				session("jsonCourseData", wantTakeCourses);
				
				JSONObject alreadyCourse = (JSONObject) alreadyCourses.get(i);
				int _id = alreadyCourse.getInt("id");
				int _sid = alreadyCourse.getInt("sid");
				int _cid = alreadyCourse.getInt("cid");
				
				//get all courses JSON
				CourseWrapper cw = new CourseWrapper(true, true, true, true,
							true, true, true, true, true);
				json.put(String.valueOf(id), Course.findById(id).toJson(cw));
				json.put(String.valueOf(id), Course.findById(_id).toJson(cw));
			}
			return ok(views.html.stu_semester.render(json.toString(), wantCourses, alreadyCourses));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}
	}
	
	public static void generateReq(Integer id){
	}
	
}