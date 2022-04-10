package in.co.srdt.myguruji.model;

public class AssignmentMarksUpdateModel {
	
	private long assignmentId;
	private long studentId;
	private long responseId;
	private long questionId;
	private double marks;
	public String submitflag;
	
	public AssignmentMarksUpdateModel() {
		super();
	}

	public AssignmentMarksUpdateModel(long assignmentId, long studentId, long responseId, long questionId, double marks,
			String submitflag) {
		super();
		this.assignmentId = assignmentId;
		this.studentId = studentId;
		this.responseId = responseId;
		this.questionId = questionId;
		this.marks = marks;
		this.submitflag = submitflag;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public String getSubmitflag() {
		return submitflag;
	}

	public void setSubmitflag(String submitflag) {
		this.submitflag = submitflag;
	}

	@Override
	public String toString() {
		return "AssignmentMarksUpdateModel [assignmentId=" + assignmentId + ", studentId=" + studentId + ", responseId="
				+ responseId + ", questionId=" + questionId + ", marks=" + marks + ", submitflag=" + submitflag + "]";
	}
		
}
