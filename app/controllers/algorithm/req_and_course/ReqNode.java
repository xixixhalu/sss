package controllers.algorithm.req_and_course;

public class ReqNode {
	public int rId;
	public int complexReqID;
	public ReqNode next;
	public boolean beChosen; //stored in the head node

	public ReqNode(int rId, int complexReqID) { //both first node for course and requirement node
		this.rId = rId;
		this.complexReqID = complexReqID;
	}
	
	public ReqNode(int courseID){
		this.rId = courseID;//head node of the linklist
	}

	public void showNode() {
		//System.out.print("The course is chosen in this requirement: ");
		System.out.println("The course is in complex NO. "+ complexReqID + " and simple NO. "+ rId + " " + beChosen+'\n');
	}
	public void showFirstNode(){
		System.out.print(rId + " " + beChosen +'\n');
	}

}
