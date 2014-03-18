package controllers.forms;

import play.data.validation.Constraints.Required;

public class SrAddForm {
	
	@Required
	public String title;

	@Required
	public Integer required_num;
	
	@Required
	public Integer cg_id;
	
}
