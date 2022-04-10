package in.co.srdt.myguruji.model;

public class MarksModel
{
	private String emplid;
	private long assessmentid;
	private long questionid;
	private long studentid;
	private String marks;
	
	public MarksModel(String emplid, long assessmentid, long questionid, long studentid, String marks) {
		super();
		this.emplid = emplid;
		this.assessmentid = assessmentid;
		this.questionid = questionid;
		this.studentid = studentid;
		
		if(marks == null)
			this.marks = "0";
		if(marks.equals(""))
			this.marks = "0";
		this.marks = marks;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public MarksModel(String emplid, long assessmentid, long questionid, String marks) {
		super();
		this.emplid = emplid;
		this.assessmentid = assessmentid;
		this.questionid = questionid;
		
		if(marks == null)
			this.marks = "0";
		if(marks.equals(""))
			this.marks = "0";
		this.marks = marks;
	}
	public MarksModel() {
		super();
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		if(marks == null)
			this.marks = "0";
		if(marks.equals(""))
			this.marks = "0";
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "MarksModel [emplid=" + emplid + ", assessmentid=" + assessmentid + ", questionid=" + questionid
				+ ", marks=" + marks + "]";
	}
}