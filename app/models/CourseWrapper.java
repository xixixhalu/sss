package models;

public class CourseWrapper {
	public boolean id;
	public boolean prefix;
	public boolean number;
	public boolean title;
	public boolean credit;
	public boolean prereq;
	public boolean coreq;
	public boolean oncampus;
	public boolean online;
	
	public CourseWrapper() {
		this.id = false;
		this.prefix = false;
		this.number = false;
		this.title = false;
		this.credit = false;
		this.prereq = false;
		this.coreq = false;
		this.oncampus = false;
		this.online = false;
	}
	
	public CourseWrapper(boolean id, boolean prefix, boolean number, 
			boolean title, boolean credit, boolean prereq, 
			boolean coreq, boolean oncampus, boolean online) {
		
		this.id = id;
		this.prefix = prefix;
		this.number = number;
		this.title = title;
		this.credit = credit;
		this.prereq = prereq;
		this.coreq = coreq;
		this.oncampus = oncampus;
		this.online = online;
	}
}
