package controllers.forms;

import play.data.validation.Constraints.Required;

public class RequirementEditForm {
	
	@Required
	public String title;

	@Required
	public String sr_ids;
}
