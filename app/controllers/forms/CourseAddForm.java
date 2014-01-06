package controllers.forms;

import play.data.validation.Constraints.Required;

public class CourseAddForm {
	@Required
	public String prefix;
	@Required
	public Integer number;
	@Required
	public String title;
	@Required
	public Integer credit;
	
	public String prerequisite_ids;

	public String corequisite_ids;

	public String oncampus;

	public String online;
}
