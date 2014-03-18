package controllers;

import controllers.forms.SrAddForm;
import controllers.forms.SrEditForm;
import models.Sr;
import models.Cg;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class SrController extends Controller{
	
	public static Result retrieveSrs() {
		try{
			return ok(views.html.sr_list.render(Sr.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve simple requirement list"));
    	}
    }
	
	public static Result deleteSr(Integer id){
		try{
	    	Sr.delete(id);
	    	return redirect(routes.SrController.retrieveSrs());
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete simple requirement"));
    	}
    }
    
    public static Result requestEditSrPage(Integer id){
    	try{
	    	Form<SrEditForm> form = Form.form(SrEditForm.class);
	    	return ok(views.html.sr_edit.render(Cg.getAll(), Sr.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find simple requirement information"));
    	}
    }
    
    public static Result updateSr(Integer id){
    	Form<SrEditForm> filledForm = Form.form(SrEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Not all mandatory fields correct or entered.");
    	} 
    	try{
    		SrEditForm srForm = filledForm.get();
			Sr sr = Sr.findById(id);
			sr.setTitle(srForm.title);
			sr.setCg_id(srForm.cg_id);
			sr.setRequired_num(srForm.required_num);
			//sr.setNot(srForm.not);
			sr.update();
    		return redirect(routes.SrController.retrieveSrs());
    	}
    	catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result requestCreateSrPage(){
    	try{
	    	Form<SrAddForm> form = Form.form(SrAddForm.class);
	    	return ok(views.html.sr_add.render(Cg.getAll(), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    public static Result addSr(){
    	Form<SrAddForm> filledForm = Form.form(SrAddForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	try{
	    	SrAddForm srForm = filledForm.get();
	    	Sr sr = Sr.createNewEntity();
	    	sr.setTitle(srForm.title);
			sr.setCg_id(srForm.cg_id);
			sr.setRequired_num(srForm.required_num);
			//sr.setNot(srForm.not);
	    	sr.save();
	    	return redirect(routes.SrController.retrieveSrs());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    } 
    
}
