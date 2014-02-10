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
        return ok(views.html.sr_list.render(Sr.getAll()));
    }
	
	public static Result deleteSr(Integer id){
    	Sr.delete(id);
    	return redirect(routes.SrController.retrieveSrs());
    }
    
    public static Result requestEditSrPage(Integer id){
    	Form<SrEditForm> form = Form.form(SrEditForm.class);
    	return ok(views.html.sr_edit.render(Cg.getAll(), Sr.findById(id), form));
    }
    
    public static Result updateSr(Integer id){
    	Form<SrEditForm> filledForm = Form.form(SrEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong");
    	} 
    	else {
    		SrEditForm srForm = filledForm.get();
			Sr sr = Sr.findById(id);
			sr.setTitle(srForm.title);
			sr.setCg_id(srForm.cg_id);
			sr.setRequired_num(srForm.required_num);
			//sr.setNot(srForm.not);
			sr.update();
    		return redirect(routes.SrController.retrieveSrs());
    	}
    }
    
    public static Result requestCreateSrPage(){
    	Form<SrAddForm> form = Form.form(SrAddForm.class);
    	return ok(views.html.sr_add.render(Cg.getAll(), form));
    }
    
    public static Result addSr(){
    	Form<SrAddForm> filledForm = Form.form(SrAddForm.class).bindFromRequest();
    	SrAddForm srForm = filledForm.get();
    	Sr sr = Sr.createNewEntity();
    	sr.setTitle(srForm.title);
		sr.setCg_id(srForm.cg_id);
		sr.setRequired_num(srForm.required_num);
		//sr.setNot(srForm.not);
    	sr.save();
    	return redirect(routes.SrController.retrieveSrs());
    } 
    
}
