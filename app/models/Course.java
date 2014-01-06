package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.Course;
import models.entities.ECourse;


/* Bowen: 
 * This class is used for encapsulating the data entity, so that any extended functions
 * AND exceptions handling can be added here.
 * 
 * Exceptions have not been taken into account, and will be added later*/


public class Course extends Model{
	
	private ECourse entity;
	
	/** wrap(ECourse) : Course,
	 * Wrap the course Entity*/
	private static Course wrap(ECourse ecourse) {
		Course course = new Course();
		course.entity = ecourse;
		return ecourse == null ? null : course;
	}

	/** unwrap(Course) : ECourse,
	 * Unwrap the course Entity*/
	private static ECourse unwrap(Course course) {
		return course == null ? null : course.entity;
	}
	
	/** getAll() : List<Course>,
	 * Get the information of all courses from database*/
	public static List<Course> getAll() {
		List<ECourse> list = Ebean.find(ECourse.class).findList();
		List<Course> courseList = new ArrayList<Course>(list.size());

		Iterator<ECourse> iter = list.iterator();
		while(iter.hasNext()){
			Course course = new Course();
			course.entity = iter.next();
			courseList.add(course);
		}
		return courseList;

	}
	
	/** findById(Integer) : Course, 
	 * Corresponding to select statement for one record*/
	public static Course findById(Integer id){
		ECourse course = Ebean.find(ECourse.class, id);
		return wrap(course);
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		ECourse ecourse = Ebean.find(ECourse.class, id);
		if(ecourse != null)
			ecourse.delete();
		return; 
	}
	
	/** update(), 
	 * Corresponding to update statement*/
	public void update(){
		this.entity.update();
		return;
	}
	
	/** save(), 
	 * Corresponding to create statement*/
	public void save(){
		this.entity.save();
		return;
	}
	
	/** createNewEntity() : Course, 
	 * Return a new ECourse entity*/
	public static Course createNewEntity()
	{
		ECourse entity = new ECourse();
		return wrap(entity);
	}
	
	/** Getter and Setter*/
	public Integer getId() {
		return this.entity.getId();
	}

	/*
	//warning: this action is forbidden
	public void setId(Integer id) {

	}
	*/

	public String getPrefix() {
		return this.entity.getPrefix();
	}

	public void setPrefix(String prefix) {
		this.entity.setPrefix(prefix);
		return;
	}

	public Integer getNumber() {
		return this.entity.getNumber();
	}

	public void setNumber(Integer number) {
		this.entity.setNumber(number);
		return;
	}

	public String getTitle() {
		return this.entity.getTitle();
	}

	public void setTitle(String title) {
		this.entity.setTitle(title);
		return;
	}

	public Integer getCredit() {
		return this.entity.getCredit();
	}

	public void setCredit(Integer credit) {
		this.entity.setCredit(credit);
		return;
	}

	public String getPrerequisite_ids() {
		return this.entity.getPrerequisite_ids();
	}

	public void setPrerequisite_ids(String prerequisite_ids) {
		this.entity.setPrerequisite_ids(prerequisite_ids);
		return;
	}

	public String getCorequisite_ids() {
		return this.entity.getCorequisite_ids();
	}

	public void setCorequisite_ids(String corequisite_ids) {
		this.entity.setCorequisite_ids(corequisite_ids);
		return;
	}

	public String getOncampus() {
		return this.entity.getOncampus();
	}

	public void setOncampus(String oncampus) {
		this.entity.setOncampus(oncampus);
		return;
	}

	public String getOnline() {
		return this.entity.getOnline();
	}

	public void setOnline(String online) {
		this.entity.setOnline(online);
		return;
	}
	
}
