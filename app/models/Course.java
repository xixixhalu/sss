package models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.avaje.ebean.Ebean;

import play.Logger;
import play.db.ebean.*;
import models.Course;
import models.entities.ECourse;


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
		try{
			ECourse course = Ebean.find(ECourse.class, id);
			return wrap(course);
		}catch(Exception e)
		{
			play.Logger.debug(e.toString());
			return wrap(new ECourse());
		}
	}
	
	/** delete(Integer), 
	 * Corresponding to delete statement*/
	public static void delete(Integer id) {
		/**
		 * cascade
		 */
		List<Course> courses = Course.getAll();
		for (Course course : courses) {
			try {
				JSONArray jarr_pre = new JSONArray(course.getPrerequisite_ids());
				JSONArray jarr_co = new JSONArray(course.getCorequisite_ids());
				JSONArray newjarr_pre = new JSONArray();
				JSONArray newjarr_co = new JSONArray();
				for (int i = 0; i < jarr_pre.length(); i++) {
					JSONObject json = jarr_pre.getJSONObject(i);
					if (!id.toString().equals(json.get("id"))){
						newjarr_pre.put(json);
					}
				}
				for (int i = 0; i < jarr_co.length(); i++) {
					JSONObject json = jarr_co.getJSONObject(i);
					if (!id.toString().equals(json.get("id"))){
						newjarr_co.put(json);
					}
				}
				if (newjarr_pre.length() == 1) {
					JSONObject json = newjarr_pre.getJSONObject(0);
					json.remove("relation");
					json.put("relation", "");
				}
				if (newjarr_co.length() == 1) {
					JSONObject json = newjarr_co.getJSONObject(0);
					json.remove("relation");
					json.put("relation", "");
				}
				
				course.setPrerequisite_ids(newjarr_pre.toString());
				course.setCorequisite_ids(newjarr_co.toString());
				
				course.update();
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Cg> cgs = Cg.getAll();
		for (Cg cg : cgs) {
			ArrayList<String> ids = cg.getCourse_ids();
			ids.remove(id.toString());

			StringBuffer updated_course_ids = new StringBuffer();
			for (int i = 0; i < ids.size(); ++i) {
				updated_course_ids.append(ids.get(i));
				if (i != ids.size() - 1)
					updated_course_ids.append(",");
			}
			
			if (ids.size() == 0)
				Cg.delete(cg.getId());
			else
				cg.setCourse_ids(updated_course_ids.toString());
			cg.update();
			
			
			List<Sr> srs = Sr.getAll();
			for (Sr sr : srs) {
				if (sr.getCg_id().equals(cg.getId())) {
					if (sr.getRequired_num() > cg.getCourse_ids().size()) {
						sr.setRequired_num(cg.getCourse_ids().size());
						sr.update();
						break;
					}
				}
			}
		}
		
		
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
		if(prerequisite_ids != "")
			this.entity.setPrerequisite_ids(prerequisite_ids);
		else
			this.entity.setPrerequisite_ids("null");
		return;
	}

	public String getCorequisite_ids() {
		return this.entity.getCorequisite_ids();
	}

	public void setCorequisite_ids(String corequisite_ids) {
		if(corequisite_ids != "")
			this.entity.setCorequisite_ids(corequisite_ids);
		else
			this.entity.setCorequisite_ids("null");
		return;
	}

	public ArrayList<String> getOncampus() {
		ArrayList<String> OncampusList = new ArrayList<String>();
		String oncampus = this.entity.getOncampus();
		if(oncampus != "null")
    	{
    		String[] oncampusList = oncampus.split(",");
    		int pos = 0;
    		for(int i = 0; i < 4; i++)
    		{
    			if(pos < oncampusList.length && oncampusList[pos].equals(Integer.toString(i)))
    				OncampusList.add(oncampusList[pos++]);
    			else
    				OncampusList.add(null);
    		}
    	}
    	else
    	{
    		for(int i = 0; i < 4; i++)
    			OncampusList.add(null);
    	}
		return OncampusList;
	}

	public void setOncampus(ArrayList<String> oncampus) {
		if(oncampus != null)
		{
			String campus = "";
			for(int i = 0; i < oncampus.size(); i++)
			{
				if(oncampus.get(i) != null)
					campus += Integer.toString(i) + ",";
			}
			campus = campus.substring(0, campus.length()-1);
			this.entity.setOncampus(campus);
		}
		else
			this.entity.setOncampus("null");
		return;
	}

	public ArrayList<String> getOnline() {
		ArrayList<String> OnlineList = new ArrayList<String>();
		String online = this.entity.getOnline();
		if(online != "null")
    	{
    		String[] onlineList = online.split(",");
    		int pos = 0;
    		for(int i = 0; i < 4; i++)
    		{
    			if(pos < onlineList.length && onlineList[pos].equals(Integer.toString(i)))
    				OnlineList.add(onlineList[pos++]);
    			else
    				OnlineList.add(null);
    		}
    	}
    	else
    	{
    		for(int i = 0; i < 4; i++)
    			OnlineList.add(null);
    	}
		return OnlineList;
	}

	public void setOnline(ArrayList<String> online) {
		if(online != null)
		{
			String line = "";
			for(int i = 0; i < online.size(); i++)
			{
				if(online.get(i) != null)
					line += Integer.toString(i) + ",";
			}
			line = line.substring(0, line.length()-1);
			this.entity.setOnline(line);
		}
		else
			this.entity.setOnline("null");

		return;
	}
	
	public JSONObject toJson(CourseWrapper cw) {
		JSONObject json = new JSONObject();
		JSONArray ja = null;
		
		if (cw.id)
			json.put("id", this.entity.getId());
		if (cw.prefix)
			json.put("prefix", this.entity.getPrefix());
		if (cw.number)
			json.put("num", this.entity.getNumber());
		if (cw.title)
			json.put("title", this.entity.getTitle());
		if (cw.credit)
			json.put("credit", this.entity.getCredit());
		if (cw.prereq) {
			try {
				ja = new JSONArray(this.entity.getPrerequisite_ids());
			} catch (ParseException e) {
				Logger.debug(e.toString());
				e.printStackTrace();
			}
			json.put("prereq", ja);
		}
		if (cw.coreq) {
			try {
				ja = new JSONArray(this.entity.getCorequisite_ids());
			} catch (ParseException e) {
				Logger.debug(e.toString());
				e.printStackTrace();
			}
			json.put("coreq", ja);
		}
		if (cw.oncampus)
			json.put("oncampus", this.entity.getOncampus());
		if (cw.online)
			json.put("online", this.entity.getOnline());
		
		return json;
	}
	
	/**
	 * @author tongrui
	 * @return something like "CS115, CS184" or "id, id"
	 * @param choice = 1 (prefix+number), choice = 2 (id), otherwise nothing
	 */
	public String getPrereq (int choice) {
		StringBuffer str = new StringBuffer();
		JSONArray ja = null;
		JSONObject json = null;
		
		if (!this.entity.getPrerequisite_ids().equals("[]")) {
			try {
				ja = new JSONArray(this.entity.getPrerequisite_ids());

				int id = 0;
				Course course = null;
				for (int i = 0; i < ja.length(); i++) {
					json = (JSONObject)ja.get(i);
					id = json.getInt("id");
					course = Course.findById(id);
					
					String relation = (String) json.get("relation");
					if (relation.equals("and"))
						relation = ",";
					str.append(" " + relation + " ");
					if (choice == 1)
						str.append(course.getPrefix() + course.getNumber());
					else if (choice == 2)
						str.append(id);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				Logger.debug(this.getPrefix() + this.getNumber() + this.getTitle());
			}
		} else {
			str.append("-");
		}
		
		return str.toString();
	}
	
	/**
	 * @author tongrui
	 * @return something like "CS115 or CS184"
	 * @param choice = 1 (prefix+number), choice = 2 (id), otherwise nothing
	 */
	public String getCoreq(int choice) {
		StringBuffer str = new StringBuffer();
		JSONArray ja = null;
		JSONObject json = null;
		
		if (!this.entity.getCorequisite_ids().equals("[]")) {
			try {
				ja = new JSONArray(this.entity.getCorequisite_ids());

				int id = 0;
				Course course = null;
				for (int i = 0; i < ja.length(); i++) {
					json = (JSONObject)ja.get(i);
					id = (int) json.getInt("id");
					course = Course.findById(id);
					
					String relation = (String) json.get("relation");
					if (relation.equals("and"))
						relation = ",";
					str.append(" " + relation + " ");
					if (choice == 1)
						str.append(course.getPrefix() + course.getNumber());
					else if (choice == 2)
						str.append(id);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				Logger.debug(this.getPrefix() + this.getNumber() + this.getTitle());
			}
		} else {
			str.append("-");
		}
		
		return str.toString();
	}
}
