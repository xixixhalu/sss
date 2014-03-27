package controllers.algorithm.pre_and_core;

import java.util.concurrent.ArrayBlockingQueue;

public class Cal_Depth {
	public ArrayBlockingQueue<ArcBox> level = new ArrayBlockingQueue<ArcBox>(256); // put
																			// each
																			// level
																			// course
																			// in
																			// this
																			// queue
	public CrossLinkedList allCross_relation_example = new CrossLinkedList();

	public void Create_Graph() {

		allCross_relation_example.addCourse(115);
		allCross_relation_example.addCourse(334);
		allCross_relation_example.addCourse(502);
		allCross_relation_example.addCourse(135);
		allCross_relation_example.addCourse(284);
		allCross_relation_example.addCourse(181);
		allCross_relation_example.addCourse(383);
		allCross_relation_example.addCourse(-1);
		allCross_relation_example.addCourse(-2);
		allCross_relation_example.addCourse(-3);
		allCross_relation_example.addCourse(503);
		allCross_relation_example.addCourse(579);
		allCross_relation_example.addCourse(548);
		allCross_relation_example.addCourse(594);
		allCross_relation_example.addCourse(578);

		//allCross_relation_example.Display_All_Headnode();

		allCross_relation_example.setArcBox(115, 334, 1);
		allCross_relation_example.setArcBox(115,  284, 1);
		allCross_relation_example.setArcBox(135, 334, 1);
		allCross_relation_example.setArcBox(502, -1, 3);
		allCross_relation_example.setArcBox(135, -1, 3);
		allCross_relation_example.setArcBox(135, 284, 2);
		allCross_relation_example.setArcBox(284, -2, 3);
		allCross_relation_example.setArcBox(181, -2, 3);
		allCross_relation_example.setArcBox(-2, 383, 2);
		allCross_relation_example.setArcBox(-1, 503, 1);
		allCross_relation_example.setArcBox(503, 579, 1);
		allCross_relation_example.setArcBox(594, -3, 3);
		allCross_relation_example.setArcBox(579, -3, 3);
		allCross_relation_example.setArcBox(548, 594, 1);
		allCross_relation_example.setArcBox(-3, 578, 1);

		//allCross_relation_example.displayCrossLinkedList();
		System.out.println();

	}

	public void BFS_Min() {
		for (int i = 0; i < this.allCross_relation_example.headNodeList.size(); i++) {

			NodeInGraph course = this.allCross_relation_example.headNodeList.get(i);
			if (course.firstIn == null) {
				if (course.assign == false) {
					course.assign = true;
					course.minDepth = 1;
					ArcBox arc = course.firstOut;
					while(arc !=null){
						level.add(arc);
						arc = arc.tlink;
					}
					BFS_Travese_Min();
				}

			}
		}

	}

	public void BFS_Travese_Min() {

		ArrayBlockingQueue<ArcBox> next = new ArrayBlockingQueue<ArcBox>(50);

		while (level.isEmpty() == false) {
			while (level.isEmpty() == false) {
				ArcBox tempCourse = level.poll();
				NodeInGraph temp = this.allCross_relation_example
						.findHeadCourse(tempCourse.headCourseID);
				int stepLength=0;
				if(tempCourse.info==1){
					stepLength=1;
				}
				ArcBox arcOfTemp = temp.firstOut;
				while (arcOfTemp != null) {
					next.add(arcOfTemp);
					arcOfTemp = arcOfTemp.tlink;
				}
				if (temp.assign == false) {
					temp.assign = true;
					//all course depth is equal to its tail course's depth + 1
					temp.minDepth = this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).minDepth + stepLength; 
				} else { // courses in queue have been assigned with a depth
					if (temp.minDepth > this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).minDepth + stepLength)
						temp.minDepth = this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).minDepth + stepLength; 

				}
			}
			next.drainTo(level);
			//level = next;
			next.clear();
		}

	}
	
	
	
	public void BFS_Max() {
		for (int i = 0; i < this.allCross_relation_example.headNodeList.size(); i++) {

			NodeInGraph course = this.allCross_relation_example.headNodeList.get(i);
			if (course.firstIn == null) {
				if (course.assign == false) {
					course.assign = true;
					course.maxDepth = 1;
					ArcBox arc = course.firstOut;
					while(arc !=null){
						level.add(arc);
						arc = arc.tlink;
					}
					BFS_Travese_Max();
				}

			}
		}

	}

	public void BFS_Travese_Max() {

		ArrayBlockingQueue<ArcBox> next = new ArrayBlockingQueue<ArcBox>(50);

		while (level.isEmpty() == false) {
			while (level.isEmpty() == false) {
				ArcBox tempCourse = level.poll();
				NodeInGraph temp = this.allCross_relation_example
						.findHeadCourse(tempCourse.headCourseID);
				int stepLength=0;
				if(tempCourse.info==1){
					stepLength=1;
				}
				ArcBox arcOfTemp = temp.firstOut;
				while (arcOfTemp != null) {
					next.add(arcOfTemp);
					arcOfTemp = arcOfTemp.tlink;
				}
				if (temp.assign == false) {
					temp.assign = true;
					//all course depth is equal to its tail course's depth + 1
					temp.maxDepth = this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).maxDepth + stepLength; 
				} else { // courses in queue have been assigned with a depth
					if (temp.maxDepth < this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).maxDepth + stepLength)
						temp.maxDepth = this.allCross_relation_example.findHeadCourse(tempCourse.tailCourseID).maxDepth + stepLength; 

				}
			}
			next.drainTo(level);
			//level = next;
			next.clear();
		}

	}
	public void Display_All_Headnode_Min() {
		System.out.print("Show all the head nodes: " + '\n');
		for (int i = 0; i < this.allCross_relation_example.headNodeList.size(); i++) {
			this.allCross_relation_example.headNodeList.get(i).showNodeWithAssign_Min();
		}
	}
	
	public void Display_All_Headnode_Max() {
		System.out.print("Show all the head nodes: " + '\n');
		for (int i = 0; i < this.allCross_relation_example.headNodeList.size(); i++) {
			this.allCross_relation_example.headNodeList.get(i).showNodeWithAssign_Max();
		}
	}

	public static void main(String args[]) {
		Cal_Depth g = new Cal_Depth();
		g.Create_Graph();
		g.BFS_Min();
		g.Display_All_Headnode_Min();

	}
}
