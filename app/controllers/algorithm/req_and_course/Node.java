package controllers.algorithm.req_and_course;
//requirement link list
public class Node {

	public Node next;
	public int cName;
	//public String cName; // course name
	public boolean chosen; //be chosen or not
	
	
	public Node(int cName){
		this.cName = cName;
	}
	
	public void showNode(){
		play.Logger.info(cName + " " + chosen);
	}
}
