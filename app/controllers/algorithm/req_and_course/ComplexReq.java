package controllers.algorithm.req_and_course;

public class ComplexReq {

	public ComplexReq_Node first;

	/**
	 * Check if a complex requirement is null
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public boolean isNull() {
		ComplexReq_Node temp = first;
		if (temp.next.SimpleReq.isNull() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Initialize a complex requirement
	 * 
	 * @param id
	 *            complex requirement id
	 * @param Complex_Req_Name
	 *            complex requirement name
	 * 
	 * @param relation
	 *            the relation between each simple requirements within this
	 *            complex requirement could be OR or AND
	 * 
	 * @return null
	 */
	public ComplexReq(int id, String Complex_Req_Name, String relation) {
		ComplexReq_Node c = new ComplexReq_Node(id, Complex_Req_Name, relation);
		this.first = c;
		this.first.next = null;
	}

	/**
	 * Initialize a complex requirement with a simple requirement
	 * 
	 * @param l
	 *            simple requirement (complex requirement node)
	 * 
	 * @return null
	 */
	public ComplexReq(ComplexReq_Node l) {
		ComplexReq_Node simple = l;
		this.first = simple;
	}

	/**
	 * Insert a simple requirement into a complex requirement
	 * 
	 * @param l
	 *            simple requirement (complex requirement node)
	 * 
	 * @return null
	 */
	public void insertSimple(SimpleReq l) { // firstly find the simple
											// requirement
		ComplexReq_Node simple = new ComplexReq_Node(l);
		simple.next = this.first.next;
		this.first.next = simple;

	}

	/**
	 * Display all complex requirement with their simple requirements
	 * 
	 * @param null
	 * 
	 * @return null
	 */
	public void displayComplexReq() {
		System.out.println("This complex requirement has " + first.relation + " relation and status is " + first.satisfied + "\n");
		ComplexReq_Node simpleReq = first.next;
		while (simpleReq != null) {
			simpleReq.SimpleReq.displayAllNodes();
			simpleReq = simpleReq.next;
		}

	}

}
