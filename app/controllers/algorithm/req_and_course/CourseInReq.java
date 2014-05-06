package controllers.algorithm.req_and_course;

public class CourseInReq {
	public CourseInReq next;
	public int cId=0;
	public String courseName;
	public String abbreviation;
	public int credit;
	public int needFinish;
	public boolean statisfied;
	public boolean chosen; //be chosen or not
	public int minDepth=10000;
	public int maxDepth=0;
	public int semester=-1;
	
	/**
	 * Check if this course node is null
	 * 
	 * @param null
	 * 
	 * @return true is null, false is not null
	 */
	public boolean isNull(){//if this course is null
		
		if(this.cId==0){
			return true; 
		}
		return false;
	}
	
	public CourseInReq(int cId){
		this.cId = cId;
	}
	
	
	
	public CourseInReq(int simpleReqID, String simpleReqName, int needFinish){
		this.cId = simpleReqID;
		this.courseName  = simpleReqName;
		this.needFinish = needFinish;
	}
	
	public void showNode(){
		System.out.println(cId + " " + chosen);
	}
	
	public void showFirstNode(){
		System.out.println(cId + " " + statisfied);
	}
}
