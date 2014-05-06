package controllers.utility;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import models.StudyPlan;

public class TimerPerformer extends TimerTask {
	public long delay = 7200000;
	public String uuid = "";
	public ConcurrentHashMap<String, StudyPlan> studyPlanPool = null;
	
	/**
	 * remove the study plan from memory when time out
	 * 
	 * */
	@Override
	public void run() {
		
		if(studyPlanPool != null)
			studyPlanPool.remove(uuid);
	}

}
