package controllers;

import java.util.concurrent.ConcurrentHashMap;

import models.StudyPlan;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import controllers.utility.TimerPerformer;
import play.Logger;
import play.mvc.Controller;

public class StudyPlanPoolController extends Controller{
	public static ConcurrentHashMap<String, StudyPlan> studyPlanPool = 
			new ConcurrentHashMap<String, StudyPlan>();
	
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
	
	public static StudyPlan getStudyPlan()
	{
		return studyPlanPool.get(session().get("uuid"));
	}
	
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
	
	public static int poolSize()
	{
		return studyPlanPool.size();
	}
	
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
