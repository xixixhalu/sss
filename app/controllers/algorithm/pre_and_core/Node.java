package controllers.algorithm.pre_and_core;


//same as VexNode
public class Node {
	
	public String courseName;
	public int courseID;
	
	public String abbreviation;
	public int credit;
	public String semester[];
	
	public int depth = 10000; //
	public boolean assign = false;
	public ArcBox firstIn, firstOut;
	public boolean visited = false;  //course is marked, visited=true
	public boolean finished = false; //course is selected successfully with pre/core-requisite finished = true ;
	
	public Node(){
		this.courseID=0;
		this.courseName = "";
		this.firstIn = null;
		this.firstOut = null;
	}
	
	
	
	public Node(int courseID){
		this.courseID=courseID;
		this.firstIn = null;
		this.firstOut=null;
		this.courseName="";
		this.abbreviation="";
		
	}
	
	
	public void showNode(){
		//System.out.println(courseName + " ");
		System.out.println(courseID + " ");
	}
	
	public void showNodeWithAssign(){
		System.out.println(courseID + ": " + depth);
	}
}
