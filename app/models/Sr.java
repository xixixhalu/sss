package models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.entities.ESr;


public class Sr extends Model{
	
	private ESr entity;
	
	/** wrap(ESr) : Sr,
	 * Wrap the sr Entity*/
	private static Sr wrap(ESr esr) {
		Sr sr = new Sr();
		sr.entity = esr;
		return esr == null ? null : sr;
	}

	/** unwrap(Sr) : ESr,
	 * Unwrap the sr Entity*/
	private static ESr unwrap(Sr sr) {
		return sr == null ? null : sr.entity;
	}
	
	/** getAll() : List<Sr>,
	 * Get the information of all srs from database*/
	public static List<Sr> getAll() {
		List<ESr> list = Ebean.find(ESr.class).findList();
		List<Sr> srList = new ArrayList<Sr>(list.size());

		Iterator<ESr> iter = list.iterator();
		while(iter.hasNext()){
			Sr sr = new Sr();
			sr.entity = iter.next();
			srList.add(sr);
		}
		return srList;

	}
	
	/** findById(Integer) : Sr, 
	 * Corresponding to select statement for one record*/
	public static Sr findById(Integer id){
		try{
			ESr sr = Ebean.find(ESr.class, id);
			return wrap(sr);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new ESr());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		/**
		 * cascade
		 */
		List<Requirement> requirements = Requirement.getAll();
		for (Requirement requirement : requirements) {
			try {
				JSONArray jarr = new JSONArray(requirement.getSr_ids());
				JSONArray newjarr = new JSONArray();
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject json = jarr.getJSONObject(i);
					if (!id.toString().equals(json.get("id"))){
						newjarr.put(json);
					}
				}
				if (newjarr.length() != 0)
					requirement.setSr_ids(newjarr.toString());
				else
					Requirement.delete(requirement.getId());
				
				requirement.update();
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		ESr esr = Ebean.find(ESr.class, id);
		if(esr != null)
			esr.delete();
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
	
	/** createNewEntity() : Sr, 
	 * Return a new ESr entity*/
	public static Sr createNewEntity()
	{
		ESr entity = new ESr();
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
	
	public Integer getRequired_num() {
		return this.entity.getRequired_num();
	}

	public void setRequired_num(Integer required_num) {
		this.entity.setRequired_num(required_num);
	}

	public Integer getCg_id() {
		return this.entity.getCg_id();
	}

	public void setCg_id(Integer cg_id) {
		this.entity.setCg_id(cg_id);
	}

}