package controllers.forms;

import play.data.validation.Constraints.Required;

public class SrEditForm {
	
	@Required
	public String title;

	@Required
	public Integer required_num;
	
	@Required
	public Integer cg_id;
	
	public boolean not;
}
