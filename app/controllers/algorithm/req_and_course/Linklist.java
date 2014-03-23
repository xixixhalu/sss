package controllers.algorithm.req_and_course;

public class Linklist {
	public Node first; // head node
	//public boolean satisfied = false;

	private int pos; // Node's position

	public Linklist() {
		this.first = null;
	}
	
	public boolean isNull(){
		Node temp = first;
		if(temp.next.isNull()==false){
			return false;
		}else{
			return true;
		}
	}
	
	public Linklist(int simpleReqID, String simpleReqName, int needFinish){
		this.first = null;
		addFirstNode(simpleReqID, simpleReqName, needFinish);
	}

	public void addFirstNode(int simpleReqID, String simpleReqName, int needFinish) {// cName here is requirement name
		Node node = new Node(simpleReqID, simpleReqName, needFinish);
		node.next = first;
		first = node;
	}

	public Node deleteFirstNode() {
		Node tempnode = first;
		tempnode.next = first.next;
		return tempnode;
	}

	public void insertNode(int index, int cName) { // cName here is course
														// name
		Node node = new Node(cName);
		Node current = first;
		Node previous = first;
		while (pos != index) {
			previous = current;
			current = current.next;
			pos++;
		}

		node.next = current;
		previous.next = node;
		pos = 0;

	}

	public void insertNode(Node course) { // cName here is course
		// name
		
		Node current = first;

		course.next = current.next;
		current.next = course;

	}
	
	public Node deleteByPos(int index) {
		Node current = first;
		Node previous = first;
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

	public void deleteByData(int cName) {
		Node current = first;
		Node previous = first;
		while (current.cName != cName) {
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
		Node current = first;
		System.out.print("This simple need to "+ current.needFinish + " to be finished and status is "+current.statisfied +"\n");
		
		current.showFirstNode();
		current = current.next;
		while (current != null) {
			current.showNode();
			current = current.next;

		}

		System.out.println();

	}

	public Node findByPos(int index) {
		Node current = first;
		if (pos != index) {
			current = current.next;
			pos++;
		}
		return current;
	}

	public Node findByData(int cName) {
		Node current = first;
		while (current.cName != cName) {
			if (current.next == null)
				return null;
			current = current.next;
		}
		return current;
	}

	// choose a course in a requirement link list
	public boolean chooseCourse(int reqName, int courseName) { //传进requirement name，传进course name
		if (this.first.cName == reqName) { //如果传进来的目标req_name和本身的requirement 相同
			Node temp = this.findByData(courseName);
			if(temp != null){
				return true;  // find this course in this requirement
			}else{
				return false;
			}
		}
		return false;

	}
	
	public void set2ChooseCourse(int courseName){
		Node temp = this.findByData(courseName);
		if(temp != null){
			temp.chosen=true; // set the course chosen
		}
		
	}

}
