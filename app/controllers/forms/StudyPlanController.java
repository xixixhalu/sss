package controllers;

import play.mvc.*;

import controllers.algorithm.req_and_course.*;

public class StudyPlanController extends Controller {
	
	public static void generateReq(Integer id){
		boolean chooeseSuccess =false;//是否选课成功
		//TestLinkList list = new TestLinkList();// set
//		Degree degree = Degree.findById(id);
//		TestLinkList degreeProgram = new TestLinkList(degree.getTitle());	//add new degree
//		List<String> complexIds = degree.getReq_ids();						//get Requirement ids
//		for(String complexId : complexIds)
//		{ 
//			try{
//				Requirement req = Requirement.findById(Integer.valueOf(complexId)); //get Requirement
//				JSONArray srReqs = new JSONArray(req.getSr_ids());
//				ComplexReq complexReq = null;
//				if(srReqs.length() < 2)
//					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), "or");
//				else
//					complexReq = new ComplexReq(Integer.valueOf(req.getId()), req.getTitle(), (String)((JSONObject)srReqs.get(1)).get("relation"));
//				JSONObject srReqObject = null;
//				for (int i = 0; i < srReqs.length(); i++) {
//					srReqObject = (JSONObject) srReqs.get(i);
//					int srId = srReqObject.getInt("id");					//get Simple Requirment
//					Sr sr = Sr.findById(new Integer(srId));
//					int cgId = Integer.valueOf(sr.getCg_id());
//					int reqNum = Integer.valueOf(sr.getRequired_num());
//					Linklist simpleReq = new Linklist(srId, sr.getTitle(), reqNum); //initiate simple requirement
//					Cg cg = Cg.findById(new Integer(cgId));
//					List<String> courseIds = cg.getCourse_ids();
//					for(String courseId : courseIds)
//					{
//						simpleReq.insertNode(1, Integer.valueOf(courseId));	//add course
//					}
//					complexReq.insertSimple(simpleReq);
//					degreeProgram.course_list.add(simpleReq);
//				}
//				degreeProgram.addComplexReq(complexReq);
//			}catch(Exception e)
//			{
//				
//			}	
//		}
		TestLinkList degreeProgram  =new TestLinkList("degreeName1"); //need degreeName input
		ComplexReq complexReq1 = new ComplexReq(1,"complexReq1","or"); 
		
		
		Linklist simpleReq1 = new Linklist(1,"simpleReq1",15);
		
		//req1 -> course1->course2
		addCourse(degreeProgram, simpleReq1, 100);
		
        //req1 -> course1->course2
		addCourse(degreeProgram, simpleReq1, 200);
		
		complexReq1.insertSimple(simpleReq1);
		degreeProgram.addComplexReq(complexReq1);
		degreeProgram.course_list.add(simpleReq1);
		
		degreeProgram.displayallComplexReq();
		
		//course1 -> req1 ->re2
		add2Course_List2(degreeProgram, simpleReq1,100);
		add2Course_List2(degreeProgram, simpleReq1,200);
		
		degreeProgram.displayCourseList();
		
		//Linklist linkList1 = new Linklist();// simple_requirement 链表

//		linkList1.addFirstNode("Requirement_1");
//		
//		linkList1.insertNode(1, "CS105");
//		linkList1.insertNode(2, "CS108");
//		linkList1.insertNode(3, "CS109");
		// linkList1.displayAllNodes();
		// linkList.deleteByData(99);
		// System.out.println("After deleting:");
		// linkList.displayAllNodes();

//		linkList2.addFirstNode("Requirement_2");
//		
//		linkList2.insertNode(1, "CS135");
//		linkList2.insertNode(1, "CS105");
//		linkList2.insertNode(1, "CS455");


//		linkList3.addFirstNode("Requirement_3");
//		linkList3.insertNode(1, "CS105");
//		linkList3.insertNode(1, "CS135");
//		linkList3.insertNode(1, "CS404");
//		linkList3.insertNode(1, "CS455");


//		Linklist linkList4 = new Linklist();
//		linkList4.addFirstNode("Requirement_4");
//		linkList4.insertNode(1, "CS500");
//		linkList4.insertNode(1, "CS404");
//		linkList4.insertNode(1, "CS455");

//		list.course_list.add(linkList1);
//		list.course_list.add(linkList2);
//		list.course_list.add(linkList3);
//		list.course_list.add(linkList4);
		
//		int size = list.course_list.size();
//		System.out.print("Size is: "+size);
//		System.out.println();

		//list.displayReqList();//显示requirement list

//		Course_LinkList cLink_list = new Course_LinkList();// 课程链表
//		cLink_list.addFirstNode(100);
//		cLink_list.insertNode(1, 10);
//		cLink_list.insertNode(1, 11);
//		cLink_list.insertNode(1, 12);
//
//		Course_LinkList cLink_list2 = new Course_LinkList();// each link list
//		cLink_list2.addFirstNode(101);
//		cLink_list2.insertNode(1, 10);
//
//		Course_LinkList cLink_list3 = new Course_LinkList();// each link list
//		cLink_list3.addFirstNode(102);
//		cLink_list3.insertNode(1, 10);
//
//		Course_LinkList cLink_list4 = new Course_LinkList();// each link list
//		cLink_list4.addFirstNode(103);
//		cLink_list4.insertNode(1, 11);
//		cLink_list4.insertNode(1, 12);
//
//		Course_LinkList cLink_list5 = new Course_LinkList();// each link list
//		cLink_list5.addFirstNode(104);
//
//		cLink_list5.insertNode(1, 12);
//		cLink_list5.insertNode(1, 13);
//
//		Course_LinkList cLink_list6 = new Course_LinkList();// each link list
//		cLink_list6.addFirstNode(105);
//		cLink_list6.insertNode(1, 11);
//		cLink_list6.insertNode(1, 12);
//		cLink_list6.insertNode(1, 13);
//
//		list.course_list2.add(cLink_list);
//		list.course_list2.add(cLink_list2);
//		list.course_list2.add(cLink_list3);
//		list.course_list2.add(cLink_list4);
//		list.course_list2.add(cLink_list5);
//		list.course_list2.add(cLink_list6);
		//list.displayCourseList();//显示course list
		
		//开始选课
//		chooeseSuccess = list.checkCourseIn_ReqList("Requirement_1", "CS109");
//		if(chooeseSuccess){
//			list.displayReqList();//显示requirement list
//			list.displayCourseList();//显示course list
//		}else
//		{
//			System.out.print("failed");
//		}
		
//		int complexId = 1;
//		ComplexReq complexReq1 = new ComplexReq(complexId);  //add a complex requirement 
//		
//		complexReq1.insertSimple(linkList2);  //add a simple requirement into a complex requirement
//		
//		list.addComplexReq(complexReq1);   //add this complex requirement into complex_requirement list
//		list.displayallComplexReq();//dialplay all complex requirement
		play.Logger.info("================================================");
	}
	
	
	public static void addCourse(TestLinkList degreeProgram,Linklist simpleReq1, int courseID){
		boolean ifCourseExist = degreeProgram.prepareInsertCourse(courseID);
		//System.out.println(ifCourseExist);
		if(ifCourseExist){	
				simpleReq1.insertNode(degreeProgram.course.get(courseID));
		}else{
			//System.out.println("OK");
			Node newCourse = new Node(courseID);
			degreeProgram.course.put(100, newCourse);
			simpleReq1.insertNode(newCourse);
		}
		return;
	}
	
	
	public static void add2Course_List2(TestLinkList degreeProgram, Linklist simpleReq1, int courseID){
		Course_LinkList courseNode = new Course_LinkList(courseID);
		int simpleReqName = simpleReq1.first.cName;
		CourseNode simpleReq = new CourseNode(simpleReqName);
		courseNode.insertNode(simpleReq);
		degreeProgram.addReq2List(courseNode);
		
	}
	
}