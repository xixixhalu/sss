package models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import play.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import controllers.algorithm.pre_and_core.ArcBox;
import controllers.algorithm.pre_and_core.Cal_Depth;
import controllers.algorithm.pre_and_core.CrossLinkedList;
import controllers.algorithm.pre_and_core.NodeInGraph;
import controllers.algorithm.req_and_course.ComplexReq;
import controllers.algorithm.req_and_course.ComplexReq_Node;
import controllers.algorithm.req_and_course.CourseNode;
import controllers.algorithm.req_and_course.Course_LinkList;
import controllers.algorithm.req_and_course.Linklist;
import controllers.algorithm.req_and_course.Node;
import controllers.algorithm.req_and_course.TestLinkList;

public class StudyPlan {

	public int redNode = 0;
	public TestLinkList degreeProgram;
	CrossLinkedList allCross_relation;
	Cal_Depth calSemester;
	public ArrayList<Integer> courseBin; // Course Info after auto fill course
	public Multimap<Integer, Integer> corerequsiteList = ArrayListMultimap.create();
	public HashMap<Integer, ArrayList<Integer>> studyplanResult; // Study Plan
																	// Info
																	// after
																	// auto fill
																	// semester

	public void GetCourseMaxDepthInGraph(Cal_Depth calSemester) {
		calSemester.allCross_relation_example = allCross_relation;
		calSemester.BFS_Max();
		// calSemester.BFS_Min();
//		calSemester.Display_All_Headnode_Max();
		// calSemester.Display_All_Headnode_Min();
		return;
	}

	@SuppressWarnings("unchecked")
	public void GetAllCoureReq() {
		HashMap<Integer, Integer> tempCoreList = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> tempCoreList2 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> tempCoreList3 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> tempCoreList4 = new HashMap<Integer, Integer>();
		Multimap<Integer, Integer> tempCoreList5 = ArrayListMultimap.create();
		tempCoreList = allCross_relation.TraverCore();
		// allCross_relation.DisplayCore(tempCoreList);
		tempCoreList2 = (HashMap<Integer, Integer>) tempCoreList.clone();
		tempCoreList3 = (HashMap<Integer, Integer>) tempCoreList.clone();
		for (Integer key1 : tempCoreList2.keySet()) {
			if (tempCoreList2.get(key1) < 0) {// get value. if the value is less
												// than 0.
				for (Integer key2 : tempCoreList3.keySet()) {
					if (key2 == tempCoreList2.get(key1)) {
						tempCoreList4.put(key1, tempCoreList3.get(key2));
					}
				}
			} else if (key1 > 0 && tempCoreList2.get(key1) > 0) {
				tempCoreList4.put(key1, tempCoreList2.get(key1));
			}
		}
		// tempCoreList5 = (Multimap<Integer, Integer>) tempCoreList4.clone();
		for (Integer kk : tempCoreList4.keySet()) {
			tempCoreList5.put(kk, tempCoreList4.get(kk));

		}
		for (Integer kk : tempCoreList4.keySet()) {
			tempCoreList5.put(tempCoreList4.get(kk), kk);

		}
		System.out.println("**************");
		//allCross_relation.DisplayCore(tempCoreList5);
		this.corerequsiteList = tempCoreList5;
		return;
	}

