package controllers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import controllers.forms.CgAddForm;
import controllers.forms.CgEditForm;
import models.Cg;
import models.Course;
import models.CourseWrapper;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class CgController extends Controller{
	
	/**
	 * retrieve Course Group Information
	 * @return course group list page
	 */
	public static Result retrieveCgs() {
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
		try{
			return ok(views.html.cg_list.render(Cg.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve course group list"));
    	}
    }
	
	/**
	 * delete course group
	 * @param id
	 *         - id of the course group selected
	 * */
	public static Result deleteCg(Integer id){
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
    		Cg.delete(id);
    		return redirect(routes.CgController.retrieveCgs());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete course group"));
    	}
    }
    
	/**
	 * request edit course group page
	 * @param id
	 *         - id of the course group selected
	 * */
    public static Result requestEditCgPage(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
    		Form<CgEditForm> form = Form.form(CgEditForm.class);
    		return ok(views.html.cg_edit.render(Course.getAll(), Cg.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find course group information"));
    	}
    		
    }
    
    /**
	 * update the selected course group information
	 * @param id
	 *         - id of the course group selected
	 * */
    public static Result updateCg(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<CgEditForm> filledForm = Form.form(CgEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Not all mandatory fields correct or entered.");
    	} 
    	try{
			CgEditForm cgForm = filledForm.get();
			Cg cg = Cg.findById(id);
			cg.setPrefix(cgForm.prefix);
			cg.setTitle(cgForm.title);
			cg.setCourse_ids(cgForm.course_ids);
			cg.update();
			return redirect(routes.CgController.retrieveCgs());
    	}
    	catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    	
    }
    
    /**
     * request create course group page
     * @return course group information input page
     */
    public static Result requestCreateCgPage(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
	    	Form<CgAddForm> form = Form.form(CgAddForm.class);
	    	return ok(views.html.cg_add.render(Course.getAll(), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    /**
     * submit a new course group information
     * @return course group list page
     */
    public static Result addCg(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<CgAddForm> filledForm = Form.form(CgAddForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	try{
	    	CgAddForm cgForm = filledForm.get();
	    	Cg cg = Cg.createNewEntity();
	    	cg.setPrefix(cgForm.prefix);
			cg.setTitle(cgForm.title);	
			cg.setCourse_ids(cgForm.course_ids);
	    	cg.save();
	    	return redirect(routes.CgController.retrieveCgs());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    } 
    
    /**
	 * get the courses information in the selected course group
	 * @param id
	 *         - id of the course group selected
	 * */
    public static Result retrieveCgCourses(Integer id) {
    	
    	List<String> ids = Cg.findById(id).getCourse_ids();
    	
    	JSONArray carray = new JSONArray();
    	
    	CourseWrapper cw = new CourseWrapper(false, true, true, true, 
    			false, false, false, false, false);
    	
    	for (int i = 0; i < ids.size(); ++i) {
    		carray.put(Course.findById(Integer.valueOf(ids.get(i))).toJson(cw));
    	}
    	
    	JSONObject cajson = new JSONObject();
    	cajson.put("courses", carray);
    	
    	return ok(cajson.toString());
    }
}
