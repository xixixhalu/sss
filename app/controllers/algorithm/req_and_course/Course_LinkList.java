package controllers.algorithm.req_and_course;

public class Course_LinkList {
	public CourseNode first; // head node

	public boolean beChosen;
	private int pos; // Node's position

	public Course_LinkList() {
		this.first = null;
	}

	public void addFirstNode(int rName) {
		CourseNode node = new CourseNode(rName);
		node.beChosen = false;// initialize the course has not been selected by
								// any requirement.
		node.next = first;
		first = node;
	}

	public CourseNode deleteFirstNode() {
		CourseNode tempnode = first;
		tempnode.next = first.next;
		return tempnode;
	}

	public void insertNode(int index, int rName) {
		CourseNode node = new CourseNode(rName);
		CourseNode current = first;
		CourseNode previous = first;
		while (pos != index) {
			previous = current;
			current = current.next;
			pos++;
		}

		node.next = current;
		previous.next = node;
		pos = 0;

	}

	public CourseNode deleteByPos(int index) {
		CourseNode current = first;
		CourseNode previous = first;
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

	public void deleteByData(int rName) {
		CourseNode current = first;
		CourseNode previous = first;
		while (current.rName != rName) {
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
		int flag = 1;
		CourseNode current = first;

		while (current != null) {
			if (flag == 1) {
				current.showFirstNode();
				current=current.next;
				++flag;
			} else {
				current.showNode();
				current = current.next;
			}

		}

		System.out.println();// 输出空行，美观

	}

	public CourseNode findByPos(int index) {
		CourseNode current = first;
		if (pos != index) {
			current = current.next;
			pos++;
		}
		return current;
	}

	public CourseNode findByData(int rName) {
		CourseNode current = first;
		while (current.rName != rName) {
			if (current.next == null)
				return null;
			current = current.next;
		}
		return current;
	}

	// Once a course is chosen in requirement link list, there will be a
	// function call
	// this function to update the head node of "course link list" as true.

	public void set_course_be_chosen() {
		this.first.beChosen = true;

	}

	// Every time we need to choose a course in requirement link list, we will
	// first call this
	// function to check is the course have been chosen.
	public boolean checkChosen() {
		if (this.first.beChosen == true) {
			return true; // this course has been chosen.
		}
		return false;

	}

}
