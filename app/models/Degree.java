package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.entities.EDegree;


/* Bowen: 
 * This class is used for encapsulating the data entity, so that any extended functions
 * AND exceptions handling can be added here.
 * 
 * Exceptions have not been taken into account, and will be added later*/


public class Degree extends Model{
	
	private EDegree entity;
	
	/** wrap(EDegree) : Degree,
	 * Wrap the degree Entity*/
	private static Degree wrap(EDegree edegree) {
		Degree degree = new Degree();
		degree.entity = edegree;
		return edegree == null ? null : degree;
	}

	/** unwrap(Degree) : EDegree,
	 * Unwrap the degree Entity*/
	private static EDegree unwrap(Degree degree) {
		return degree == null ? null : degree.entity;
	}
	
	/** getAll() : List<Degree>,
	 * Get the information of all degrees from database*/
	public static List<Degree> getAll() {
		List<EDegree> list = Ebean.find(EDegree.class).findList();
		List<Degree> degreeList = new ArrayList<Degree>(list.size());

		Iterator<EDegree> iter = list.iterator();
		while(iter.hasNext()){
			Degree degree = new Degree();
			degree.entity = iter.next();
			degreeList.add(degree);
		}
		return degreeList;

	}
	
	/** findById(Integer) : Degree, 
	 * Corresponding to select statement for one record*/
	public static Degree findById(Integer id){
		try{
			EDegree degree = Ebean.find(EDegree.class, id);
			return wrap(degree);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new EDegree());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		EDegree edegree = Ebean.find(EDegree.class, id);
		if(edegree != null)
			edegree.delete();
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
	
	/** createNewEntity() : Degree, 
	 * Return a new EDegree entity*/
	public static Degree createNewEntity()
	{
		EDegree entity = new EDegree();
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
	
	public ArrayList<String> getReq_ids() {
		ArrayList<String> ReqList = new ArrayList<String>();
		String reqs = this.entity.getReq_ids();
		if(reqs != "null")
    	{
    		String[] reqList = reqs.split(",");

    		for(int i = 0; i < reqList.length; i++)
    		{
    			ReqList.add(reqList[i]);
    		}
    	}
		return ReqList;
	}

	public void setReq_ids(String req_ids) {
		this.entity.setReq_ids(req_ids);
	}
	
}