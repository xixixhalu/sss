package controllers;

import models.Degree;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import controllers.algorithm.req_and_course.*;
import controllers.forms.DegreeForm;
import controllers.forms.TakeForm;
import controllers.StudyPlanPoolController;
import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class StudyPlanController extends Controller {	
	
	/**
	 * retrieve degree selection page
	 * @return degree selection page
	 */
	public static Result retrieveDegrees() {
		try{
			return ok(views.html.stu_index.render(Degree.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve degree list"));
    	}
    }
	
	/**
	 * retrieve courses selection page
	 * @return	courses selection page
	 */
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
			StudyPlan studyplan = new StudyPlan();

			StudyPlanPoolController.insertStudyPlan(studyplan);
			studyplan.CreateDegreeProgram(Integer.valueOf(degreeId));
			//get all courses' JSON
			JSONObject json = new JSONObject();
			List<Course> courses = Course.getAll();
			for (Course course : courses) {
			
				CourseWrapper cw = new CourseWrapper(true, true, true, true,
						true, true, true, false, false);
				json.put(course.toJson(cw).getString("id"), course.toJson(cw));
			}
			Logger.debug("c"+session().size() + session().get("uuid") + " "+StudyPlanPoolController.poolSize());
			return ok(views.html.stu_course.render(degree, json.toString()));
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Cannot retrieve course list"));
		}
	}
	
	/**
	 * automatically select the courses to satisfy a degree requirement
	 * @return courses data (JSON format)
	 */
	public static Result autoFillCourse(){
		StudyPlan studyplan = StudyPlanPoolController.getStudyPlan();
		if(studyplan.courseBin == null) {
			Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();	
			try {
				TakeForm form = filledForm.get();
				String want = form.wantTakeCourses;
				String already = form.alreadyTakenCourses;	
				JSONObject coursesArr = new JSONObject();	
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
				
				studyplan.degreeProgram.displayallComplexReq();
				studyplan.degreeProgram.displayAllCourse();
				studyplan.AutoFillCourseBin();
				studyplan.changeCourseStatus();
				ArrayList<Integer> courseBin = studyplan.courseBin;
				
				JSONArray newWantCourses = new JSONArray();
				for (Integer id : courseBin) {
					Course course = Course.findById(id);
					JSONObject auto_want = new JSONObject();
					auto_want.put("id", course.getId());
					auto_want.put("prefix", course.getPrefix());
					auto_want.put("num", course.getNumber());
					auto_want.put("title", course.getTitle());
					auto_want.put("maxDepth", 
							((CourseInReq)studyplan.degreeProgram.course.get(id).get(0)).maxDepth);
					newWantCourses.put(auto_want);
				}
				// alreadyCourse: (NOT_IMPLEMENTED)
				
				coursesArr.put("want", newWantCourses);
				coursesArr.put("already", alreadyCourses);
				
				return ok(coursesArr.toString());
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return badRequest(views.html.error.render("Some data cannot be obtained"));
			}
		}
		else
			return ok("");
	}
	
	/**
	 * retrieve semester assignment page
	 * @return	semester assignment page
	 */
	public static Result assignSemester(){
		if(!StudyPlanPoolController.isStudyPlanExists())
		{
			badRequest(views.html.error.render("Session Expired or Wrong Operation"));
		}
		StudyPlan studyplan = StudyPlanPoolController.getStudyPlan();
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		boolean needAuto = false;
		if(studyplan.courseBin == null)
			needAuto = true;
		try{
			TakeForm form = filledForm.get();
			
			String wantTakeCourses = form.wantTakeCourses;
			String alreadyTakeCourses = form.alreadyTakenCourses;
			String ASO = form.aso;
			
			JSONArray wantCourses = new JSONArray(wantTakeCourses);
			JSONArray alreadyCourses = new JSONArray(alreadyTakeCourses);
			JSONObject json = new JSONObject();
			CourseWrapper cw = new CourseWrapper(true, true, true, true,
						true, true, true, true, true);
//			Logger.info(ASO);
			for (int i = 0; i < wantCourses.length(); i++) {
				JSONObject wantCourse = (JSONObject) wantCourses.get(i);
				int id = wantCourse.getInt("id");
				int sid = wantCourse.getInt("sid");
				int cid = wantCourse.getInt("cid");
				if(needAuto)
				{
					studyplan.CheckInSelectedCourse(cid, sid, id);
				}
				json.put(String.valueOf(id), Course.findById(id).toJson(cw));
			}

			for (int i = 0; i < alreadyCourses.length(); i++) {
				JSONObject alreadyCourse = (JSONObject) alreadyCourses.get(i);
				int id = alreadyCourse.getInt("id");
				int sid = alreadyCourse.getInt("sid");
				int cid = alreadyCourse.getInt("cid");
				if(needAuto)
				{
					studyplan.CheckInSelectedCourse(cid, sid, id);
				}
				json.put(String.valueOf(id), Course.findById(id).toJson(cw));
			}
			
			//if courseBin is null, auto fill course in back-end
			if(needAuto)
			{
				studyplan.degreeProgram.CheckAllSimpleAndComplex();
				studyplan.AutoFillCourseBin();
			}
			studyplan.degreeProgram.displayallComplexReq();
			return ok(views.html.stu_semester.render(json.toString(), 
					wantCourses.toString(), alreadyCourses.toString(), ASO));
		}catch(Exception e)
		{
			e.printStackTrace();
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}
	}
	
	/**
	 * automatically assign the selected courses into the semester to satisfy a degree requirement
	 * @return courses data (JSON format)
	 */
	public static Result autoAssignSemester(){
		StudyPlan studyplan = StudyPlanPoolController.getStudyPlan();
		Form<TakeForm> filledForm = Form.form(TakeForm.class).bindFromRequest();
		HashMap<Integer, ArrayList<Integer>> corequisites = new HashMap<Integer, ArrayList<Integer>>();
		
		try{
			TakeForm form = filledForm.get();
			
			String wantTakeCourses = form.wantTakeCourses;
			String alreadyTakeCourses = form.alreadyTakenCourses;
			String semesterData = form.semesterData;
			String semesterNum = form.semesterNum;
			
			JSONArray wantCourses = new JSONArray(wantTakeCourses);
			JSONArray alreadyCourses = new JSONArray(alreadyTakeCourses);
			JSONArray semesterArray = new JSONArray(semesterData);
			
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
			
			studyplan.AutoAssignSemester(Integer.valueOf(semesterNum), semesterData);
			
			/**
			 * @author tongrui
			 * return semester data filled with courses
			 */
			for (int i = 0; i < semesterArray.length(); i++) {
				JSONObject semester = (JSONObject) semesterArray.get(i);
				
				int semesterID = semester.getInt("num");
				JSONArray courses = (JSONArray) semester.get("courses");
				ArrayList<Integer> coursesFilled = studyplan.studyplanResult.get(semesterID);
				
				for (Integer course : coursesFilled) {
					boolean flag = false;
					for (int j = 0; j < courses.length(); j++) {
						if (courses.getInt(j) == course) {
							flag = true;
							break;
						}
					}
					if (!flag)
						courses.put(course);
				}
			}
			
			return ok(semesterArray.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
			return badRequest(views.html.error.render("Some data cannot be obtained"));
		}	
	}
	
	/**
	 * retrieve final study plan page
	 * @return study plan page
	 */
	public static Result generateStudyPlan(){
		if(!StudyPlanPoolController.isStudyPlanExists())
		{
			return badRequest(views.html.error.render("Session Expired or Wrong Operation"));
		}
		return ok(views.html.stu_studyplan.render());
	}
	
	public static Result undo_fill() {
		Form<DegreeForm> filledForm = Form.form(DegreeForm.class).bindFromRequest();
		DegreeForm form = filledForm.get();
			
		StudyPlanPoolController.removeStudyPlan();
		StudyPlan studyplan = new StudyPlan();
		studyplan.CreateDegreeProgram(Integer.valueOf(form.degreeId));
		StudyPlanPoolController.insertStudyPlan(studyplan);
		return ok("");
	}
}