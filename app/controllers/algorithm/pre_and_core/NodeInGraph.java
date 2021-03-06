package controllers.algorithm.pre_and_core;

//same as VexNode
public class NodeInGraph {

	public String courseName;
	public int courseID;

	public String abbreviation;
	public int credit;
	public String semester[];

	public int minDepth = 10000; //
	public int maxDepth = 0;
	public boolean assign = false;
	public CourseRelation firstIn, firstOut;
	public boolean visited = false; // course is visited in BFS_MAX, visited=true
	public boolean finished = false; // course is selected successfully with
										// pre/core-requisite finished = true ;

	public NodeInGraph() {
		this.courseID = 0;
		this.courseName = "";
		this.firstIn = null;
		this.firstOut = null;
	}

	public NodeInGraph(int courseID) {
		this.courseID = courseID;
		this.firstIn = null;
		this.firstOut = null;
		this.courseName = "";
		this.abbreviation = "";

	}

	/**
	 * Display all courses in course relation DAG
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showNode() {
		// System.out.println(courseName + " ");
		System.out.println(courseID + " " + this.assign);
	}

	// public void showNodeWithAssign_Min(){
	// System.out.println("The" + courseID + " has the min depth: " + minDepth
	// +"\n");
	// }
	
	/**
	 * Display each course in course relation DAG with its max_depth
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void showNodeWithAssign_Max() {
		System.out.println("The" + courseID + " has the max depth: " + maxDepth + "\n");
	}
}
