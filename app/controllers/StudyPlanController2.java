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
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class StudyPlanController2 extends Controller {	
	
	static StudyPlan studyplan;
	
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
			
			//Initialize study plan graph.
			studyplan = new StudyPlan();
			studyplan.CreateDegreeProgram(Integer.valueOf(degreeId));
			
			//get all courses' JSON
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
	
	public static Result autoFillCourse(){
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		TakeForm form = filledForm.get();
		String want = form.wantTakeCourses;
		String already = form.alreadyTakenCourses;			
		try {
			JSONArray wantCourses = new JSONArray(want);
			JSONArray alreadyCourses = new JSONArray(already);
			for (int i = 0; i < wantCourses.length(); i++) {
				JSONObject wantCourse = (JSONObject) wantCourses.get(i);
				int id = wantCourse.getInt("id");
				int sid = wantCourse.getInt("sid");
				int cid = wantCourse.getInt("cid");
				studyplan.CheckInSelectedCourse(cid, sid, id);
			}
	
			for (int i = 0; i < alreadyCourses.length(); i++) {
				JSONObject alreadyCourse = (JSONObject) alreadyCourses.get(i);
				int id = alreadyCourse.getInt("id");
				int sid = alreadyCourse.getInt("sid");
				int cid = alreadyCourse.getInt("cid");
				studyplan.CheckInSelectedCourse(cid, sid, id);
			}
			studyplan.degreeProgram.CheckAllSimpleAndComplex();
			studyplan.AutoFillCourseBin();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}
		
		return ok();
		
	}
	
	public static Result assignSemester(){
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		
		try{
			TakeForm form = filledForm.get();
			
			String wantTakeCourses = form.wantTakeCourses;
			String alreadyTakeCourses = form.alreadyTakenCourses;
			
			JSONArray wantCourses = new JSONArray(wantTakeCourses);
			JSONArray alreadyCourses = new JSONArray(alreadyTakeCourses);
			JSONObject json = new JSONObject();
			CourseWrapper cw = new CourseWrapper(true, true, true, true,
						true, true, true, true, true);
			
			for (int i = 0; i < wantCourses.length(); i++) {
				JSONObject wantCourse = (JSONObject) wantCourses.get(i);
				int id = wantCourse.getInt("id");
				int sid = wantCourse.getInt("sid");
				int cid = wantCourse.getInt("cid");
				//session("jsonCourseData", wantTakeCourses);
				
				json.put(String.valueOf(id), Course.findById(id).toJson(cw));
			}

			for (int i = 0; i < alreadyCourses.length(); i++) {
				JSONObject alreadyCourse = (JSONObject) alreadyCourses.get(i);
				int id = alreadyCourse.getInt("id");
				int sid = alreadyCourse.getInt("sid");
				int cid = alreadyCourse.getInt("cid");
				
				json.put(String.valueOf(id), Course.findById(id).toJson(cw));
			}
			
			return ok(views.html.stu_semester.render(json.toString(), 
					wantCourses.toString(), alreadyCourses.toString()));
		}catch(Exception e)
		{
			e.printStackTrace();
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}
	}
	
	public static Result autoAssignSemester(){
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		HashMap<Integer, ArrayList<Integer>> corequisites = new HashMap<Integer, ArrayList<Integer>>();
		
		try{
			TakeForm form = filledForm.get();
			
			String wantTakeCourses = form.wantTakeCourses;
			String alreadyTakeCourses = form.alreadyTakenCourses;
			
			JSONArray wantCourses = new JSONArray(wantTakeCourses);
			JSONArray alreadyCourses = new JSONArray(alreadyTakeCourses);
			
			for (int i = 0; i < wantCourses.length(); i++) {
				JSONObject wantCourse = (JSONObject) wantCourses.get(i);
				int id = wantCourse.getInt("id");

				String core_ids = Course.findById(Integer.valueOf(id)).getCoreq(2);
				if (!core_ids.trim().equals("-")) {
					String[] colist = core_ids.split(" ");
					ArrayList<Integer> core = new ArrayList<Integer>();
					for(int j = 2; j < colist.length; j+=2)
					{
						core.add(Integer.valueOf(colist[j]));
					}
					corequisites.put(Integer.valueOf(id), core);
				}
			}

			for (int i = 0; i < alreadyCourses.length(); i++) {
				JSONObject alreadyCourse = (JSONObject) alreadyCourses.get(i);
				int id = alreadyCourse.getInt("id");
				String core_ids = Course.findById(Integer.valueOf(id)).getCoreq(2);
				if (!core_ids.trim().equals("-")) {
					String[] colist = core_ids.split(" ");
					ArrayList<Integer> core = new ArrayList<Integer>();
					for(int j = 2; j < colist.length; j+=2)
					{
						core.add(Integer.valueOf(colist[j]));
					}
					corequisites.put(Integer.valueOf(id), core);
				}
			}
			//Bowen: CALL algorithm function and input "corequisites : HashMap<Integer, ArrayList<Integer>>" here;
			
			//Bowen: autoAssignSemester
			Logger.info("+++++++++++++++++++++++++++++");
			studyplan.AutoAssignSemester(8);
			return ok();
		}catch(Exception e)
		{
			e.printStackTrace();
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}	
	}
	
	public static void generateReq(Integer id){
	}
	
}