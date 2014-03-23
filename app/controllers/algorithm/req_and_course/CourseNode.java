package controllers.algorithm.req_and_course;


public class CourseNode {

	public int rName;
	public int complexReqID;
	public CourseNode next;
	public boolean beChosen; //stored in the head node

	public CourseNode(int rName, int complexReqID) { //both first node for course and requirement node
		this.rName = rName;
		this.complexReqID = complexReqID;
	}
	
	public CourseNode(int courseID){
		this.rName = courseID;//head node of the linklist
	}

	public void showNode() {
		System.out.print("The course is chosen in this requirement: ");
		System.out.println("The course is in complex NO. "+ complexReqID + " and simple NO. "+ rName + " " + beChosen+'\n');
	}
	public void showFirstNode(){
		System.out.print(rName + " " + beChosen +'\n');
	}

}
