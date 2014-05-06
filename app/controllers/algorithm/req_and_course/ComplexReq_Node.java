package controllers.algorithm.req_and_course;

public class ComplexReq_Node {// head node name
	public String Complex_Req_Name; // complex requirement name

	public SimpleReq SimpleReq; // simple requirement
	public int ComplexReq_Id; // complex requirement id

	public String relation; // simple requirement relation

	public boolean satisfied = false; // this complex requirement is satisfied
										// or not

	public ComplexReq_Node next;

	/**
	 * Given a simple requirement, add it into complex requirement link list as
	 * a node
	 * 
	 * @param l
	 *            simple requirement
	 * 
	 * @return null
	 */
	public ComplexReq_Node(SimpleReq l) {
		this.SimpleReq = l;

	}

	/**
	 * Initialize the first node of a complex requirement
	 * 
	 * @param id
	 *            complex requirement id
	 * 
	 * @param Complex_Req_Name
	 *            complex requirement name
	 * 
	 * @param relation
	 *            the relation between each simple requirements within this
	 *            complex requirement could be OR or AND
	 * 
	 * @return null
	 */
	public ComplexReq_Node(int id, String Complex_Req_Name, String relation) {
		this.ComplexReq_Id = id;
		this.Complex_Req_Name = Complex_Req_Name;
		this.relation = relation;

	}

}
