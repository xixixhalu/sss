package controllers;

import controllers.forms.CgAddForm;
import controllers.forms.CgEditForm;
import models.Cg;
import models.Course;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class CgController extends Controller{
	
	public static Result retrieveCgs() {
		try{
			return ok(views.html.cg_list.render(Cg.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve course group list"));
    	}
    }
	
	public static Result deleteCg(Integer id){
    	try{
    		Cg.delete(id);
    		return redirect(routes.CgController.retrieveCgs());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete course group"));
    	}
    }
    
    public static Result requestEditCgPage(Integer id){
    	try{
    		Form<CgEditForm> form = Form.form(CgEditForm.class);
    		return ok(views.html.cg_edit.render(Course.getAll(), Cg.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find course group information"));
    	}
    		
    }
    
    public static Result updateCg(Integer id){
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
    
    public static Result requestCreateCgPage(){
    	try{
	    	Form<CgAddForm> form = Form.form(CgAddForm.class);
	    	return ok(views.html.cg_add.render(Course.getAll(), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result addCg(){
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
}
