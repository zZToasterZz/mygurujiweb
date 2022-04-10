package in.co.srdt.myguruji.model;

public class AssignmentQuest {
	
	private long assignmentquesid;
	private long assignmentid;
	private long courseid;
	private long questionid;
	private double marks;
	private String createdby;
		
	public AssignmentQuest() {
		super();
	}

	public AssignmentQuest(long assignmentquesid, long assignmentid, long courseid, long questionid, double marks,
			String createdby) {
		super();
		this.assignmentquesid = assignmentquesid;
		this.assignmentid = assignmentid;
		this.courseid = courseid;
		this.questionid = questionid;
		this.marks = marks;
		this.createdby = createdby;
	}

	public long getAssignmentquesid() {
		return assignmentquesid;
	}

	public void setAssignmentquesid(long assignmentquesid) {
		this.assignmentquesid = assignmentquesid;
	}

	public long getAssignmentid() {
		return assignmentid;
	}

	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public long getQuestionid() {
		return questionid;
	}

	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Override
	public String toString() {
		return "AssignmentQuest [assignmentquesid=" + assignmentquesid + ", assignmentid=" + assignmentid
				+ ", courseid=" + courseid + ", questionid=" + questionid + ", marks=" + marks + ", createdby="
				+ createdby + "]";
	}
	
}
