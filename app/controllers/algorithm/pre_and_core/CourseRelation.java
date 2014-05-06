package controllers.algorithm.pre_and_core;

public class CourseRelation {
	public int tailCourseID; //node at the tail
	public int headCourseID; //node at the head
	public CourseRelation tlink; //next arc with same tail
	public CourseRelation hlink; //next arc with same head
	public int info; //
	
	public CourseRelation(){
		this.headCourseID=0;
		this.tailCourseID=0;
		this.hlink=null;
		this.tlink=null;
	}
	
	
	public CourseRelation(int tail, int head, int info){ 
		this.tailCourseID = tail;//
		this.headCourseID = head;
		this.info = info;
		this.hlink=null;
		this.tlink=null;
		
	}
	
	/**
	 * Display course relation 
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showArcFromTail(){
		String outPut = this.tailCourseID + "-->" +this.headCourseID+" with " +info;
		System.out.println(outPut);
		
		return;
	}
	
	/**
	 * Display course relation 
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showArcFromHead(){
		String outPut = this.tailCourseID + "-->" +this.headCourseID+" with " +info;
		System.out.println(outPut);
		return;
	}
}
