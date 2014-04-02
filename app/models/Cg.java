package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.Cg;
import models.entities.ECg;


/* Bowen: 
 * This class is used for encapsulating the data entity, so that any extended functions
 * AND exceptions handling can be added here.
 * 
 * Exceptions have not been taken into account, and will be added later*/


public class Cg extends Model{
	
	private ECg entity;
	
	/** wrap(ECg) : Cg,
	 * Wrap the cg Entity*/
	private static Cg wrap(ECg ecg) {
		Cg cg = new Cg();
		cg.entity = ecg;
		return ecg == null ? null : cg;
	}

	/** unwrap(Cg) : ECg,
	 * Unwrap the cg Entity*/
	private static ECg unwrap(Cg cg) {
		return cg == null ? null : cg.entity;
	}
	
	/** getAll() : List<Cg>,
	 * Get the information of all cgs from database*/
	public static List<Cg> getAll() {
		List<ECg> list = Ebean.find(ECg.class).findList();
		List<Cg> cgList = new ArrayList<Cg>(list.size());

		Iterator<ECg> iter = list.iterator();
		while(iter.hasNext()){
			Cg cg = new Cg();
			cg.entity = iter.next();
			cgList.add(cg);
		}
		return cgList;

	}
	
	/** findById(Integer) : Cg, 
	 * Corresponding to select statement for one record*/
	public static Cg findById(Integer id){
		try{
			ECg cg = Ebean.find(ECg.class, id);
			return wrap(cg);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new ECg());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		/**
		 * Cascade
		 */
		List<Sr> srs = Sr.getAll();
		for (Sr sr : srs) {
			if (sr.getCg_id().equals(id)){
				Sr.delete(sr.getId());
				sr.update();
				break;
			}
		}
		
		ECg ecg = Ebean.find(ECg.class, id);
		if(ecg != null)
			ecg.delete();
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
	
	/** createNewEntity() : Cg, 
	 * Return a new ECg entity*/
	public static Cg createNewEntity()
	{
		ECg entity = new ECg();
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
	}

	public String getTitle() {
		return this.entity.getTitle();
	}
	
	public void setTitle(String title) {
		this.entity.setTitle(title);
	}

	public ArrayList<String> getCourse_ids() {
		ArrayList<String> CourseList = new ArrayList<String>();
		String courses = this.entity.getCourse_ids();
		if(courses != "null")
    	{
    		String[] courseList = courses.split(",");

    		for(int i = 0; i < courseList.length; i++)
    		{
    			CourseList.add(courseList[i]);
    		}
    	}
		return CourseList;
	}

	public void setCourse_ids(String course_ids) {
		this.entity.setCourse_ids(course_ids);
	}
}
