package controllers.algorithm.req_and_course;


public class ComplexReq {
	
	public ComplexReq_Node first;

//	public ComplexReq(String name){
//		ComplexReq_Node c = new ComplexReq_Node();
//		c.Complex_Req_Name = name;
//		this.first = null;
//		this.satisfied = false;
//		this.count = 0;
//		this.required_num = 0;
//		
//	}
	public boolean isNull(){
		ComplexReq_Node temp = first;
		if(temp.next.SimpleReq.isNull()==false){
			return false;
		}else{
			return true;
		}
	}
	
	public ComplexReq(int id, String Complex_Req_Name, String relation){
		ComplexReq_Node c = new ComplexReq_Node(id, Complex_Req_Name, relation);
		this.first = c;
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
	
	
	public void displayComplexReq(){
		System.out.println("This complex requirement has "+first.relation +" relation and status is "+first.satisfied +"\n");
		ComplexReq_Node simpleReq = first.next;
		while(simpleReq!=null){
			simpleReq.SimpleReq.displayAllNodes();
			simpleReq = simpleReq.next;
		}
		
	}
	
}
