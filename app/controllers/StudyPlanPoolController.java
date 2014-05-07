package controllers;

import java.util.concurrent.ConcurrentHashMap;

import models.StudyPlan;

import java.util.Timer;
import java.util.UUID;

import controllers.utility.TimerPerformer;
import play.Logger;
import play.mvc.Controller;

public class StudyPlanPoolController extends Controller{
	public static ConcurrentHashMap<String, StudyPlan> studyPlanPool = 
			new ConcurrentHashMap<String, StudyPlan>();
	
	/**
	 * insert a new study plan when there is a new user coming
	 * @param studyplan
	 * 			- A degree study plan which consist of a lot build-in data structure
	 */
	public static void insertStudyPlan(StudyPlan studyplan)
	{
		String uuid = UUID.randomUUID().toString();

		session("uuid", uuid);
		studyPlanPool.put(uuid, studyplan);
		
		Timer timer = new Timer();
		TimerPerformer timerPerformer = new TimerPerformer();
		timerPerformer.delay = 7200000;
		timerPerformer.uuid = uuid;
		timerPerformer.studyPlanPool = studyPlanPool;
		timer.schedule(timerPerformer, timerPerformer.delay);
	}
	
	/**
	 * get user's study plan by mapping UUID
	 * @return specified study plan
	 */
	public static StudyPlan getStudyPlan()
	{
		return studyPlanPool.get(session().get("uuid"));
	}
	
	/**
	 * to check if the session is timeout
	 */
	public static boolean isStudyPlanExists()
	{
		if(!session().containsKey("uuid"))
		{
			return false;
		}
		else
		{
			String uuid = session().get("uuid");
			if(!studyPlanPool.containsKey(uuid))
				return false;
			else
				return true;
		}
	}
	
	/**
	 * get the number of study plan in the memory
	 */
	public static int poolSize()
	{
		return studyPlanPool.size();
	}
	
	/**
	 * remove the study plan from memory
	 */
	public static void removeStudyPlan()
	{
		Logger.debug("count"+studyPlanPool.size()+" "+session().size());
		String uuid = session().get("uuid");
		//StudyPlan studyplan = studyPlanPool.get(uuid);
		studyPlanPool.remove(uuid);
		session().remove("uuid");
		Logger.debug("count"+studyPlanPool.size()+" "+session().size());
		
	}
}
