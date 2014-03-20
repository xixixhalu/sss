package controllers.algorithm.req_and_course;


public class CourseNode {

	public int rName;
	public CourseNode next;
	public boolean beChosen; //stored in the head node

	public CourseNode(int rName) { //both first node for course and requirement node
		this.rName = rName;
	}
	
	

	public void showNode() {
		System.out.print("The course is chosen in this requirement: ");
		System.out.println(rName + " " + beChosen+'\n');
	}
	public void showFirstNode(){
		System.out.print(rName + " " + beChosen +'\n');
	}

}
