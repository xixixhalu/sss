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
        return ok(views.html.cg_list.render(Cg.getAll()));
    }
	
	public static Result deleteCg(Integer id){
    	Cg.delete(id);
    	return redirect(routes.CgController.retrieveCgs());
    }
    
    public static Result requestEditCgPage(Integer id){
    	Form<CgEditForm> form = Form.form(CgEditForm.class);
    	return ok(views.html.cg_edit.render(Course.getAll(), Cg.findById(id), form));
    }
    
    public static Result updateCg(Integer id){
    	Form<CgEditForm> filledForm = Form.form(CgEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong");
    	} 
    	else {
    		CgEditForm cgForm = filledForm.get();
			Cg cg = Cg.findById(id);
			cg.setPrefix(cgForm.prefix);
			cg.setTitle(cgForm.title);
			cg.setCourse_ids(cgForm.course_ids);
			cg.update();
    		return redirect(routes.CgController.retrieveCgs());
    	}
    }
    
    public static Result requestCreateCgPage(){
    	Form<CgAddForm> form = Form.form(CgAddForm.class);
    	return ok(views.html.cg_add.render(Course.getAll(), form));
    }
    
    public static Result addCg(){
    	Form<CgAddForm> filledForm = Form.form(CgAddForm.class).bindFromRequest();
    	CgAddForm cgForm = filledForm.get();
    	Cg cg = Cg.createNewEntity();
    	cg.setPrefix(cgForm.prefix);
		cg.setTitle(cgForm.title);	
		cg.setCourse_ids(cgForm.course_ids);
    	cg.save();
    	return redirect(routes.CgController.retrieveCgs());
    } 
}
