package controllers.forms;

import play.data.validation.Constraints.Required;

public class TakeForm {
	@Required
	public String wantTakeCourses;
	public String alreadyTakenCourses;
}
