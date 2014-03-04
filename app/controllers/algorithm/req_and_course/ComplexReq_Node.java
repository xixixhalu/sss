package controllers.algorithm.req_and_course;

public class ComplexReq_Node {//head node name
	public String Complex_Req_Name; 
	public Linklist SimpleReq;
	public int ComplexReq_Id;
	
	
	public ComplexReq_Node next;
	

	public ComplexReq_Node(Linklist l){
		this.SimpleReq = l;
		
	}
	public ComplexReq_Node(int  id){
		this.ComplexReq_Id = id;
		
	}

}
