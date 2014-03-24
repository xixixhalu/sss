package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import models.Cg;
import models.Course;
import models.Degree;
import models.Requirement;
import models.Sr;

import org.json.JSONArray;
import org.json.JSONObject;

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


public class StudyPlan{
	
	public int redNode = 0;
	public TestLinkList degreeProgram;
	CrossLinkedList allCross_relation;
	
	
	public void GetCourseMaxDepthInGraph(Cal_Depth calSemester){
		calSemester.allCross_relation_example = allCross_relation;
		calSemester.BFS_Max();
		//calSemester.BFS_Min();
		//calSemester.Display_All_Headnode_Max();
		//calSemester.Display_All_Headnode_Min();
		return;
	}
	
	
	public void CreateDegreeProgram(Integer id){
		//boolean chooeseSuccess =false;//是否选课成功
		Degree degree = Degree.findById(id);
		degreeProgram = new TestLinkList(degree.getTitle());	//add new degree
		List<String> complexIds = degree.getReq_ids();						//get Requirement ids
		allCross_relation = new CrossLinkedList();
		for(String complexId : complexIds)
		{ 
			try{
				Requirement req = Requirement.findById(Integer.valueOf(complexId)); //get Requirement
				JSONArray srReqs = new JSONArray(req.getSr_ids());
				ComplexReq complexReq = null;
				if(srReqs.length() < 2)
					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), "or");
				else
					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), (String)((JSONObject)srReqs.get(1)).get("relation"));
				JSONObject srReqObject = null;
				for (int i = 0; i < srReqs.length(); i++) {
					srReqObject = (JSONObject) srReqs.get(i);
					int srId = srReqObject.getInt("id");					//get Simple Requirment
					Sr sr = Sr.findById(new Integer(srId));
					int cgId = Integer.valueOf(sr.getCg_id());
					int reqNum = Integer.valueOf(sr.getRequired_num());
					Linklist simpleReq = find_or_create_simpleReq(srId, sr.getTitle(), reqNum); //initiate simple requirement
					Cg cg = Cg.findById(new Integer(cgId));
					List<String> courseIds = cg.getCourse_ids();
					for(String courseId : courseIds)
					{
						addCourse(simpleReq, Integer.valueOf(courseId));	//add course
						add2Course_List2(complexReq,simpleReq, Integer.valueOf(courseId));
					}
					
					complexReq.insertSimple(simpleReq);
					degreeProgram.course_list.add(simpleReq);
				}
				
				degreeProgram.addComplexReq(complexReq);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
		
		allCross_relation.removeAloneNode();
		
		degreeProgram.CheckAllSimpleAndComplex();
		
		//System.out.print("Before AutoFill:");
		//degreeProgram.displayallComplexReq();
		//degreeProgram.displayCourseList();
		//System.out.print("\n");
		Cal_Depth calSemester = new Cal_Depth();
		calSemester.allCross_relation_example = allCross_relation;
		AutoFillCourseBin(calSemester);
		//System.out.print("After AutoFill: \n");
		//degreeProgram.displayallComplexReq();
		//degreeProgram.displayCourseList();
		
		//allCross_relation.Display_All_Headnode();
		//allCross_relation.displayCrossLinkedList();
		
		play.Logger.info("================================================");
	}
	
	
	public void addCourse(Linklist simpleReq1, int courseID){
		//System.out.println(ifCourseExist);

			//System.out.println("OK");
		
			Node newNode = new Node(courseID);
		
			if (degreeProgram.course.containsKey(courseID)) {
				degreeProgram.course.get(courseID).add(newNode);
			} else {
				ArrayList<Node> clist = new ArrayList<Node>();
				clist.add(newNode);
				degreeProgram.course.put(courseID, clist);
			}
			
			simpleReq1.insertNode(newNode);
			
			/**
			 * @author tongrui
			 * function: construct the crosslist
			 */

			allCross_relation.addCourse(Integer.valueOf(courseID));
			Course course = Course.findById(courseID);
			
			String prereq = course.getPrereq(2);
			String coreq = course.getCoreq(2);
			
		
			
			if (!prereq.trim().equals("-")) {
				String[] prelist = prereq.split(" ");
				
				if (prelist.length - 2 == 1) {
					allCross_relation.setArcBox(Integer.valueOf(prelist[2]), courseID, 1);
				} else if (prelist.length - 2 == 3) {
					if (prelist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(prelist[2]), courseID, 1);
						allCross_relation.setArcBox(Integer.valueOf(prelist[4]), courseID, 1);
					} else if (prelist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(prelist[2]), redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(prelist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 3);
					}
				} else if (prelist.length - 2 == 5) {
					if (prelist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(prelist[2]), courseID, 1);
						allCross_relation.setArcBox(Integer.valueOf(prelist[4]), courseID, 1);
						
						if (prelist[5].equals("or")) {
							// issue
						} else if (prelist[5].equals(",")) {
							allCross_relation.setArcBox(Integer.valueOf(prelist[6]), courseID, 1);
						}
					} else if (prelist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(prelist[2]), redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(prelist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 3);
						
						if (prelist[5].equals("or")) {
							allCross_relation.setArcBox(Integer.valueOf(prelist[6]), redNode, 3);
						} else if (prelist[5].equals(",")) {
							allCross_relation.setArcBox(redNode, courseID, 3);
							allCross_relation.setArcBox(Integer.valueOf(prelist[6]), courseID, 1);
						}
					}
				}
			}
			
			if (!coreq.trim().equals("-")) {
				String[] colist = coreq.split(" ");
				
				if (colist.length - 2 == 1) {
					allCross_relation.setArcBox(Integer.valueOf(colist[2]), courseID, 2);
				} else if (colist.length - 2 == 3) {
					if (colist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(colist[2]), courseID, 2);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]), courseID, 2);
					} else if (colist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(colist[2]), redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 3);
					}
				} else if (colist.length - 2 == 5) {
					if (colist[3].equals(",")) {
						allCross_relation.setArcBox(Integer.valueOf(colist[2]), courseID, 2);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]), courseID, 2);
						
						if (colist[5].equals("or")) {
							// issue
						} else if (colist[5].equals(",")) {
							allCross_relation.setArcBox(Integer.valueOf(colist[6]), courseID, 2);
						}
					} else if (colist[3].equals("or")) {
						allCross_relation.addCourse(--redNode);
						allCross_relation.setArcBox(Integer.valueOf(colist[2]), redNode, 3);
						allCross_relation.setArcBox(Integer.valueOf(colist[4]), redNode, 3);
						allCross_relation.setArcBox(redNode, courseID, 3);
						
						if (colist[5].equals("or")) {
							allCross_relation.setArcBox(Integer.valueOf(colist[6]), redNode, 3);
						} else if (colist[5].equals(",")) {
							allCross_relation.setArcBox(redNode, courseID, 3);
							allCross_relation.setArcBox(Integer.valueOf(colist[6]), courseID, 2);
						}
					}
				}
			}
		return;
	}
	
	
	public void add2Course_List2(ComplexReq complexReq, Linklist simpleReq1, int courseID){
		boolean ifCourseExist = degreeProgram.prepareInsertCourseLinkList(courseID);
		if(ifCourseExist){
			int simpleReqName = simpleReq1.first.cName;
			int complexReqID = complexReq.first.ComplexReq_Id;
			CourseNode ReqInfo = new CourseNode(simpleReqName, complexReqID);
			for(int i =0; i<degreeProgram.course_list2.size();i++){
				if(courseID == degreeProgram.course_list2.get(i).first.rName){
					degreeProgram.course_list2.get(i).insertNode(ReqInfo);
				}
					
			}
		}else{
			Course_LinkList courseNode = new Course_LinkList(courseID);
			int simpleReqName = simpleReq1.first.cName;
			int complexReqID = complexReq.first.ComplexReq_Id;
			CourseNode ReqInfo = new CourseNode(simpleReqName, complexReqID);
			courseNode.insertNode(ReqInfo);
			degreeProgram.addReq2List(courseNode);
		}
	
	}
	
	public Linklist find_or_create_simpleReq(int srId, String srTitle, int reqNum){
		boolean simpleReqExist = degreeProgram.prepareInsertSimple(srId);
			if(simpleReqExist){
				int i=0;
				for(; i<degreeProgram.course_list.size();i++){
					if(srId == degreeProgram.course_list.get(i).first.cName)
						break;
				}
				return degreeProgram.course_list.get(i);
			}else{
				Linklist simpleReq = new Linklist(srId, srTitle, reqNum);
				return simpleReq;
			}
	}
	
	public void CheckInSelectedCourse(int complexID, int simpleID, int courseID){
		for(int i =0;i<degreeProgram.allComplexReq.size();i++){
			if(degreeProgram.allComplexReq.get(i).first.ComplexReq_Id == complexID){
				ComplexReq  complexReq = degreeProgram.allComplexReq.get(i);
				if(!complexReq.isNull()){ //if this complex has simples in it
						for(int j = 0; j<degreeProgram.course_list.size();j++){
							if(degreeProgram.course_list.get(j).first.cName==simpleID){
								degreeProgram.checkCourseIn_ReqList(simpleID, courseID);
								for(int k=0; k<allCross_relation.headNodeList.size();k++){
									if(allCross_relation.headNodeList.get(k).courseID==courseID){
										allCross_relation.headNodeList.get(k).assign=true;
										break;
									}
								}
							}
					}
				}
				
			}
		}
	}
	
	
	
	public void AutoFillCourseBin(Cal_Depth calSemester){  //change the arguments and recursively call this function 
		ArrayList<Integer> courseBinResult = new ArrayList<Integer>();
		GetCourseMaxDepthInGraph(calSemester); //mark the min and max in nodeInGraph
		for (int i = 0; i < calSemester.allCross_relation_example.headNodeList.size(); i++) { //in node graph find all value
			NodeInGraph courseInGraph = calSemester.allCross_relation_example.headNodeList.get(i);
			for(Integer key: degreeProgram.course.keySet()){
				if(degreeProgram.course.get(key).get(0).cName==courseInGraph.courseID){//update course both in requirement and graph
					ArrayList<Node> sameCourseList = degreeProgram.course.get(key);
					for(Node course: sameCourseList){
						course.maxDepth = courseInGraph.maxDepth;
						//course.minDepth = courseInGraph.minDepth;
					}
				}
				
			}
		}
		
		
//		for(Integer key: degreeProgram.course.keySet()){
//			System.out.print("The course "+ degreeProgram.course.get(key).get(0).cName + " has max depth "+degreeProgram.course.get(key).get(0).maxDepth +"\n");
//		}
		
		for(int i =0;i<degreeProgram.allComplexReq.size();i++){
			ComplexReq  complexReq = degreeProgram.allComplexReq.get(i);
			if(complexReq.first.satisfied==true){
				continue;// check next complex requirement
			}else{
				ComplexReq_Node simpleReq = complexReq.first.next;// find all unsatisfied complex requirement, fetch its simple requirement
				while(simpleReq !=null){// as long as the simple is not null
					if(simpleReq.SimpleReq.first.statisfied==true){
						//do nothing and check next simple requirement
					}else{
						//add a function to sort as semester as ascending
						Node course = simpleReq.SimpleReq.first.next;//each course in simple requirement
						HashMap<Integer, Node> courseHash = new HashMap<Integer, Node>();
						SortedSet<Integer> courseOrderByMaxDepth = new TreeSet<Integer>();// each simple requirement has a sortedset stores courses' maxDepth
						while(course !=null){
							courseHash.put(course.maxDepth, course);
							courseOrderByMaxDepth.add(course.maxDepth);
							course = course.next;//In this simple requirement, put the maxDepth into set and sort
							
						}
						
//						Iterator<Integer> it = courseOrderByMaxDepth.iterator();
						int maxMaxDepth = courseOrderByMaxDepth.last();
//						while(it.hasNext()){
//							maxMaxDepth = it.next();  //find the latest course
//						}
						
						if(maxMaxDepth==0){
							// all course in this simple requirement has no pre and core relation 
							FillNonePreCoreReq(complexReq, simpleReq.SimpleReq, courseBinResult);
							
						}else{
							//the course in this simple requirement has pre and core relation 
							//test use:
							//simpleReq.SimpleReq.satisfied=true;
							//simpleReq.SimpleReq.first.statisfied=true;
							//simpleReq.SimpleReq.first.needFinish--;
//							Iterator<Integer> it = courseOrderByMaxDepth.iterator();
//							while(it.hasNext()){
//								Node checkCourse = courseHash.get(it.next());
//								ArrayList<Integer> courseList = new ArrayList<Integer> ();
//								//put all pre and core including red in arraylist
//								backtrackCourse(allCross_relation, checkCourse.cName, courseList);
//								RemoveTheLastCourseItSelf(courseList);// remove the new course itself
//								RemoveTheRedInRelatedCourseList(courseList);// remove small red node
//								//assign to all requirement
//								for(Integer needToBeSelectedCourse : courseList){
//									if(degreeProgram.course.containsKey(needToBeSelectedCourse)){
//										for(Linklist assignSimple : degreeProgram.course_list){
//											if(degreeProgram.checkCourseIn_ReqList(assignSimple.first.cName, checkCourse.cName)){
//												degreeProgram.CheckAllSimpleAndComplex();
//												courseBinResult.add(needToBeSelectedCourse);
//												break;
//											}else{
//												courseBinResult.add(needToBeSelectedCourse);
//											}
//											
//											  
//										}
//									}else{
//										courseBinResult.add(needToBeSelectedCourse);
//									}
//									
//									
//								}
//								
//								
//								
//								
//								
//								maxMaxDepth = it.next();  //find the latest course
//							}
							
							
							
						}
						
					}
					// update/check complex requirement
					degreeProgram.CheckAllSimpleAndComplex();
					simpleReq = simpleReq.next;
				}
			}
		}
		
		for(Integer id:courseBinResult){
			System.out.print(id +" ");
		}
		
		System.out.print("\n");
	}
	
	
	public void FillNonePreCoreReq(ComplexReq complexReq, Linklist simpleReq, ArrayList<Integer> courseBinResult){
		Node course=null;
		Linklist tempSimple = null;
		ComplexReq tempComplex=null;
		//TestLinkList originDegree = new TestLinkList();
//		Linklist originSimpleState = null;
//		ComplexReq originComplexState = null;
		for(Linklist simpleRequirement: degreeProgram.course_list){
			if(simpleRequirement.first.cName == simpleReq.first.cName){
				course=simpleRequirement.first.next;
				tempSimple = simpleRequirement;
				break;
			}
		}
		
		for(ComplexReq complexRequirement: degreeProgram.allComplexReq){
			if(complexRequirement.first.ComplexReq_Id == complexReq.first.ComplexReq_Id){
				tempComplex = complexRequirement;
				break;
			}
		}
		
		
		//Need deep copy 
		//originDegree = degreeProgram;
		//degreeProgram.allComplexReq.get(1).first.next.SimpleReq.first.next.cName=1234;
		//System.out.print(degreeProgram.allComplexReq.get(1).first.next.SimpleReq.first.next.cName +"\n");
		//System.out.print(originDegree.allComplexReq.get(1).first.next.SimpleReq.first.next.cName +"\n");
		
		while(course!=null && course.chosen==false &&  
			tempSimple.first.statisfied==false && tempComplex.first.satisfied==false){
			degreeProgram.checkCourseIn_ReqList(tempSimple.first.cName, course.cName); // mark this course true in the simple
			degreeProgram.CheckAllSimpleAndComplex();
			//if(degreeProgram.checker()==false){
			//}else{
			courseBinResult.add(course.cName);
			//}
			
			course = course.next;
		}
		
		//if(course==null && tempSimple.first.statisfied)
		 return;
	}
	
	
	public void ShowRelatedCourse(ArrayList<Integer> pre_and_core){
		for(int i=0; i< pre_and_core.size();i++){
			
			System.out.print( pre_and_core.get(i)+"\n");
		}
	}
	
	public ArrayList<Integer>  RemoveTheLastCourseItSelf(ArrayList<Integer> courseList){
		
		Iterator<Integer> itDelete = courseList.iterator();
		while(itDelete.hasNext()){
			if(itDelete.next()==null)
				itDelete.remove();
			itDelete.next();
		}
		
		
		
		return courseList;
	}
	
	public ArrayList<Integer> RemoveTheRedInRelatedCourseList(ArrayList<Integer> pre_and_core){
		for(int i=0; i< pre_and_core.size();i++){
			if(pre_and_core.get(i)<0){
				pre_and_core.remove(i);
			}
		}
		
		return pre_and_core;
	}
	public void backtrackCourse(int courseID, ArrayList<Integer> courseList) { // given course name
		int size = allCross_relation.headNodeList.size();
		ArcBox tempArc = new ArcBox();
		NodeInGraph tempNode = new NodeInGraph();
		// int i = 0, j = 0; // 计数器
		for (int i = 0; i < size; i++) {
			tempNode = allCross_relation.headNodeList.get(i); // 找到这个将要被选的课程，开始回溯
			if (tempNode.courseID==courseID) {// 找到了这个课名的headnode
																	// 开始遍历链表
				tempArc = tempNode.firstIn;
				for (; tempArc != null; tempArc = tempArc.hlink) {
					int relation_type = tempArc.info;// 边的权值，代表关系
					int tempTailCourseID = tempArc.tailCourseID;
					if (relation_type == 3) {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = allCross_relation.headNodeList.get(j);
							if (tempNode2.courseID==tempTailCourseID) { // for
																				// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(tempNode2.courseID, courseList);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(tempNode2.courseID, courseList);
								}
							}

						}
						break;
					} else {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = allCross_relation.headNodeList.get(j);
							if (tempNode2.courseID==tempTailCourseID) { // for
																				// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(tempNode2.courseID, courseList);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(tempNode2.courseID, courseList);

								}
							}

						}
					}

				}
				tempNode.finished = true; // 这个课的所有前驱finished=true;
				tempNode.visited = true;
				courseList.add(tempNode.courseID);
				//System.out.print(tempNode.courseID + " ");
				//System.out.println();
				return;
			}
			
		}
		System.out.print("Cannot find selected course in headnodelist!");
		return; // 没找到这个被选的课程
	}

	
}