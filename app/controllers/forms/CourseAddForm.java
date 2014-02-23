package controllers.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

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

	public ArrayList<String> oncampus;

	public ArrayList<String> online;
}
