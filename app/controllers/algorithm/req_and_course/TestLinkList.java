package controllers.algorithm.req_and_course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestLinkList {

	public String degreeName;
	public ArrayList<Linklist> course_list = new ArrayList<Linklist>(); //requirement list
	public ArrayList<Course_LinkList> course_list2 = new ArrayList<Course_LinkList>(); //course list
 
	public HashMap<Integer, ArrayList<Node>> course  = new HashMap<Integer, ArrayList<Node>>();
	
	public ArrayList<ComplexReq> allComplexReq = new ArrayList<ComplexReq>();  //a set of all complex requirement
	
	public void displayAllCourse(){
		for (Integer key : this.course.keySet()) {
		    System.out.println("Key = " + key + " - " 
		    		+ this.course.get(key).get(0).cName + " - "
		    		+ this.course.get(key).size());
		}
	}
	
	
	public void addCourse(int courseID){

	}
	
	public boolean prepareInsertSimple(int simpleReqID){
		for(int i =0; i<this.course_list.size();i++){
			if(simpleReqID == this.course_list.get(i).first.cName)
				return true;
		}
		return false;
	}
	
	public boolean prepareInsertCourse(int courseID){
		return this.course.containsKey(courseID);

	}
	
	public boolean prepareInsertCourseLinkList(int courseID){
		for(int i =0; i<this.course_list2.size();i++){
			if(courseID == this.course_list2.get(i).first.rName)
				return true; //this course exists!
		}
		return false;
	}
	
	public TestLinkList(){
	}
	
	public TestLinkList(String degreeName){
		this.degreeName = degreeName;
	}
	
	
	public void addComplexReq(ComplexReq c){
		this.allComplexReq.add(c);
	}
	
	
	
	public void displayallComplexReq() {
		for(int i=0; i<allComplexReq.size();i++){
			this.allComplexReq.get(i).displayComplexReq();
		}
		
	}
	
	
	public void addReq2List(Linklist linklist) {
		this.course_list.add(linklist);
	}

	public void displayReqList() {

		for (int i = 0; i < course_list.size(); i++) {
			course_list.get(i).displayAllNodes();
		}
	}

	public void addReq2List(Course_LinkList course_linklist) {
		this.course_list2.add(course_linklist);
	}

	public void displayCourseList() {

		for (int i = 0; i < course_list2.size(); i++) {
			course_list2.get(i).displayAllNodes();
		}
	}

	// choose a course in a requirement link list
	// First, we need to find this course in a specific requirement. Given a
	// requirement and a course,
	// we need to find them first and than choose them.
	public boolean checkCourseIn_ReqList(int reqName, int courseID) {
		boolean ifFind = false;
		boolean ifChosen = false;
	
		int i =0; //记录requirement的index
		int j = 0; //记录课程链表的index，第几个课程链表，找到后直接修改/判断头结点
		for (; i < course_list.size(); i++) {
			
			ifFind = this.course_list.get(i).chooseCourse(reqName, courseID);
			if (ifFind) {
				break;//Find this course in this requirement
			}
		}
		if(!ifFind)
			return false;  //error detection: if no this course in this requirement
		
		for (; j < this.course_list2.size(); j++) {
			if(this.course_list2.get(j).first.rName == courseID){
				ifChosen = this.course_list2.get(j).checkChosen();
				
				if (ifChosen) {
					return false; // this course has been chosen, so it cannot be chosen again.
				}
				break;
			}
			
		}
		//选课
		this.course_list.get(i).set2ChooseCourse(courseID); //在requirement链表中标记
		for(int k=0; k<this.course_list.size(); k++){
			if(k!=i){
				this.course_list.get(k).deleteByData(courseID);
				System.out.print("delete success"+"\n");
			}
		}
		this.course_list2.get(j).set_course_be_chosen(reqName); //在课程表链表中标记
		
		return true; // this course exists and has not been chosen.
	}
	


}
