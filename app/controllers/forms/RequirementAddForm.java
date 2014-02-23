package controllers.forms;

import play.data.validation.Constraints.Required;

public class RequirementAddForm {
	
	@Required
	public String title;

	@Required
	public String sr_ids;
}
