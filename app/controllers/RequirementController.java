package controllers;

import controllers.forms.RequirementAddForm;
import controllers.forms.RequirementEditForm;
import models.Requirement;
import models.Sr;
import models.Course;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class RequirementController extends Controller{
	
	public static Result retrieveRequirements() {
        return ok(views.html.requirement_list.render(Requirement.getAll()));
    }
	
	public static Result deleteRequirement(Integer id){
    	Requirement.delete(id);
    	return redirect(routes.RequirementController.retrieveRequirements());
    }
    
    public static Result requestEditRequirementPage(Integer id){
    	Form<RequirementEditForm> form = Form.form(RequirementEditForm.class);

    	return ok(views.html.requirement_edit.render(Sr.getAll(), Requirement.findById(id), form));
    }
    
    public static Result updateRequirement(Integer id){
    	Form<RequirementEditForm> filledForm = Form.form(RequirementEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Wrong");
    	} 
    	else {
    		RequirementEditForm requirementForm = filledForm.get();
			Requirement requirement = Requirement.findById(id);
			requirement.setTitle(requirementForm.title);
			requirement.setSr_ids(requirementForm.sr_ids);
			requirement.update();
    		return redirect(routes.RequirementController.retrieveRequirements());
    	}
    }
    
    public static Result requestCreateRequirementPage(){
    	Form<RequirementAddForm> form = Form.form(RequirementAddForm.class);
    	return ok(views.html.requirement_add.render(Sr.getAll(), form));
    }
    
    public static Result addRequirement(){
    	Form<RequirementAddForm> filledForm = Form.form(RequirementAddForm.class).bindFromRequest();
    	RequirementAddForm requirementForm = filledForm.get();
    	Requirement requirement = Requirement.createNewEntity();
    	requirement.setTitle(requirementForm.title);
		requirement.setSr_ids(requirementForm.sr_ids);
    	requirement.save();
    	return redirect(routes.RequirementController.retrieveRequirements());
    } 
    
}
