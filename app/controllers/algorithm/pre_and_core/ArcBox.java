package controllers.algorithm.pre_and_core;

//same as ArcBox
public class ArcBox {

	public int tailCourseID; //node at the tail
	public int headCourseID; //node at the head
	public ArcBox tlink; //next arc with same tail
	public ArcBox hlink; //next arc with same head
	int info; //
	
	public ArcBox(){
		this.headCourseID=0;
		this.tailCourseID=0;
		this.hlink=null;
		this.tlink=null;
	}
	
	
	public ArcBox(int tail, int head, int info){ //构造函数,没有初始化指针
		this.tailCourseID = tail;//
		this.headCourseID = head;
		this.info = info;
		this.hlink=null;
		this.tlink=null;
		
	}
	
	public void showArcFromTail(){
		String outPut = this.tailCourseID + "-->" +this.headCourseID+" with " +info;
		System.out.println(outPut);
		
		return;
	}
	
	public void showArcFromHead(){
		String outPut = this.tailCourseID + "-->" +this.headCourseID+" with " +info;
		System.out.println(outPut);
		return;
	}
}
