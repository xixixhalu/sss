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
        return ok(views.html.degree_list.render(Degree.getAll()));
    }
	
	public static Result deleteDegree(Integer id){
    	Degree.delete(id);
    	return redirect(routes.DegreeController.retrieveDegrees());
    }
   
    public static Result requestEditDegreePage(Integer id){
    	Form<DegreeEditForm> form = Form.form(DegreeEditForm.class);
    	return ok(views.html.degree_edit.render(Requirement.getAll(), Degree.findById(id), form));
    }
         
    public static Result updateDegree(Integer id){
    	Form<DegreeEditForm> filledForm = Form.form(DegreeEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong");
    	} 
    	else {
    		DegreeEditForm degreeForm = filledForm.get();
			Degree degree = Degree.findById(id);
			degree.setTitle(degreeForm.title);
			degree.setReq_ids(degreeForm.req_ids);
			degree.update();
    		return redirect(routes.DegreeController.retrieveDegrees());
    	}
    }
    
    public static Result requestCreateDegreePage(){
    	Form<DegreeAddForm> form = Form.form(DegreeAddForm.class);
    	return ok(views.html.degree_add.render(Requirement.getAll(), form));
    }
    
    public static Result addDegree(){
    	Form<DegreeAddForm> filledForm = Form.form(DegreeAddForm.class).bindFromRequest();
    	DegreeAddForm degreeForm = filledForm.get();
    	Degree degree = Degree.createNewEntity();
    	degree.setTitle(degreeForm.title);
		degree.setReq_ids(degreeForm.req_ids);
    	degree.save();
    	return redirect(routes.DegreeController.retrieveDegrees());
    } 
    
}
