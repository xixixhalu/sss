package controllers;

import java.util.List;

import models.Cg;
import models.Course;
import models.Degree;
import models.Requirement;
import models.Sr;

import org.json.JSONArray;
import org.json.JSONObject;

import play.mvc.Controller;
import controllers.algorithm.pre_and_core.CrossLinkedList;
import controllers.algorithm.req_and_course.ComplexReq;
import controllers.algorithm.req_and_course.CourseNode;
import controllers.algorithm.req_and_course.Course_LinkList;
import controllers.algorithm.req_and_course.Linklist;
import controllers.algorithm.req_and_course.Node;
import controllers.algorithm.req_and_course.TestLinkList;


public class StudyPlanController extends Controller {
	public static CrossLinkedList allCross_relation = new CrossLinkedList();
	public static int redNode = 0;
	
	public static void CreateDegreeProgram(Integer id){
		//boolean chooeseSuccess =false;//是否选课成功
		Degree degree = Degree.findById(id);
		TestLinkList degreeProgram = new TestLinkList(degree.getTitle());	//add new degree
		List<String> complexIds = degree.getReq_ids();						//get Requirement ids
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
					Linklist simpleReq = find_or_create_simpleReq(degreeProgram,srId, sr.getTitle(), reqNum); //initiate simple requirement
					Cg cg = Cg.findById(new Integer(cgId));
					List<String> courseIds = cg.getCourse_ids();
					for(String courseId : courseIds)
					{
						addCourse(degreeProgram, simpleReq, Integer.valueOf(courseId));	//add course
						add2Course_List2(degreeProgram, simpleReq, Integer.valueOf(courseId));
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
		
		//allCross_relation.Display_All_Headnode();
		//allCross_relation.displayCrossLinkedList();

		
//		degreeProgram.displayAllCourse();

//		TestLinkList degreeProgram  =new TestLinkList("degreeName1"); //need degreeName input
//		ComplexReq complexReq1 = new ComplexReq(1,"complexReq1","or"); 
//		
//		
//		Linklist simpleReq1 = new Linklist(1,"simpleReq1",15);
//		
//		//req1 -> course1->course2
//		addCourse(degreeProgram, simpleReq1, 100);
//		
//        //req1 -> course1->course2
//		addCourse(degreeProgram, simpleReq1, 200);
//		addCourse(degreeProgram, simpleReq1, 300);
//		addCourse(degreeProgram, simpleReq1, 400);
//		addCourse(degreeProgram, simpleReq1, 500);
//		addCourse(degreeProgram, simpleReq1, 600);
//		
//		complexReq1.insertSimple(simpleReq1);
//		degreeProgram.addComplexReq(complexReq1);
//		degreeProgram.course_list.add(simpleReq1);
//		
//		degreeProgram.displayallComplexReq();
//		
//		//course1 -> req1 ->re2
//		add2Course_List2(degreeProgram, simpleReq1,100);
//		add2Course_List2(degreeProgram, simpleReq1,200);
//		add2Course_List2(degreeProgram, simpleReq1,300);
//		add2Course_List2(degreeProgram, simpleReq1,400);
//		add2Course_List2(degreeProgram, simpleReq1,500);
//		add2Course_List2(degreeProgram, simpleReq1,600);
//		
//		degreeProgram.displayCourseList();
		
		//mark student's  chosen course
//		boolean  chooeseSuccess = degreeProgram.checkCourseIn_ReqList(10,108);
//		if(chooeseSuccess){
//			degreeProgram.displayallComplexReq();
//			degreeProgram.displayCourseList();
//		}
//		degreeProgram.displayallComplexReq();
		degreeProgram.displayCourseList();
		play.Logger.info("================================================");
	}
	
	
	public static void addCourse(TestLinkList degreeProgram,Linklist simpleReq1, int courseID){
		//System.out.println(ifCourseExist);

			//System.out.println("OK");
			Node newCourse = new Node(courseID);
			degreeProgram.course.put(courseID, newCourse);
			simpleReq1.insertNode(newCourse);
			
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
	
	
	public static void add2Course_List2(TestLinkList degreeProgram, Linklist simpleReq1, int courseID){
		boolean ifCourseExist = degreeProgram.prepareInsertCourseLinkList(courseID);
		if(ifCourseExist){
			int simpleReqName = simpleReq1.first.cName;
			CourseNode simpleReq = new CourseNode(simpleReqName);
			for(int i =0; i<degreeProgram.course_list2.size();i++){
				if(courseID == degreeProgram.course_list2.get(i).first.rName){
					degreeProgram.course_list2.get(i).insertNode(simpleReq);
				}
					
			}
		}else{
			Course_LinkList courseNode = new Course_LinkList(courseID);
			int simpleReqName = simpleReq1.first.cName;
			CourseNode simpleReq = new CourseNode(simpleReqName);
			courseNode.insertNode(simpleReq);
			degreeProgram.addReq2List(courseNode);
		}
	
	}
	
	public static Linklist find_or_create_simpleReq(TestLinkList degreeProgram, int srId, String srTitle, int reqNum){
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
	
}