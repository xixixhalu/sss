package controllers.algorithm.pre_and_core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import controllers.algorithm.req_and_course.Node;

public class CrossLinkedList {
	// store all head nodes;
	public ArrayList<NodeInGraph> headNodeList = new ArrayList<NodeInGraph>();
	

	// 默认的构造方法
	public CrossLinkedList() {
//		Node firstNode = new Node();
//		this.headNodeList.add(firstNode);
	}
	
	public NodeInGraph findHeadCourse(int courseID){
		int size = this.headNodeList.size();
		for(int i=0; i<size;i++){
			if(this.headNodeList.get(i).courseID == courseID)
				return this.headNodeList.get(i);   //find this course
		}
		return null;
	}

	// 规定总共的课程数量构造方法  
	//give me number of courses to initialize the a list of headnode
//	public CrossLinkedList(int numOf_Course) { // 
//		int temp = numOf_Course;
//		for (int i = 0; i < temp; i++) {
//			String courseTempName = "Course " + i;
//			Node node = new Node(courseTempName);
//			this.headNodeList.add(node);
//		}
//
//	}

	public void addCourse(int courseID) {
		NodeInGraph node = new NodeInGraph(courseID);
		this.headNodeList.add(node);
	}

	public void Display_All_Headnode() {
		System.out.print("Show all the head nodes: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) {
			this.headNodeList.get(i).showNode();
		}
	}

	// add a arc to the linked list
	public void setArcBox(int tail, int head, int info) {
		int size = this.headNodeList.size();
		ArcBox arc = new ArcBox(tail, head, info);

		for (int i = 0; i < size; i++) {
			//I am the tail "me--->"
			if (this.headNodeList.get(i).courseID==tail) { 
				arc.tlink = this.headNodeList.get(i).firstOut;
				this.headNodeList.get(i).firstOut = arc; // 把新的一门课加入到了图中，以arc表示
			}
			//I am the head  "--->me"
			if (this.headNodeList.get(i).courseID==head) {
				arc.hlink = this.headNodeList.get(i).firstIn;
				this.headNodeList.get(i).firstIn = arc;
			}
		}

		//System.out.println("this arc is: " + tail + "-->" + head + " " + info);
	}
	
	public void addAllCourseInGraph(HashMap<Integer, ArrayList<Node>> list){
			
			for(Entry<Integer, ArrayList<Node>> entry:list.entrySet()){
				int courseId = entry.getKey();
				addCourse(courseId);
			}
			
		}

	
	public void removeAloneNode(){
		
		Iterator<NodeInGraph> iter = this.headNodeList.iterator();
		
		while(iter.hasNext())
		{
			NodeInGraph course = iter.next();
			if(course.firstOut == null && course.firstIn == null)
				iter.remove();
		}
			
	}

	// Display
	public void displayCrossLinkedList() {
		System.out.print("Show Cross LinkedList from tail to head: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) { // show head
																// linklist
			ArcBox arc = this.headNodeList.get(i).firstOut;
			while (arc != null) {
				arc.showArcFromTail();
				arc = arc.tlink;
			}
		}

		System.out.print("Show Cross LinkedList from head to tail: " + '\n');
		for (int i = 0; i < this.headNodeList.size(); i++) {
			ArcBox arc = this.headNodeList.get(i).firstIn;
			while (arc != null) {
				arc.showArcFromHead();
				arc = arc.hlink;
			}
		}

		return;
	}
	
	public HashMap<Integer, Integer> TraverCore(){
		HashMap<Integer, Integer> core = new HashMap<Integer, Integer>(); 
		for(int i=0; i<this.headNodeList.size();i++){
			ArcBox arc = this.headNodeList.get(i).firstIn;
			while(arc !=null){
				if(arc.info==2){
					if(arc.tailCourseID<=0){
						for(int j=0; j<this.headNodeList.size();j++){
							if(this.headNodeList.get(j).courseID==arc.tailCourseID){
								ArcBox arc2 = this.headNodeList.get(j).firstIn;
								while(arc2!=null){
									core.put(arc2.tailCourseID, arc2.headCourseID);
									arc2=arc2.hlink;
								}
							}
						}
						core.put(arc.tailCourseID, arc.headCourseID);
					}else{
						core.put(arc.tailCourseID, arc.headCourseID); // A==>B
						
					}
					
				}
				
				arc =arc.hlink;
			}
		}
		return core;
	}
	
	public void DisplayCore(Multimap<Integer, Integer> core){
		for(Integer key : core.keySet()){
			System.out.println(key +" "+ core.get(key));
		}
	}

}
