package controllers.forms;

import play.data.validation.Constraints.Required;

public class DegreeAddForm {

	@Required
	public String title;

	@Required
	public String req_ids;
}
