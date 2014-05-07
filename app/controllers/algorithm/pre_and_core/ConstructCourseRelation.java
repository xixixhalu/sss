package controllers.algorithm.pre_and_core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import controllers.algorithm.req_and_course.CourseInReq;

public class ConstructCourseRelation {
	// store all head nodes;
	public ArrayList<NodeInGraph> headNodeList = new ArrayList<NodeInGraph>();

	public ConstructCourseRelation() {
		// Node firstNode = new Node();
		// this.headNodeList.add(firstNode);
	}

	/**
	 * Given course id, find this course in prerequisite and corequisite DAG.
	 * 
	 * @param courseID
	 *            Id of the course
	 * 
	 */
	public NodeInGraph findHeadCourse(int courseID) {
		int size = this.headNodeList.size();
		for (int i = 0; i < size; i++) {
			if (this.headNodeList.get(i).courseID == courseID)
				return this.headNodeList.get(i); // find this course
		}
		return null;
	}

	// give me number of courses to initialize the a list of headnode
	// public CrossLinkedList(int numOf_Course) { //
	// int temp = numOf_Course;
	// for (int i = 0; i < temp; i++) {
	// String courseTempName = "Course " + i;
	// Node node = new Node(courseTempName);
	// this.headNodeList.add(node);
	// }
	//
	// }

	/**
	 * Given course id, add this course into prerequisite and corequisite DAG.
	 * 
	 * @param courseID
	 *            Id of the course
	 * 
	 */
	public void addCourse(int courseID) {
		NodeInGraph node = new NodeInGraph(courseID);
		this.headNodeList.add(node);
	}

	/**
	 * Display all course, including red nodes in prerequisite and corequisite
	 * DAG
	 * 
	 * @param null
	 * 
	 */
	public void Display_All_Headnode() {
		System.out.print("Show all the head nodes: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) {
			this.headNodeList.get(i).showNode();
		}
	}

	/**
	 * add an arc to between two courses
	 * 
	 * @param tail
	 *            course1's id head course2's id info relation between two
	 *            courses
	 * 
	 */
	public void setCourseRelation(int tail, int head, int info) {
		int size = this.headNodeList.size();
		CourseRelation arc = new CourseRelation(tail, head, info);

		for (int i = 0; i < size; i++) {
			// I am the tail "me--->"
			if (this.headNodeList.get(i).courseID == tail) {
				arc.tlink = this.headNodeList.get(i).firstOut;
				this.headNodeList.get(i).firstOut = arc; // 把新的一门课加入到了图中，以arc表示
			}
			// I am the head "--->me"
			if (this.headNodeList.get(i).courseID == head) {
				arc.hlink = this.headNodeList.get(i).firstIn;
				this.headNodeList.get(i).firstIn = arc;
			}
		}

	}

	/**
	 * add all course into DAG
	 * 
	 * @param list
	 *            add all courses in requirement into the DAG
	 * 
	 */
	public void addAllCourseInGraph(HashMap<Integer, ArrayList<CourseInReq>> list) {

		for (Entry<Integer, ArrayList<CourseInReq>> entry : list.entrySet()) {
			int courseId = entry.getKey();
			addCourse(courseId);
		}

	}

	/**
	 * remove courses that have no prerequisite and corequisite relation
	 * 
	 * @param null
	 * 
	 */
	public void removeAloneNode() {

		Iterator<NodeInGraph> iter = this.headNodeList.iterator();

		while (iter.hasNext()) {
			NodeInGraph course = iter.next();
			if (course.firstOut == null && course.firstIn == null)
				iter.remove();
		}

	}

	/**
	 * Display all course relation
	 * 
	 * @param null
	 * 
	 */
	public void displayCrossLinkedList() {
		System.out.print("Show Cross LinkedList from tail to head: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) { // show head
																// linklist
			CourseRelation arc = this.headNodeList.get(i).firstOut;
			while (arc != null) {
				arc.showArcFromTail();
				arc = arc.tlink;
			}
		}

		System.out.print("Show Cross LinkedList from head to tail: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) {
			CourseRelation arc = this.headNodeList.get(i).firstIn;
			while (arc != null) {
				arc.showArcFromHead();
				arc = arc.hlink;
			}
		}

		return;
	}

	/**
	 * Get all corequisite relation in DAG
	 * 
	 * @param null
	 * 
	 */
	public HashMap<Integer, Integer> TraverCore() {
		HashMap<Integer, Integer> core = new HashMap<Integer, Integer>();
		for (int i = 0; i < this.headNodeList.size(); i++) {
			CourseRelation arc = this.headNodeList.get(i).firstIn;
			while (arc != null) {
				if (arc.info == 2) {
					if (arc.tailCourseID <= 0) {
						for (int j = 0; j < this.headNodeList.size(); j++) {
							if (this.headNodeList.get(j).courseID == arc.tailCourseID) {
								CourseRelation arc2 = this.headNodeList.get(j).firstIn;
								while (arc2 != null) {
									core.put(arc2.tailCourseID, arc2.headCourseID);
									arc2 = arc2.hlink;
								}
							}
						}
						core.put(arc.tailCourseID, arc.headCourseID);
					} else {
						core.put(arc.tailCourseID, arc.headCourseID); // A==>B

					}

				}

				arc = arc.hlink;
			}
		}
		return core;
	}

	/**
	 * Display all course relation
	 * 
	 * @param null
	 * 
	 */
	public void DisplayCore(Multimap<Integer, Integer> core) {
		for (Integer key : core.keySet()) {
			System.out.println(key + " " + core.get(key));
		}
	}
}
