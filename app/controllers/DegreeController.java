package controllers;

import controllers.forms.DegreeAddForm;
import controllers.forms.DegreeEditForm;
import models.Degree;
import models.Requirement;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class DegreeController extends Controller{
	
	public static Result retrieveDegrees() {
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
		try{
			return ok(views.html.degree_list.render(Degree.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve degree list"));
    	}
    }
	
	public static Result deleteDegree(Integer id){
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
		try{
	    	Degree.delete(id);
	    	return redirect(routes.DegreeController.retrieveDegrees());
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete degree"));
    	}
    }
   
    public static Result requestEditDegreePage(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
	    	Form<DegreeEditForm> form = Form.form(DegreeEditForm.class);
	    	return ok(views.html.degree_edit.render(Requirement.getAll(), Degree.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find degree information"));
    	}
    }
         
    public static Result updateDegree(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<DegreeEditForm> filledForm = Form.form(DegreeEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Not all mandatory fields correct or entered.");
    	} 
    	try{
    		DegreeEditForm degreeForm = filledForm.get();
			Degree degree = Degree.findById(id);
			degree.setTitle(degreeForm.title);
			degree.setReq_ids(degreeForm.req_ids);
			degree.update();
    		return redirect(routes.DegreeController.retrieveDegrees());
    	}
    	catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    	
    }
    
    public static Result requestCreateDegreePage(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
	    	Form<DegreeAddForm> form = Form.form(DegreeAddForm.class);
	    	return ok(views.html.degree_add.render(Requirement.getAll(), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result addDegree(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<DegreeAddForm> filledForm = Form.form(DegreeAddForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	try{
	    	DegreeAddForm degreeForm = filledForm.get();
	    	Degree degree = Degree.createNewEntity();
	    	degree.setTitle(degreeForm.title);
			degree.setReq_ids(degreeForm.req_ids);
	    	degree.save();
	    	return redirect(routes.DegreeController.retrieveDegrees());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    } 
    
}
