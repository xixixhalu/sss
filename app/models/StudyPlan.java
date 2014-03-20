package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import models.entities.ESr;


/* Bowen: 
 * This class is used for encapsulating the data entity, so that any extended functions
 * AND exceptions handling can be added here.
 * 
 * Exceptions have not been taken into account, and will be added later*/


public class StudyPlan extends Model{
	
	public Integer degreeId;
	public class Requirement{
		Integer id;
		String title;
		
	}
	List<List<List<String>>> degreeProgram = new ArrayList<List<List<String>>>();
	List<List<String>> requirement = new ArrayList<List<String>>();
	List<String> simpleReq = new ArrayList<String>();
}