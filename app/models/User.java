package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.entities.EUser;


public class User extends Model{
	
	private EUser entity;
	
	/** wrap(EUser) : User,
	 * Wrap the user Entity*/
	private static User wrap(EUser euser) {
		User user = new User();
		user.entity = euser;
		return euser == null ? null : user;
	}

	/** unwrap(User) : EUser,
	 * Unwrap the user Entity*/
	private static EUser unwrap(User user) {
		return user == null ? null : user.entity;
	}
	
	/** getAll() : List<User>,
	 * Get the information of all users from database*/
	public static List<User> getAll() {
		List<EUser> list = Ebean.find(EUser.class).findList();
		List<User> userList = new ArrayList<User>(list.size());

		Iterator<EUser> iter = list.iterator();
		while(iter.hasNext()){
			User user = new User();
			user.entity = iter.next();
			userList.add(user);
		}
		return userList;

	}
	
	/** findById(Integer) : User, 
	 * Corresponding to select statement for one record*/
	public static User findById(Integer id){
		try{
			EUser user = Ebean.find(EUser.class, id);
			return wrap(user);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new EUser());
		}
	}
	
	/** findByName(String) : User, 
	 * Corresponding to select statement for one record*/
	public static User findByName(String Name){
		try{
			EUser user = Ebean.find(EUser.class).where("user_name=\""+Name+"\"").findUnique();
			return wrap(user);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new EUser());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		EUser euser = Ebean.find(EUser.class, id);
		if(euser != null)
			euser.delete();
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
	
	/** createNewEntity() : User, 
	 * Return a new EUser entity*/
	public static User createNewEntity()
	{
		EUser entity = new EUser();
		return wrap(entity);
	}
	
	/** Getter and Setter*/
	public Integer getId() {
		return this.entity.getId();
	}

//	public void setId(Integer id) {
//		this.entity.setId(id);
//	}
	
	public String getName() {
		return this.entity.getName();
	}

	public void setName(String name) {
		this.entity.setName(name);
	}

	public String getPassword() {
		return this.entity.getPassword();
	}

	public void setPassword(String password) {
		this.entity.setPassword(password);
	}
	
}