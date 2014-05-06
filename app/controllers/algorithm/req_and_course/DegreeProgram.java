package controllers.algorithm.req_and_course;

import java.util.ArrayList;
import java.util.HashMap;

public class DegreeProgram {
	public String degreeName;
	// each simple requirement stores all courses belongs to it
	public ArrayList<SimpleReq> course_list = new ArrayList<SimpleReq>();
	// each course stores all requirements that it belongs to
	public ArrayList<ReqListForCourse> course_list2 = new ArrayList<ReqListForCourse>();

	public HashMap<Integer, ArrayList<CourseInReq>> course = new HashMap<Integer, ArrayList<CourseInReq>>();

	// a set of all complex requirement
	public ArrayList<ComplexReq> allComplexReq = new ArrayList<ComplexReq>();

	/**
	 * Display all courses in all requirement. If a course belongs to several
	 * different simple requirement, it will have many same course node of it
	 * 
	 * @param l
	 *            simple requirement (complex requirement node)
	 * 
	 * @return null
	 */
	public void displayAllCourse() {
		for (Integer key : this.course.keySet()) {
			ArrayList<CourseInReq> theSameCourse = this.course.get(key);
			System.out.print(theSameCourse.size());
			for (CourseInReq eachCourse : theSameCourse) {
				System.out.println(" - " + eachCourse.cId + " - " + eachCourse.chosen);

			}

		}
	}

	// public void addCourse(int courseID){
	//
	// }

	/**
	 * Check if there exist such kind of simple requirement
	 * 
	 * @param simpleReqID
	 *            simple requirement's id
	 * 
	 * @return null
	 */
	public boolean prepareInsertSimple(int simpleReqID) {
		for (int i = 0; i < this.course_list.size(); i++) {
			if (simpleReqID == this.course_list.get(i).first.cId)
				return true;
		}
		return false;
	}

	/**
	 * Check if there exist such kind of course
	 * 
	 * @param courseID
	 *            course's id
	 * 
	 * @return null
	 */
	public boolean prepareInsertCourse(int courseID) {
		return this.course.containsKey(courseID);

	}

	/**
	 * Check if there exist such kind of course link list: course1->req1->re2 if
	 * this course has been added before and belongs to at least one
	 * requirement, when this course is added to another simple requirement, we
	 * firstly find this course1 link list and add the simple requirement into
	 * the course link list.
	 * 
	 * 
	 * @param courseID
	 *            course's id
	 * 
	 * @return null
	 */
	public boolean prepareInsertCourseLinkList(int courseID) {
		for (int i = 0; i < this.course_list2.size(); i++) {
			if (courseID == this.course_list2.get(i).first.rId)
				return true; // this course exists!
		}
		return false;
	}

	public DegreeProgram() {
	}

	/**
	 * Initialize a new degree program.
	 * 
	 * @param degreeName
	 *            degree program's name
	 * 
	 * @return null
	 */
	public DegreeProgram(String degreeName) {
		this.degreeName = degreeName;
	}

	/**
	 * Add a complex requirement into degree program
	 * 
	 * @param ComplexReq
	 *            complex requirement node
	 * 
	 * @return null
	 */
	public void addComplexReq(ComplexReq c) {
		this.allComplexReq.add(c);
	}

	/**
	 * Display all complex requirement in this degree program
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayallComplexReq() {
		for (int i = 0; i < allComplexReq.size(); i++) {
			this.allComplexReq.get(i).displayComplexReq();
		}

	}

	/**
	 * Add a simple requirement link list: req1->course1->course2->course3...
	 * 
	 * @param linklist
	 *            simple requirement: req1->course1->course2->course3...
	 * 
	 * @return null
	 */
	public void addReq2List(SimpleReq linklist) {
		this.course_list.add(linklist);
	}

	/**
	 * Display all simple requirements: req1->course1->course2->course3...
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayReqList() {

		for (int i = 0; i < course_list.size(); i++) {
			course_list.get(i).displayAllNodes();
		}
	}

	/**
	 * Add a course link list: course1->req1->req2...
	 * 
	 * @param course_linklist
	 *            course link list: course1->req1->req2...
	 * 
	 * @return null
	 */
	public void addReq2List(ReqListForCourse course_linklist) {
		this.course_list2.add(course_linklist);
	}

