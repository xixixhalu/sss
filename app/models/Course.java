package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity 
@Table(name = "course")
public class Course extends Model{
	
	@Id
	@Column(name="course_id_pk")
	public Integer id;
	
	@Column(name="course_prefix")
	public String prefix;
	
	@Column(name="course_number")
	public Integer number;
	
	@Column(name="course_title")
	public String title;
	
	@Column(name="course_credit")
	public Integer credit;
	
	@Column(name="course_prerequisite_ids")
	public String prerequisite_ids;
	
	@Column(name="course_corequisite_ids")
	public String corequisite_ids;
	
	@Column(name="course_oncampus")
	public String oncampus;
	
	@Column(name="course_online")
	public String online;
	
	/** Finder<Integer, Bar>, private variable, 
	 * Method to realize select, update, delete statement in Play Framework*/
	private static Finder<Integer,Course> finder = 
			new Finder<Integer,Course>( Integer.class, Course.class );
	
	/** save() : void, 
	 * Corresponding to insert statement*/
	public void save()
	{
		super.save();
	}
	
	/** find() : List<Course>, 
	 * Corresponding to select statement*/
	public static List<Course> find()
	{
		return finder.all();
	}
	
	public static Course find(Integer id){
		return finder.ref(id);
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		  finder.ref(id).delete();  
	}
	
	/*
	public static void update(Course course){
		 finder.byId(course.id).update();
	}
	*/

	/** Getter and Setter*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getPrerequisite_ids() {
		return prerequisite_ids;
	}

	public void setPrerequisite_ids(String prerequisite_ids) {
		this.prerequisite_ids = prerequisite_ids;
	}

	public String getCorequisite_ids() {
		return corequisite_ids;
	}

	public void setCorequisite_ids(String corequisite_ids) {
		this.corequisite_ids = corequisite_ids;
	}

	public String getOncampus() {
		return oncampus;
	}

	public void setOncampus(String oncampus) {
		this.oncampus = oncampus;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
}
