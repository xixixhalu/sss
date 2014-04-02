package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.*;

import com.avaje.ebean.Ebean;

import play.Logger;
import play.db.ebean.*;
import models.entities.ERequirement;


/* Bowen: 
 * This class is used for encapsulating the data entity, so that any extended functions
 * AND exceptions handling can be added here.
 * 
 * Exceptions have not been taken into account, and will be added later*/


public class Requirement extends Model{
	
	private ERequirement entity;
	
	/** wrap(ERequirement) : Requirement,
	 * Wrap the requirement Entity*/
	private static Requirement wrap(ERequirement erequirement) {
		Requirement requirement = new Requirement();
		requirement.entity = erequirement;
		return erequirement == null ? null : requirement;
	}

	/** unwrap(Requirement) : ERequirement,
	 * Unwrap the requirement Entity*/
	private static ERequirement unwrap(Requirement requirement) {
		return requirement == null ? null : requirement.entity;
	}
	
	/** getAll() : List<Requirement>,
	 * Get the information of all requirements from database*/
	public static List<Requirement> getAll() {
		List<ERequirement> list = Ebean.find(ERequirement.class).findList();
		List<Requirement> requirementList = new ArrayList<Requirement>(list.size());

		Iterator<ERequirement> iter = list.iterator();
		while(iter.hasNext()){
			Requirement requirement = new Requirement();
			requirement.entity = iter.next();
			requirementList.add(requirement);
		}
		return requirementList;

	}
	
	/** findById(Integer) : Requirement, 
	 * Corresponding to select statement for one record*/
	public static Requirement findById(Integer id){
		try{
			ERequirement requirement = Ebean.find(ERequirement.class, id);
			return wrap(requirement);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new ERequirement());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		/**
		 * cascade
		 */
		List<Degree> degrees = Degree.getAll();
		for (Degree degree : degrees) {
			ArrayList<String> req_ids = degree.getReq_ids();
			
			req_ids.remove(id.toString());
			
			StringBuffer updated_req_ids = new StringBuffer();
			for (int i = 0; i < req_ids.size(); ++i) {
				updated_req_ids.append(req_ids.get(i));
				if (i != req_ids.size() - 1)
					updated_req_ids.append(",");
			}
			degree.setReq_ids(updated_req_ids.toString());
			degree.update();
		}
		
		ERequirement erequirement = Ebean.find(ERequirement.class, id);
		if(erequirement != null)
			erequirement.delete();
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
	
	/** createNewEntity() : Requirement, 
	 * Return a new ERequirement entity*/
	public static Requirement createNewEntity()
	{
		ERequirement entity = new ERequirement();
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

	public String getTitle() {
		return this.entity.getTitle();
	}
	
	public void setTitle(String title) {
		this.entity.setTitle(title);
	}
	
	public String getSr_ids() {
		return this.entity.getSr_ids();

	}

	public void setSr_ids(String sr_ids) {
		this.entity.setSr_ids(sr_ids);
	}
	
}