	/**
	 * Display all simple requirements: course->req1->req1->req3...
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayCourseList() {

		for (int i = 0; i < course_list2.size(); i++) {
			course_list2.get(i).displayAllNodes();
		}
	}

	/**
	 * Select a course in a requirement link list. First, we need to find this
	 * course in a specific requirement. Given a requirement and a course, we
	 * need to find them first and than choose them.
	 * 
	 * @param reqName
	 *            requirement name
	 * @param courseID
	 *            course id
	 * 
	 * @return null
	 */
	public boolean checkCourseIn_ReqList(int reqName, int courseID) {
		boolean ifFind = false;
		boolean ifChosen = false;

		int i = 0; // record requirement's index
		int j = 0; // record course's index
		for (; i < course_list.size(); i++) {

			ifFind = this.course_list.get(i).chooseCourse(reqName, courseID);
			if (ifFind) {
				break;// Find this course in this requirement
			}
		}
		if (!ifFind)
			return false; // error detection: if no this course in this
							// requirement

		for (; j < this.course_list2.size(); j++) {
			if (this.course_list2.get(j).first.rId == courseID) {
				ifChosen = this.course_list2.get(j).checkChosen();

				if (ifChosen) {
					return false; // this course has been chosen, so it cannot
									// be chosen again.
				}
				break;
			}

		}
		// selecting classes
		this.course_list.get(i).set2ChooseCourse(courseID); // mark in
															// requirement link
															// list
		this.course_list.get(i).first.needFinish--;
		if (this.course_list.get(i).first.needFinish == 0) {
			this.course_list.get(i).first.statisfied = true;
		}
		for (int k = 0; k < this.course_list.size(); k++) {
			if (k != i) {
				this.course_list.get(k).deleteByData(courseID);
			}
		}
		this.course_list2.get(j).set_course_be_chosen(reqName); // mark in
																// course link
																// list

		return true; // this course exists and has not been chosen.
	}

	// public boolean checker() {
	// for (ComplexReq complex : allComplexReq) {
	// ComplexReq_Node simpleReq = complex.first.next;
	// if (complex.first.relation.equals("or")) {
	// boolean ifBreakInOr = false;
	// while (simpleReq != null) {
	// int remainCourseInSimple = 0;
	// CourseInReq course = simpleReq.SimpleReq.first.next;
	// while (course != null && course.chosen == false) {
	// remainCourseInSimple++;
	// course = course.next;
	// }
	// if (simpleReq.SimpleReq.first.needFinish <= remainCourseInSimple) {
	// ifBreakInOr = true;
	// break;
	// } else {
	// simpleReq = simpleReq.next;
	// }
	// }
	//
	// if (ifBreakInOr == true) {
	// continue;
	// } else {
	// return false;
	// }
	// } else if (complex.first.relation.equals("and")) { // complex has
	// // and relation
	// boolean ifBreakInAnd = false;
	// while (simpleReq != null) {
	// int remainCourseInSimple = 0;
	// CourseInReq course = simpleReq.SimpleReq.first.next;
	// while (course != null & course.chosen == false) {
	// remainCourseInSimple++;
	// course = course.next;
	// }
	//
	// if (simpleReq.SimpleReq.first.needFinish <= remainCourseInSimple) {
	// simpleReq = simpleReq.next;
	// } else {
	// ifBreakInAnd = true;
	// break;
	// }
	// }
	//
	// if (ifBreakInAnd == true) {
	// return false;
	// }
	// }
	// }
	//
	// return true;
	// }

	/**
	 * Check all simple requirements and complex requirements are satisfied
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void CheckAllSimpleAndComplex() { // check all simple and complex
												// satisfaction
		// int flag1=0;
		// int flag2 = 0;
		boolean ifBreak = false;
		for (int i = 0; i < this.course_list.size(); i++) {
			if (this.course_list.get(i).first.needFinish == 0) {
				this.course_list.get(i).first.statisfied = true;
			}
			// System.out.print("in simple "+ flag1++ +" ");
		}

		for (int j = 0; j < this.allComplexReq.size(); j++) {
			ComplexReq complexTemp = this.allComplexReq.get(j);
			ComplexReq_Node simpleReq = complexTemp.first.next;
			if (this.allComplexReq.get(j).first.relation.equals("or")) {

				while (simpleReq != null) {
					if (simpleReq.SimpleReq.first.needFinish == 0) {
						complexTemp.first.satisfied = true;
						break;
					}
					simpleReq = simpleReq.next;
				}
				// System.out.print("in complex or "+ flag2++ +" ");
			} else {
				while (simpleReq != null) {
					if (simpleReq.SimpleReq.first.needFinish != 0) {
						ifBreak = true;
						break;
					}
					simpleReq = simpleReq.next;
				}
				if (ifBreak) {

				} else {
					complexTemp.first.satisfied = true;
				}

				// System.out.print("in complex and"+ flag2++ +" ");
			}
		}
	}
}
