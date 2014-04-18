package controllers.utility;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import models.StudyPlan;

public class TimerPerformer extends TimerTask {
	public long delay = 3600000;
	public String uuid = "";
	public ConcurrentHashMap<String, StudyPlan> studyPlanPool = null;
	@Override
	public void run() {
		
		if(studyPlanPool != null)
			studyPlanPool.remove(uuid);
	}

}
