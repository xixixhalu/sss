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
	
	/**
	 * retrieve Requirement Information
	 * @return requirement list page
	 */
	public static Result retrieveRequirements() {
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
		try{
			return ok(views.html.requirement_list.render(Requirement.getAll()));
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot retrieve requirement list"));
    	}
    }
	
	/**
	 * delete requirement
	 * @param id
	 *         - id of the requirement selected
	 * */
	public static Result deleteRequirement(Integer id){
		String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
		try{
	    	Requirement.delete(id);
	    	return redirect(routes.RequirementController.retrieveRequirements());
		}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot delete requirement"));
    	}
    }
    
	/**
	 * request edit requirement page
	 * @param id
	 *         - id of the requirement selected
	 * */
    public static Result requestEditRequirementPage(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
	    	Form<RequirementEditForm> form = Form.form(RequirementEditForm.class);
	    	return ok(views.html.requirement_edit.render(Sr.getAll(), Requirement.findById(id), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render("Cannot find requirement information"));
    	}
    }
    
    /**
	 * update the selected requirement information
	 * @param id
	 *         - id of the requirement selected
	 * */
    public static Result updateRequirement(Integer id){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<RequirementEditForm> filledForm = Form.form(RequirementEditForm.class).bindFromRequest();
    	
    	if(filledForm.hasErrors()) {
    		return badRequest("Not all mandatory fields correct or entered.");
    	} 
    	try{
    		RequirementEditForm requirementForm = filledForm.get();
			Requirement requirement = Requirement.findById(id);
			requirement.setTitle(requirementForm.title);
			requirement.setSr_ids(requirementForm.sr_ids);
			requirement.update();
    		return redirect(routes.RequirementController.retrieveRequirements());
    	}
    	catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    	
    }
    
    /**
     * request create requirement page
     * @return requirement information input page
     */
    public static Result requestCreateRequirementPage(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	try{
	    	Form<RequirementAddForm> form = Form.form(RequirementAddForm.class);
	    	return ok(views.html.requirement_add.render(Sr.getAll(), form));
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    }
    
    /**
     * submit a new requirement information
     * @return requirement list page
     */
    public static Result addRequirement(){
    	String user = session().get("user");
    	if(user == null)
    	{
    		return redirect(routes.UserController.retrieveLoginPage());
    	}
    	Form<RequirementAddForm> filledForm = Form.form(RequirementAddForm.class).bindFromRequest();
    	if (filledForm.hasErrors())
			return badRequest(views.html.error.render("Not all mandatory fields correct or entered."));
    	try{
	    	RequirementAddForm requirementForm = filledForm.get();
	    	Requirement requirement = Requirement.createNewEntity();
	    	requirement.setTitle(requirementForm.title);
			requirement.setSr_ids(requirementForm.sr_ids);
	    	requirement.save();
	    	return redirect(routes.RequirementController.retrieveRequirements());
    	}catch(Exception e)
    	{
    		return badRequest(views.html.error.render(e.toString()));
    	}
    } 
    
}
