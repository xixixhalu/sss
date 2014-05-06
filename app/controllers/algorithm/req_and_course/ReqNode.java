package controllers.algorithm.req_and_course;

public class ReqNode {
	public int rId;
	public int complexReqID;
	public ReqNode next;
	public boolean beChosen; // stored in the head node

	/**
	 * Initialize a requirement node including simple requirement id and complex
	 * requirement id
	 * 
	 * @param rId
	 *            simple requirement id
	 * @param complexReqID
	 *            complex requirement if
	 * @return null
	 */
	public ReqNode(int rId, int complexReqID) { // both first node for course
												// and requirement node
		this.rId = rId;
		this.complexReqID = complexReqID;
	}

	/**
	 * Initialize a head node
	 * 
	 * @param courseId
	 *            course id
	 * @return null
	 */
	public ReqNode(int courseID) {
		this.rId = courseID;// head node of the linklist
	}

	/**
	 * Show the requirement node
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showNode() {
		// System.out.print("The course is chosen in this requirement: ");
		System.out.println("The course is in complex NO. " + complexReqID + " and simple NO. " + rId + " " + beChosen + '\n');
	}

	/**
	 * Show the head node
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showFirstNode() {
		System.out.print(rId + " " + beChosen + '\n');
	}

}
