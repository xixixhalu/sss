package controllers.algorithm.req_and_course;

public class ComplexReq {
	public ComplexReq_Node first;
	public boolean satisfied;
	public int required_num;
	public int count = 0;
	public String relation;
	

//	public ComplexReq(String name){
//		ComplexReq_Node c = new ComplexReq_Node();
//		c.Complex_Req_Name = name;
//		this.first = null;
//		this.satisfied = false;
//		this.count = 0;
//		this.required_num = 0;
//		
//	}
	
	
	public ComplexReq(int id){
		ComplexReq_Node c = new ComplexReq_Node(id);
		this.first = c;
		this.satisfied = false;
		this.count = 0;
		this.required_num = 0;
		this.first.next = null;
	}
	
	public ComplexReq(ComplexReq_Node l){ 
		ComplexReq_Node simple = l;
		this.first = simple;
	}

	public void insertSimple(Linklist l){  //firstly find the simple requirement
		ComplexReq_Node simple = new ComplexReq_Node(l);
		simple.next = this.first.next;
		this.first.next = simple;
		
	}
	
	public void addRequired_num (int n){
		this.required_num = n;
	}
	
	public void displayComplexReq(){
		ComplexReq_Node simpleReq = first.next;
		while(simpleReq!=null){
			simpleReq.SimpleReq.displayAllNodes();
			simpleReq = simpleReq.next;
		}
		
	}
	
}
