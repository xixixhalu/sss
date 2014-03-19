package controllers.algorithm.req_and_course;

public class ComplexReq_Node {//head node name
	public String Complex_Req_Name; //complex requirement name
	
	public Linklist SimpleReq; //simple requirement 
	public int ComplexReq_Id; // complex requirement id
	
	public String relation; // simple requirement relation
	
	public boolean satisfied=false; // this complex requirement is satisfied or not
	
	
	public ComplexReq_Node next;
	

	public ComplexReq_Node(Linklist l){
		this.SimpleReq = l;
		
	}
	public ComplexReq_Node(int id, String Complex_Req_Name, String relation){
		this.ComplexReq_Id = id;
		this.Complex_Req_Name = Complex_Req_Name;
		this.relation = relation;
		
	}

}
