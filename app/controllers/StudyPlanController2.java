package controllers;

import java.awt.List;

import models.Degree;
import play.data.Form;
import play.mvc.*;
import controllers.algorithm.req_and_course.*;
import controllers.forms.DegreeForm;
import controllers.forms.SrEditForm;
import models.*;

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
			String degreeId = degreeForm.degreeId;
			
//			Degree degree = Degree.findById(degreeId);
//			TestLinkList degreeProgram = new TestLinkList(degree.getTitle());	//add new degree
//			List<String> complexIds = degree.getReq_ids();						//get Requirement ids
//			for(String complexId : complexIds)
//			{ 
//			
//				Requirement req = Requirement.findById(Integer.valueOf(complexId)); //get Requirement
//				JSONArray srReqs = new JSONArray(req.getSr_ids());
//				ComplexReq complexReq = null;
//				if(srReqs.length() < 2)
//					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), "or");
//				else
//					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), (String)((JSONObject)srReqs.get(1)).get("relation"));
//				JSONObject srReqObject = null;
//				for (int i = 0; i < srReqs.length(); i++) {
//					srReqObject = (JSONObject) srReqs.get(i);
//					int srId = srReqObject.getInt("id");					//get Simple Requirment
//					Sr sr = Sr.findById(new Integer(srId));
//					int cgId = Integer.valueOf(sr.getCg_id());
//					int reqNum = Integer.valueOf(sr.getRequired_num());
//					Linklist simpleReq = new Linklist(srId, sr.getTitle(), reqNum); //initiate simple requirement
//					Cg cg = Cg.findById(new Integer(cgId));
//					List<String> courseIds = cg.getCourse_ids();
//					for(String courseId : courseIds)
//					{
//						simpleReq.insertNode(1, Integer.valueOf(courseId));	//add course
//					}
//					complexReq.insertSimple(simpleReq);
//					degreeProgram.course_list.add(simpleReq);
//				}
//				degreeProgram.addComplexReq(complexReq);		
//			}
			return ok(views.html.course.render());
		}catch(Exception e)
		{
			return badRequest(views.html.error.render("Cannot retrieve course list"));
		}
	}
	
	public static void generateReq(Integer id){
	}
	
}