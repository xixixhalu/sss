package controllers.forms;

import play.data.validation.Constraints.Required;

public class CgAddForm {
	
	@Required
	public String prefix;
	
	@Required
	public String title;

	@Required
	public String course_ids;

}
