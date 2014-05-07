package controllers.algorithm.req_and_course;

public class ReqListForCourse {
	public ReqNode first; // head node

	private int pos; // Node's position

	public ReqListForCourse() {
		this.first = null;
	}

	/**
	 * Initialize the course link list: course1->req1->re2
	 * 
	 * @param courseID
	 *            course id
	 * 
	 * @return null
	 */
	public ReqListForCourse(int courseID) {
		addFirstNode(courseID);
	}

	/**
	 * add the first node of course link list
	 * 
	 * @param cId
	 *            course id
	 * 
	 * @return null
	 */
	public void addFirstNode(int cId) {
		ReqNode node = new ReqNode(cId);
		node.beChosen = false;// initialize the course has not been selected by
								// any requirement.
		node.next = first;
		first = node;
	}

	/**
	 * delete the first node of course link list
	 * 
	 * @param null
	 * 
	 * @return the new first node
	 */
	public ReqNode deleteFirstNode() {
		ReqNode tempnode = first;
		tempnode.next = first.next;
		return tempnode;
	}

	/**
	 * insert a simple and complex requirement node into course link list
	 * 
	 * @param SimpleAndCompleReq
	 *            requirement node includes simple requirement and complex
	 *            requirement
	 * @return null
	 */
	public void insertNode(ReqNode SimpleAndCompleReq) {
		ReqNode current = first;
		SimpleAndCompleReq.next = current.next;
		current.next = SimpleAndCompleReq;

	}

	public ReqNode deleteByPos(int index) {
		ReqNode current = first;
		ReqNode previous = first;
		while (pos != index) {
			pos++;
			previous = current;
			current = current.next;

		}
		if (current == first) {
			first = first.next;
		} else {
			pos = 0;
			previous.next = current.next;
		}
		return current;

	}

	/**
	 * Delete a requirement node from course link list, which means that the
	 * course is no longer belonging to this requirement
	 * 
	 * @param rId
	 *            requirement id
	 * @return null
	 */
	public void deleteByData(int rId) {
		ReqNode current = first;
		ReqNode previous = first;
		while (current.rId != rId) {
			if (current.next == null) {
				return;
			}
			previous = current;
			current = current.next;
		}
		if (current == first) {
			first = first.next;
		} else {
			previous.next = current.next;
		}
		return;
	}

	/**
	 * Display all requirements that the course belongs to
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayAllNodes() {
		int flag = 1;
		ReqNode current = first;

		while (current != null) {
			if (flag == 1) {
				current.showFirstNode();
				current = current.next;
				++flag;
			} else {
				current.showNode();
				current = current.next;
			}

		}

		System.out.println();// 输出空行，美观

	}

	public ReqNode findByPos(int index) {
		ReqNode current = first;
		if (pos != index) {
			current = current.next;
			pos++;
		}
		return current;
	}

	/**
	 * Find the specific requirement that the course is belonging to
	 * 
	 * @param rId
	 *            requirement id
	 * @return requirement node
	 */
	public ReqNode findByData(int rId) {
		ReqNode current = first;
		while (current.rId != rId) {
			if (current.next == null)
				return null;
			current = current.next;
		}
		return current;
	}

	/**
	 * Once a course is chosen in requirement link list, there will be a
	 * function call this function to update the head node of "course link list"
	 * as true.
	 * 
	 * @param rId
	 *            requirement id
	 * @return null
	 */
	public void set_course_be_chosen(int reqID) {
		ReqNode temp = this.first;
		temp.beChosen = true;
		while (temp.rId != reqID) {
			temp = temp.next;
		}
		// this.first.beChosen = true;
		temp.beChosen = true;

	}


	/**
	 * Every time we need to choose a course in requirement link list, we will
	 * first call this function to check is the course have been chosen.
	 * 
	 * @param null
	 * 
	 * @return true if this course has been chosen
	 * 
	 */
	public boolean checkChosen() {
		if (this.first.beChosen == true) {
			return true; // this course has been chosen.
		}
		return false;

	}
}