	public void CreateDegreeProgram(Integer id) {
		// boolean chooeseSuccess =false;//是否选课成功
		Degree degree = Degree.findById(id);
		degreeProgram = new TestLinkList(degree.getTitle()); // add new degree
		List<String> complexIds = degree.getReq_ids(); // get Requirement ids
		allCross_relation = new CrossLinkedList();
		for (String complexId : complexIds) {
			try {
				Requirement req = Requirement.findById(Integer
						.valueOf(complexId)); // get
												// Requirement
				JSONArray srReqs = new JSONArray(req.getSr_ids());
				ComplexReq complexReq = null;
				if (srReqs.length() < 2)
					complexReq = new ComplexReq(Integer.valueOf(req.getId()),
							req.getTitle(), "or");
				else
					complexReq = new ComplexReq(Integer.valueOf(req.getId()),
							req.getTitle(),
							(String) ((JSONObject) srReqs.get(1))
									.get("relation"));
				JSONObject srReqObject = null;
				for (int i = 0; i < srReqs.length(); i++) {
					srReqObject = (JSONObject) srReqs.get(i);
					int srId = srReqObject.getInt("id"); // get Simple
															// Requirment
					Sr sr = Sr.findById(new Integer(srId));
					int cgId = Integer.valueOf(sr.getCg_id());
					int reqNum = Integer.valueOf(sr.getRequired_num());
					Linklist simpleReq = find_or_create_simpleReq(srId,
							sr.getTitle(), reqNum); // initiate
					// simple
					// requirement
					Cg cg = Cg.findById(new Integer(cgId));
					List<String> courseIds = cg.getCourse_ids();
					for (String courseId : courseIds) {
						addCourse(simpleReq, Integer.valueOf(courseId)); // add
																			// course
						add2Course_List2(complexReq, simpleReq,
								Integer.valueOf(courseId));
					}

					complexReq.insertSimple(simpleReq);
					degreeProgram.course_list.add(simpleReq);
				}

				degreeProgram.addComplexReq(complexReq);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		createCrossLinkedList(degreeProgram.course);
		GetAllCoureReq();
		// degreeProgram.displayAllCourse();
		// allCross_relation.Display_All_Headnode();

		// degreeProgram.displayallComplexReq();
		// degreeProgram.displayCourseList();
		// degreeProgram.displayAllCourse();

		// TestLinkList degreeProgram =new TestLinkList("degreeName1"); //need
		// degreeName input
		// ComplexReq complexReq1 = new ComplexReq(1,"complexReq1","or");
		//
		//
		// Linklist simpleReq1 = new Linklist(1,"simpleReq1",15);
		//
		// //req1 -> course1->course2
		// addCourse(degreeProgram, simpleReq1, 100);
		//
		// //req1 -> course1->course2
		// addCourse(degreeProgram, simpleReq1, 200);
		// addCourse(degreeProgram, simpleReq1, 300);
		// addCourse(degreeProgram, simpleReq1, 400);
		// addCourse(degreeProgram, simpleReq1, 500);
		// addCourse(degreeProgram, simpleReq1, 600);
		//
		// complexReq1.insertSimple(simpleReq1);
		// degreeProgram.addComplexReq(complexReq1);
		// degreeProgram.course_list.add(simpleReq1);
		//
		// degreeProgram.displayallComplexReq();
		//
		// //course1 -> req1 ->re2
		// add2Course_List2(degreeProgram, simpleReq1,100);
		// add2Course_List2(degreeProgram, simpleReq1,200);
		// add2Course_List2(degreeProgram, simpleReq1,300);
		// add2Course_List2(degreeProgram, simpleReq1,400);
		// add2Course_List2(degreeProgram, simpleReq1,500);
		// add2Course_List2(degreeProgram, simpleReq1,600);
		//
		// degreeProgram.displayCourseList();

		// mark student's chosen course
		// boolean chooeseSuccess = degreeProgram.checkCourseIn_ReqList(10,108);
		// if(chooeseSuccess){
		// degreeProgram.displayallComplexReq();
		// degreeProgram.displayCourseList();
		// }

		// --------The first test case: one complex one simple
		// 74,84,85,86,87,88,89,90,92,93,94,95,96,97,98
		// CheckInSelectedCourse(degreeProgram, 17, 7, 74);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 84);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 85);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 86);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 87);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 88);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 89);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 90);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 92);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 93);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 94);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 95);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 96);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 97);
		// CheckInSelectedCourse(degreeProgram, 17, 7, 98);

		// --------The second test case: one complex three simple or relation
		// correct
		// CheckInSelectedCourse(degreeProgram, 20, 10, 106);
		// CheckInSelectedCourse(degreeProgram, 20, 10, 107);
		// CheckInSelectedCourse(degreeProgram, 20, 10, 108);
		//
		// --------The third test case: one complex three simple and relation
		// 1 of 2 in simple, 2 of 2 simple

		// CheckInSelectedCourse(degreeProgram, 22, 17, 189);
		// CheckInSelectedCourse(degreeProgram, 22, 17, 133);

		// CheckInSelectedCourse(degreeProgram, 22, 18, 190);
		// CheckInSelectedCourse(degreeProgram, 22, 17, 189);
		// CheckInSelectedCourse(degreeProgram, 22, 17, 133);
		// CheckInSelectedCourse(degreeProgram, 22, 16, 120);
		// CheckInSelectedCourse(degreeProgram, 22, 16, 118);

		// CheckInSelectedCourse(degreeProgram, 23, 19, 164);
		// CheckInSelectedCourse(degreeProgram, 23, 19, 102);

		// degreeProgram.CheckAllSimpleAndComplex();

		// System.out.print("Before AutoFill:");
		// degreeProgram.displayallComplexReq();
		// degreeProgram.displayCourseList();
		// System.out.print("\n");

		// System.out.print("After AutoFill: \n");

//		degreeProgram.displayallComplexReq();
		// degreeProgram.displayCourseList();

//		allCross_relation.Display_All_Headnode();
//		allCross_relation.displayCrossLinkedList();


		play.Logger.info("================================================");
	}

	public void addCourse(Linklist simpleReq1, int courseID) {
		// System.out.println(ifCourseExist);

		// System.out.println("OK");

		Node newNode = new Node(courseID);

		if (degreeProgram.course.containsKey(courseID)) {
			degreeProgram.course.get(courseID).add(newNode);
		} else {
			ArrayList<Node> clist = new ArrayList<Node>();
			clist.add(newNode);
			degreeProgram.course.put(courseID, clist);
		}

		simpleReq1.insertNode(newNode);

		return;
	}

	public void createCrossLinkedList(
			HashMap<Integer, ArrayList<Node>> course_list) {
		/**
		 * @author tongrui function: construct the crosslist
		 */
		allCross_relation.addAllCourseInGraph(course_list);

		for (Entry<Integer, ArrayList<Node>> entry : course_list.entrySet()) {
			int courseID = entry.getKey();
			Course course = Course.findById(entry.getKey());

			String prereq = course.getPrereq(2);
			String coreq = course.getCoreq(2);

			if (!prereq.trim().equals("-")) {
				String[] prelist = prereq.split(" ");

				if (prelist.length - 2 == 1) {
					allCross_relation.setArcBox(Integer.valueOf(prelist[2]),
							courseID, 1);
				} else if (prelist.length - 2 == 3) {
					if (prelist[3].equals(",")) {
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[2]), courseID, 1);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[4]), courseID, 1);
					} else if (prelist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[2]), redNode, 3);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 1);
					}
				} else if (prelist.length - 2 == 5) {
					if (prelist[3].equals(",")) {
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[2]), courseID, 1);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[4]), courseID, 1);

						if (prelist[5].equals("or")) {
							// issue
						} else if (prelist[5].equals(",")) {
							allCross_relation.setArcBox(
									Integer.valueOf(prelist[6]), courseID, 1);
						}
					} else if (prelist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[2]), redNode, 3);
						allCross_relation.setArcBox(
								Integer.valueOf(prelist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 1);
						if (prelist[5].equals("or")) {
							allCross_relation.setArcBox(
									Integer.valueOf(prelist[6]), redNode, 3);
							allCross_relation.setArcBox(redNode, courseID, 3);
						} else if (prelist[5].equals(",")) {
							allCross_relation.setArcBox(
									Integer.valueOf(prelist[6]), courseID, 1);
						}
					}
				}
			}

			if (!coreq.trim().equals("-")) {
				String[] colist = coreq.split(" ");

				if (colist.length - 2 == 1) {
					allCross_relation.setArcBox(Integer.valueOf(colist[2]),
							courseID, 2);
				} else if (colist.length - 2 == 3) {
					if (colist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(colist[2]),
								courseID, 2);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]),
								courseID, 2);
					} else if (colist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(colist[2]),
								redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]),
								redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 2);
					}
				} else if (colist.length - 2 == 5) {
					if (colist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(colist[2]),
								courseID, 2);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]),
								courseID, 2);

						if (colist[5].equals("or")) {
							// issue
						} else if (colist[5].equals(",")) {
							allCross_relation.setArcBox(
									Integer.valueOf(colist[6]), courseID, 2);
						}
					} else if (colist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(colist[2]),
								redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]),
								redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 2);

						if (colist[5].equals("or")) {
							allCross_relation.setArcBox(
									Integer.valueOf(colist[6]), redNode, 3);
							allCross_relation.setArcBox(redNode, courseID, 3);
						} else if (colist[5].equals(",")) {
							allCross_relation.setArcBox(
									Integer.valueOf(colist[6]), courseID, 2);
						}
					}
				}
			}
		}

		// allCross_relation.displayCrossLinkedList();
		allCross_relation.removeAloneNode();
	}

	public void add2Course_List2(ComplexReq complexReq, Linklist simpleReq1,
			int courseID) {
		boolean ifCourseExist = degreeProgram
				.prepareInsertCourseLinkList(courseID);
		if (ifCourseExist) {
			int simpleReqName = simpleReq1.first.cName;
			int complexReqID = complexReq.first.ComplexReq_Id;
			CourseNode ReqInfo = new CourseNode(simpleReqName, complexReqID);
			for (int i = 0; i < degreeProgram.course_list2.size(); i++) {
				if (courseID == degreeProgram.course_list2.get(i).first.rName) {
					degreeProgram.course_list2.get(i).insertNode(ReqInfo);
				}

			}
		} else {
			Course_LinkList courseNode = new Course_LinkList(courseID);
			int simpleReqName = simpleReq1.first.cName;
			int complexReqID = complexReq.first.ComplexReq_Id;
			CourseNode ReqInfo = new CourseNode(simpleReqName, complexReqID);
			courseNode.insertNode(ReqInfo);
			degreeProgram.addReq2List(courseNode);
		}

	}

	public Linklist find_or_create_simpleReq(int srId, String srTitle,
			int reqNum) {
		boolean simpleReqExist = degreeProgram.prepareInsertSimple(srId);
		if (simpleReqExist) {
			int i = 0;
			for (; i < degreeProgram.course_list.size(); i++) {
				if (srId == degreeProgram.course_list.get(i).first.cName)
					break;
			}
			return degreeProgram.course_list.get(i);
		} else {
			Linklist simpleReq = new Linklist(srId, srTitle, reqNum);
			return simpleReq;
		}
	}

	public void CheckInSelectedCourse(int complexID, int simpleID, int courseID) {
		for (int i = 0; i < degreeProgram.allComplexReq.size(); i++) {
			if (degreeProgram.allComplexReq.get(i).first.ComplexReq_Id == complexID) {
				ComplexReq complexReq = degreeProgram.allComplexReq.get(i);
				if (!complexReq.isNull()) { // if this complex has simples in it
					for (int j = 0; j < degreeProgram.course_list.size(); j++) {
						if (degreeProgram.course_list.get(j).first.cName == simpleID) {
							degreeProgram.checkCourseIn_ReqList(simpleID,
									courseID);
							for (int k = 0; k < allCross_relation.headNodeList
									.size(); k++) {
								if (allCross_relation.headNodeList.get(k).courseID == courseID) {
									allCross_relation.headNodeList.get(k).assign = true;
									break;
								}
							}
						}
					}
				}

			}
		}
	}
	
	

	public ArrayList<Integer> AutoFillCourseBin() { // change the arguments and
													// recursively call this
													// function
		calSemester = new Cal_Depth();
		calSemester.allCross_relation_example = allCross_relation;
		ArrayList<Integer> courseBinResult = new ArrayList<Integer>();
		GetCourseMaxDepthInGraph(calSemester); // mark the min and max in
												// nodeInGraph
		for (int i = 0; i < calSemester.allCross_relation_example.headNodeList
				.size(); i++) { // in
								// node
								// graph
								// find
								// all
								// value
			NodeInGraph courseInGraph = calSemester.allCross_relation_example.headNodeList
					.get(i);
			for (Integer key : degreeProgram.course.keySet()) {
				if (degreeProgram.course.get(key).get(0).cName == courseInGraph.courseID) {// update
																							// course
																							// both
																							// in
																							// requirement
																							// and
																							// graph
					ArrayList<Node> sameCourseList = degreeProgram.course
							.get(key);
					for (Node course : sameCourseList) {
						course.maxDepth = courseInGraph.maxDepth;
						// course.minDepth = courseInGraph.minDepth;
					}
				}

			}
		}

		for (int i = 0; i < degreeProgram.allComplexReq.size(); i++) {
			ComplexReq complexReq = degreeProgram.allComplexReq.get(i);
			if (complexReq.first.satisfied == true) {
				continue;// check next complex requirement
			} else {
				ComplexReq_Node simpleReq = complexReq.first.next;// find all
																	// unsatisfied
																	// complex
																	// requirement,
																	// fetch its
																	// simple
																	// requirement
				while (simpleReq != null) {// as long as the simple is not null
					if (simpleReq.SimpleReq.first.statisfied == true) {
						// do nothing and check next simple requirement
					} else {
						// add a function to sort as semester as ascending
						Node course = simpleReq.SimpleReq.first.next;// each
																		// course
																		// in
																		// simple
																		// requirement
						// HashMap<Integer, Node> courseHash = new
						// HashMap<Integer, Node>();
						HashMap<Integer, ArrayList<Node>> courseHash = new HashMap<Integer, ArrayList<Node>>();

						SortedSet<Integer> courseOrderByMaxDepth = new TreeSet<Integer>();// each
																							// simple
																							// requirement
																							// has
																							// a
																							// sortedset
																							// stores
																							// courses
																							// maxDepth

						while (course != null) {
							if (!courseHash.containsKey(course.maxDepth)) {
								courseOrderByMaxDepth.add(course.maxDepth);
								ArrayList<Node> alist = new ArrayList<Node>();
								alist.add(course);
								courseHash.put(course.maxDepth, alist);
							} else {
								ArrayList<Node> alist = courseHash
										.get(course.maxDepth);
								alist.add(course);
								courseHash.put(course.maxDepth, alist);
							}

							course = course.next;
						}
						int maxMaxDepth = courseOrderByMaxDepth.last();

						if (maxMaxDepth == 0) {
							// all course in this simple requirement has no pre
							// and core relation
							FillNonePreCoreReq(complexReq, simpleReq.SimpleReq,
									courseBinResult);

						} else {
							// the course in this simple requirement has pre and
							// core relation
							// test use:
							// simpleReq.SimpleReq.first.statisfied=true;
							// simpleReq.SimpleReq.first.needFinish--;

							for (Entry<Integer, ArrayList<Node>> entry : courseHash
									.entrySet()) {
								int key = entry.getKey();
								ArrayList<Node> courseNodeList = entry
										.getValue();
//								System.out.print("The maxDepth is " + key
//										+ " include these courses: \n");
								for (Node courseNode : courseNodeList) {
									if (complexReq.first.satisfied == false) {
//										System.out.print("Output course: "
//												+ courseNode.cName + " ");
										ArrayList<Integer> courseList = new ArrayList<Integer>(); // store
																									// its
																									// pre
																									// an
																									// core
										backtrackCourse(courseNode.cName,
												courseList);
										RemoveTheLastCourseItSelf(courseList);
										RemoveTheRedInRelatedCourseList(courseList);
										for (Integer needToBeSelectedCourse : courseList) {
											// if(degreeProgram.course.containsKey(needToBeSelectedCourse)){
											for (Linklist assignSimple : degreeProgram.course_list) {
												if (degreeProgram
														.checkCourseIn_ReqList(
																assignSimple.first.cName,
																courseNode.cName)) {
													degreeProgram
															.CheckAllSimpleAndComplex();
													break;
												}
											}
											boolean f = false;
											for (int ii = 0; ii < courseBinResult
													.size(); ii++) {
												if (needToBeSelectedCourse
														.compareTo(courseBinResult
																.get(ii)) == 0) {// *****************
													f = true;
												}
											}
											if (!f)
												courseBinResult
														.add(needToBeSelectedCourse);
											degreeProgram.CheckAllSimpleAndComplex();
										}
										degreeProgram
												.checkCourseIn_ReqList(
														simpleReq.SimpleReq.first.cName,
														courseNode.cName);
										degreeProgram
												.CheckAllSimpleAndComplex();
										/*******/
										boolean f = false;
										for (int ii = 0; ii < courseBinResult
												.size(); ii++) {
											if (Integer.valueOf(
													courseNode.cName)
													.compareTo(
															courseBinResult
																	.get(ii)) == 0) {// *****************
												f = true;
											}
										}
										if (!f)
											courseBinResult.add(courseNode.cName);
									}
								}

							}

						}

					}
					// update/check complex requirement
					degreeProgram.CheckAllSimpleAndComplex();
					simpleReq = simpleReq.next;
				}
			}
		}
//		System.out.print("*******");
//
//		for (Integer id : courseBinResult) {
//
//			Logger.info(id + " ");
//			System.out.print(id + " ");
//		}

		for (Integer id : degreeProgram.course.keySet()) {
			ArrayList<Node> temp = degreeProgram.course.get(id);
			Node tempNode = temp.get(0);
			if (tempNode.chosen == true
					&& !courseBinResult.contains(tempNode.cName)) {
				courseBinResult.add(tempNode.cName);
			}
		}

//		System.out.print("\n");
		courseBin = courseBinResult;
		return courseBinResult;
	}
	
	public void changeCourseStatus(){
		for(Integer key : degreeProgram.course.keySet()){
			ArrayList<Node> eachCourseInstance = degreeProgram.course.get(key);
			for(Node oneInstance : eachCourseInstance){
				if(oneInstance.chosen==true){
					if(eachCourseInstance.get(0).chosen==false){
						eachCourseInstance.get(0).chosen=true;
					}
				}
			}
			
		}
	
		return;
	}


	public void setAssignSemester(String semesterData, HashMap<Integer, ArrayList<Node>> courseInHash, HashMap<Integer, ArrayList<Integer>> result) {

		/**
		 * @author tongrui assign semester to back end
		 */
		if (semesterData.equals("[]"))
			return;

		try {
			JSONArray semesterArray = new JSONArray(semesterData);
			for (int i = 0; i < semesterArray.length(); i++) {
				JSONObject semester = (JSONObject) semesterArray.get(i);
				int semesterNum = semester.getInt("num");
				JSONArray courses = (JSONArray) semester.get("courses");

				result.put(semesterNum, new ArrayList<Integer>());
				for (int j = 0; j < courses.length(); j++) {
					int courseID = courses.getInt(j);
					result.get(semesterNum).add(courseID);

					ArrayList<Node> nodes = courseInHash.get(courseID);
					for (Node node : nodes) {
						node.semester = semesterNum;
					}
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer, ArrayList<Integer>> AutoAssignSemester(int numOfSemester, String semesterData) {

		HashMap<Integer, ArrayList<Node>> courseInHash = degreeProgram.course;
		//degreeProgram.displayAllCourse();
		// courses in each level
		HashMap<Integer, ArrayList<Node>> semesterBin = new HashMap<Integer, ArrayList<Node>>();
		// semester=>[courseID,courseID,courseID]
		HashMap<Integer, ArrayList<Integer>> result = new HashMap<Integer, ArrayList<Integer>>();

		// set student-specific semesters
		//setAssignSemester(semesterData, courseInHash, result);

		// initiate the courses, assign the courses with the semester which the
		// student has choose.
		JSONArray semesterJsonArray;
		HashMap<Integer, Integer> courseCountInSemester = new HashMap<Integer, Integer>();
		try {
			semesterJsonArray = new JSONArray(semesterData);
			for(int i=0;i<semesterJsonArray.length();i++){
				JSONObject semester = (JSONObject) semesterJsonArray.get(i);
				JSONArray courses = (JSONArray) semester.get("courses");
				//Bowen: recored course count in per semester
				courseCountInSemester.put(semester.getInt("num"), courses.length());
				for(int j=0;j<courses.length();j++){
					int id= courses.getInt(j);
					courseInHash.get(id).get(0).semester=semester.getInt("num");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int max = 0;
		for (int i = 0; i < courseBin.size(); i++) {
			for (Integer key : courseInHash.keySet()) {
				if (courseBin.get(i).compareTo(key) == 0) {
					Node temp = courseInHash.get(key).get(0);
					if (max <= temp.maxDepth)
						max = temp.maxDepth;
					// semesterBin.put(temp.maxDepth,temp);

					if (semesterBin.containsKey(temp.maxDepth)) { // if this
																	// semester
																	// exist
						ArrayList<Node> alist = semesterBin.get(temp.maxDepth);
						alist.add(temp);
						semesterBin.put(temp.maxDepth, alist);
					} else {
						ArrayList<Node> alist = new ArrayList<Node>();
						alist.add(temp);
						semesterBin.put(temp.maxDepth, alist);
					}
				}
			}
		}
		int courseInSemester = courseBin.size() / numOfSemester + 1;

		// record the number of courses already in the semester
		Map<Integer, Integer> numOfCourseInSemester = new HashMap<Integer, Integer>();
		int level = max;
		int curSemester = numOfSemester;
		int hhhhh = -1;
		while (level >= 0 && curSemester > 0) {

			ArrayList<Node> courseInSameLvl = semesterBin.get(level);// get the
																		// courses
																		// in
																		// this
																		// level
																		// of
																		// depth
			int num = courseInSemester;// record how many courses remain in one
										// semester
			num -= courseCountInSemester.get(curSemester);
			int lvlRemainCourse = courseInSameLvl.size();// record how many
															// courses remain in
															// this level of
															// depth
			// mark the course with semester
			for (int i = 0; i < courseInSameLvl.size() && num > 0; i++) {
				// check the if the course has already been assigned with a
				// semester.
				if (courseInSameLvl.get(i).semester != -1) {
					// -1 is the default number of semester,
					// if course has been assigned with a semester, then do
					// nothing continue the loop
					lvlRemainCourse--;
					continue;
				}
				// if the course has not been assigned
				int tempt = courseInSameLvl.get(i).cName;
				if (corerequsiteList.containsKey(Integer.valueOf(tempt))) {
					courseInSameLvl.get(i).semester = curSemester;// assign the
																	// current
																	// semester
																	// to the
																	// course
					num--;
					num = BacktrackCore(courseInHash, courseInSameLvl.get(i).cName, curSemester, num);
					// ArrayList<Integer> coreqs=(ArrayList<Integer>)
					// corerequsiteList.get(courseInSameLvl.get(i).cName);
					// for(Integer lalala:coreqs){
					// for(int n = 0 ;n<courseBin.size();n++){
					// if(courseInHash.get(lalala).get(0).cName==courseBin.get(n)){
					// courseInHash.get(lalala).get(0).semester=curSemester;
					// num--;
					// //迭代查找所有的corequisite.
					// }
					//
					// }
					//
					// }
				} else {
					courseInSameLvl.get(i).semester = curSemester;
					lvlRemainCourse--;
					num--;
				}

			}
			numOfCourseInSemester.put(curSemester, num);
			// hhhhh = lvlRemainCourse;
			if (lvlRemainCourse == 0) {
				level--;
			}
			if (num < 6)
				curSemester--;
		}

		for (int i = 1; i <= numOfCourseInSemester.size(); i++) {
			int remainCourse = numOfCourseInSemester.get(i);
			if (numOfCourseInSemester.get(i) > 0) {
				ArrayList<Node> courseWithoutReq = semesterBin.get(0);

				for (int j = 0; j < courseWithoutReq.size() && remainCourse > 0; j++) {
					if (courseWithoutReq.get(j).semester == -1) {
						courseWithoutReq.get(j).semester = i;
						remainCourse--;
					}
				}
			}
			numOfCourseInSemester.put(i, remainCourse);
		}

		// -------------------------------

//		for (Integer key : numOfCourseInSemester.keySet()) {
//			System.out.println("*************In " + key + " semester, it has "
//					+ numOfCourseInSemester.get(key) + " size remain ");
//		}

//		for (Integer key : semesterBin.keySet()) {
//			System.out.print("The semester " + key + " is \n");
//			ArrayList<Node> tempNodeList = semesterBin.get(key);
//			for (Node course : tempNodeList) {
//				System.out.print(course.cName + "\n");
//			}
//		}

		for (Integer key : semesterBin.keySet()) {
			ArrayList<Node> temp = semesterBin.get(key);
			for (Node course : temp) {// get each course
				if (result.containsKey(course.semester)) {
					ArrayList<Integer> thisSemester = result
							.get(course.semester);
					thisSemester.add(Integer.valueOf(course.cName));
					result.put(course.semester, thisSemester);
				} else {
					ArrayList<Integer> thisNewSemester = new ArrayList<Integer>();
					thisNewSemester.add(Integer.valueOf(course.cName));
					result.put(course.semester, thisNewSemester);
				}
			}
		}

		for (Integer key : result.keySet()) {
			ArrayList<Integer> courseInSameSemester = result.get(key);
			System.out.print("In semester " + key + ", there are ");
			for (Integer courseId : courseInSameSemester) {
				System.out.print(courseId + " ");
			}
			System.out.print("\n");
		}
		studyplanResult = result;
		return result;
	}

	public int BacktrackCore(HashMap<Integer, ArrayList<Node>> courseInHash, Integer courseID, int currentSemester, int num) {
		// course.semester = currentSemester;// assign the current semester to
		// the course
		// num--;
		Integer[] coreqs = corerequsiteList.get(courseID).toArray(new Integer[0]);
		for (Integer lalala : coreqs) {
			if (courseInHash.get(lalala).get(0).semester != -1)
				continue;
			for (int n = 0; n < courseBin.size(); n++) {
				if (lalala.equals(courseBin.get(n))) {
					courseInHash.get(lalala).get(0).semester = currentSemester;
					num--;
					num = BacktrackCore(courseInHash, lalala, currentSemester, num);
					// 迭代查找所有的corequisite.
				}

			}

		}

		return num;
	}

	public void FillNonePreCoreReq(ComplexReq complexReq, Linklist simpleReq, ArrayList<Integer> courseBinResult) {
		Node course = null;
		Linklist tempSimple = null;
		ComplexReq tempComplex = null;
		for (Linklist simpleRequirement : degreeProgram.course_list) {
			if (simpleRequirement.first.cName == simpleReq.first.cName) {
				course = simpleRequirement.first.next;
				tempSimple = simpleRequirement;
				break;
			}
		}

		for (ComplexReq complexRequirement : degreeProgram.allComplexReq) {
			if (complexRequirement.first.ComplexReq_Id == complexReq.first.ComplexReq_Id) {
				tempComplex = complexRequirement;
				break;
			}
		}

		while (course != null && course.chosen == false && tempSimple.first.statisfied == false && tempComplex.first.satisfied == false) {
			degreeProgram.checkCourseIn_ReqList(tempSimple.first.cName, course.cName); // mark
																						// this
																						// course
																						// true
																						// in
																						// the
																						// simple

			degreeProgram.CheckAllSimpleAndComplex();
			courseBinResult.add(course.cName);

			course = course.next;
		}
		return;
	}

//	public void ShowRelatedCourse(ArrayList<Integer> pre_and_core) {
//		for (int i = 0; i < pre_and_core.size(); i++) {
//
//			System.out.print(pre_and_core.get(i) + "\n");
//		}
//	}

	public ArrayList<Integer> RemoveTheLastCourseItSelf(
			ArrayList<Integer> courseList) {

		if (courseList.size() == 0) {

		} else {
			courseList.remove(courseList.size() - 1);
		}

		return courseList;
	}

	public ArrayList<Integer> RemoveTheRedInRelatedCourseList(
			ArrayList<Integer> pre_and_core) {
		for (int i = 0; i < pre_and_core.size(); i++) {
			if (pre_and_core.get(i) < 0) {
				pre_and_core.remove(i);
			}
		}

		return pre_and_core;
	}

	public void backtrackCourse(int courseID, ArrayList<Integer> courseList) { // given
																				// course
																				// name
		CrossLinkedList cr = allCross_relation;
		int size = cr.headNodeList.size();
		ArcBox tempArc = new ArcBox();
		NodeInGraph tempNode = new NodeInGraph();
		// int i = 0, j = 0; // 计数器
		for (int i = 0; i < size; i++) {
			tempNode = cr.headNodeList.get(i); // 找到这个将要被选的课程，开始回溯
			if (tempNode.courseID == courseID) {// 找到了这个课名的headnode
												// 开始遍历链表
				tempArc = tempNode.firstIn;
				for (; tempArc != null; tempArc = tempArc.hlink) {
					int relation_type = tempArc.info;// 边的权值，代表关系
					int tempTailCourseID = tempArc.tailCourseID;
					if (relation_type == 3) {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = cr.headNodeList.get(j);
							if (tempNode2.courseID == tempTailCourseID) { // for
																			// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(tempNode2.courseID,
											courseList);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(tempNode2.courseID,
											courseList);
								}
							}

						}
						break;
					} else {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = cr.headNodeList.get(j);
							if (tempNode2.courseID == tempTailCourseID) { // for
																			// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(tempNode2.courseID,
											courseList);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(tempNode2.courseID,
											courseList);

								}
							}

						}
					}

				}
				tempNode.finished = true; // 这个课的所有前驱finished=true;
				tempNode.visited = true;
				courseList.add(tempNode.courseID);
				// System.out.print(tempNode.courseID + " ");
				// System.out.println();
				return;
			}

		}
		// System.out.print("Cannot find selected course in headnodelist!");
		return; // 没找到这个被选的课程
	}

}