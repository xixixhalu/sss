package controllers.algorithm.pre_and_core;

public class Course_Selection {

	public void backtrackCourse(CrossLinkedList courseRelation,
			int courseID) { // given course name
		CrossLinkedList cr = courseRelation;
		int size = cr.headNodeList.size();
		ArcBox tempArc = new ArcBox();
		NodeInGraph tempNode = new NodeInGraph();
		// int i = 0, j = 0; // 计数器
		for (int i = 0; i < size; i++) {
			tempNode = cr.headNodeList.get(i); // 找到这个将要被选的课程，开始回溯
			if (tempNode.courseID==courseID) {// 找到了这个课名的headnode
																	// 开始遍历链表
				tempArc = tempNode.firstIn;
				for (; tempArc != null; tempArc = tempArc.hlink) {
					int relation_type = tempArc.info;// 边的权值，代表关系
					int tempTailCourseID = tempArc.tailCourseID;
					if (relation_type == 3) {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = cr.headNodeList.get(j);
							if (tempNode2.courseID==tempTailCourseID) { // for
																				// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(courseRelation,
											tempNode2.courseID);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(courseRelation,
											tempNode2.courseID);
								}
							}

						}
						break;
					} else {
						for (int j = 0; j < size; j++) { // 这个for
							// loop只是一个找的过程，找到tempNode的第一个前驱课程，并在headnodelist中看它是否已经被选入
							NodeInGraph tempNode2 = cr.headNodeList.get(j);
							if (tempNode2.courseID==tempTailCourseID) { // for
																				// loop只需要做if判断为正的时候的事情
								if (tempNode2.finished) {// 如果这个前驱课程被选入，应该看下一个前驱课程有没有被选入,回到上一层for循环
									break;
								} else if (tempNode2.finished == false
										&& tempNode2.visited == true) { // 这个前驱节点已经被mark但是它本身还没遍历完
									backtrackCourse(courseRelation,
											tempNode2.courseID);
								} else { // tempNode2.finished == false &&
											// tempNode2.visited == true
									tempNode2.visited = true; // 先把这个节点标记为mark过
									backtrackCourse(courseRelation,
											tempNode2.courseID);

								}
							}

						}
					}

				}
				tempNode.finished = true; // 这个课的所有前驱finished=true;
				tempNode.visited = true;
				System.out.print(tempNode.courseID + " ");
				System.out.println();
				return;
			}
		}
		System.out.print("Cannot find selected course in headnodelist!");
		return; // 没找到这个被选的课程
	}

	// 创建graph example
	public void Create_Graph() {
		CrossLinkedList allCross_relation_example = new CrossLinkedList();
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

		allCross_relation_example.Display_All_Headnode();

		allCross_relation_example.setArcBox(115, 334, 1);// (course1_ID, course2_ID, relation)
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

		allCross_relation_example.displayCrossLinkedList();
		System.out.println();

		backtrackCourse(allCross_relation_example, 578);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Course_Selection courseSelection = new Course_Selection();
		courseSelection.Create_Graph();
	}

}
