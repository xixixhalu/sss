package controllers.algorithm.req_and_course;

public class SimpleReq {
	public CourseInReq first; // head CourseInReq
	// public boolean satisfied = false;

	private int pos; // CourseInReq's position

	public SimpleReq() {
		this.first = null;
	}

	/**
	 * Check if this simple requirement is null
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public boolean isNull() {
		CourseInReq temp = first;
		if (temp.next.isNull() == false) {
			return false;
		} else {
			return true;
		}
	}

	public SimpleReq(int simpleReqID, String simpleReqName, int needFinish) {
		this.first = null;
		addFirstNode(simpleReqID, simpleReqName, needFinish);
	}
	// cId here is requirement name
	public void addFirstNode(int simpleReqID, String simpleReqName, int needFinish) {
		CourseInReq node = new CourseInReq(simpleReqID, simpleReqName, needFinish);
		node.next = first;
		first = node;
	}

	/**
	 * Delete the head node from simple requirement, which is requirement node i
	 * @return
	 */
	public CourseInReq deleteFirstNode() {
		CourseInReq tempnode = first;
		tempnode.next = first.next;
		return tempnode;
	}

	/**
	 * insert a course into simple requirement
	 * 
	 * @param index
	 *            insert position
	 * @param cID
	 *            course id
	 * 
	 * @return null
	 */
	public void insertNode(int index, int cId) { // cId here is course id
		CourseInReq node = new CourseInReq(cId);
		CourseInReq current = first;
		CourseInReq previous = first;
		while (pos != index) {
			previous = current;
			current = current.next;
			pos++;
		}

		node.next = current;
		previous.next = node;
		pos = 0;

	}

	/**
	 * insert a course into simple requirement
	 * 
	 * @param course
	 *            course node
	 * 
	 * @return null
	 */
	public void insertNode(CourseInReq course) { // cId here is course
		// name

		CourseInReq current = first;

		course.next = current.next;
		current.next = course;

	}

	public CourseInReq deleteByPos(int index) {
		CourseInReq current = first;
		CourseInReq previous = first;
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
	 * delete a course from simple requirement
	 * 
	 * @param cID
	 *            course id
	 * 
	 * @return null
	 */

	public void deleteByData(int cId) {
		CourseInReq current = first;
		CourseInReq previous = first;
		while (current.cId != cId) {
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
	 * Display all course in simple requirement
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayAllNodes() {
		CourseInReq current = first;
		System.out.print("This simple need to " + current.needFinish + " to be finished and status is " + current.statisfied + "\n");

		current.showFirstNode();
		current = current.next;
		while (current != null) {
			current.showNode();
			current = current.next;

		}

		System.out.println();

	}

	public CourseInReq findByPos(int index) {
		CourseInReq current = first;
		if (pos != index) {
			current = current.next;
			pos++;
		}
		return current;
	}

	/**
	 * find a course in simple requirement
	 * 
	 * @param cId
	 *            course id
	 * 
	 * @return null
	 */
	public CourseInReq findByData(int cId) {
		CourseInReq current = first;
		while (current.cId != cId) {
			if (current.next == null)
				return null;
			current = current.next;
		}
		return current;
	}

	/**
	 * select a course in simple requirement
	 * 
	 * @param reqName
	 *            requirement name
	 * @param courseId
	 *            course id
	 * 
	 * @return true selecting successfully
	 * 
	 */
	public boolean chooseCourse(int reqName, int courseId) { 
		if (this.first.cId == reqName) {
			CourseInReq temp = this.findByData(courseId);
			if (temp != null) {
				return true; // find this course in this requirement
			} else {
				return false;
			}
		}
		return false;

	}

	/**
	 * set course status as chosen
	 * 
	 * @param courseId
	 *            course id
	 * 
	 * @return true selecting successfully
	 * 
	 */
	public void set2ChooseCourse(int courseId) {
		CourseInReq temp = this.findByData(courseId);
		if (temp != null) {
			temp.chosen = true; // set the course chosen
		}

	}
}
