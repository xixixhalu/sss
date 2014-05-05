package controllers.algorithm.req_and_course;

public class SimpleReq {
	public CourseInReq first; // head CourseInReq
	//public boolean satisfied = false;

	private int pos; // CourseInReq's position

	public SimpleReq() {
		this.first = null;
	}
	
	public boolean isNull(){
		CourseInReq temp = first;
		if(temp.next.isNull()==false){
			return false;
		}else{
			return true;
		}
	}
	
	public SimpleReq(int simpleReqID, String simpleReqName, int needFinish){
		this.first = null;
		addFirstNode(simpleReqID, simpleReqName, needFinish);
	}

	public void addFirstNode(int simpleReqID, String simpleReqName, int needFinish) {// cId here is requirement name
		CourseInReq node = new CourseInReq(simpleReqID, simpleReqName, needFinish);
		node.next = first;
		first = node;
	}

	public CourseInReq deleteFirstNode() {
		CourseInReq tempnode = first;
		tempnode.next = first.next;
		return tempnode;
	}

	public void insertNode(int index, int cId) { // cId here is course
														// name
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

	public void displayAllNodes() {
		CourseInReq current = first;
		System.out.print("This simple need to "+ current.needFinish + " to be finished and status is "+current.statisfied +"\n");
		
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

	public CourseInReq findByData(int cId) {
		CourseInReq current = first;
		while (current.cId != cId) {
			if (current.next == null)
				return null;
			current = current.next;
		}
		return current;
	}

	// choose a course in a requirement link list
	public boolean chooseCourse(int reqName, int courseName) { //传进requirement name，传进course name
		if (this.first.cId == reqName) { //如果传进来的目标req_name和本身的requirement 相同
			CourseInReq temp = this.findByData(courseName);
			if(temp != null){
				return true;  // find this course in this requirement
			}else{
				return false;
			}
		}
		return false;

	}
	
	public void set2ChooseCourse(int courseName){
		CourseInReq temp = this.findByData(courseName);
		if(temp != null){
			temp.chosen=true; // set the course chosen
		}
		
	}
}
