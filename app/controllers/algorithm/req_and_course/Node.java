package controllers.algorithm.req_and_course;

//requirement link list
public class Node {

	public Node next;
	public int cName=0;
	public String courseName;
	public String abbreviation;
	public int credit;
	public String semester[];
	public int needFinish;
	public boolean statisfied;
	public boolean chosen; //be chosen or not
	
	
	public boolean isNull(){//if this course is null
		
		if(this.cName==0){
			return true; 
		}
		return false;
	}
	
	public Node(int cName){
		this.cName = cName;
	}
	
	
	
	public Node(int simpleReqID, String simpleReqName, int needFinish){
		this.cName = simpleReqID;
		this.courseName  = simpleReqName;
		this.needFinish = needFinish;
	}
	
	public void showNode(){
		System.out.println(cName + " " + chosen);
	}
	
	public void showFirstNode(){
		System.out.println(cName + " " + statisfied);
	}
}
