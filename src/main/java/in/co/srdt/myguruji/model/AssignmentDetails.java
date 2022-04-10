package in.co.srdt.myguruji.model;

import java.util.List;

public class AssignmentDetails
{
	private long assignmentID;
	private long courseID;
	private String assignmentTitle;
	private String assignmentDescr;
	private String assignmentType;
	private String dueDate;
	private String level;
	private String hasPlan;
	private String hasUnit;
	private String hasQuestion;
	private String hasStudent;
	private String isPublished;
	private String maxMarks;
	private String createdby;
	private String responseStatus;
	private List<AssignmentPlan> assignplan=null;
	private List<AssignmentQuestion> questionlist;
	private List<Student> studentinfo;
	
	public AssignmentDetails() {
		super();
	}

	public List<Student> getStudentinfo() {
		return studentinfo;
	}

	public void setStudentinfo(List<Student> studentinfo) {
		this.studentinfo = studentinfo;
	}

	public AssignmentDetails(long assignmentID, long courseID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String level, String hasPlan, String hasUnit, String hasQuestion,
			String hasStudent, String isPublished, String maxMarks, String createdby, String responseStatus,
			List<AssignmentPlan> assignplan, List<AssignmentQuestion> questionlist, List<Student> studentinfo) {
		super();
		this.assignmentID = assignmentID;
		this.courseID = courseID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.level = level;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.hasStudent = hasStudent;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.createdby = createdby;
		this.responseStatus = responseStatus;
		this.assignplan = assignplan;
		this.questionlist = questionlist;
		this.studentinfo = studentinfo;
	}

	public AssignmentDetails(long assignmentID, long courseID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String level, String hasPlan, String hasUnit, String hasQuestion,
			String hasStudent, String isPublished, String maxMarks, String createdby, String responseStatus,
			List<AssignmentPlan> assignplan, List<AssignmentQuestion> questionlist) {
		super();
		this.assignmentID = assignmentID;
		this.courseID = courseID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.level = level;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.hasStudent = hasStudent;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.createdby = createdby;
		this.responseStatus = responseStatus;
		this.assignplan = assignplan;
		this.questionlist = questionlist;
	}

	public long getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public String getAssignmentTitle() {
		return assignmentTitle;
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public String getAssignmentDescr() {
		return assignmentDescr;
	}

	public void setAssignmentDescr(String assignmentDescr) {
		this.assignmentDescr = assignmentDescr;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHasPlan() {
		return hasPlan;
	}

	public void setHasPlan(String hasPlan) {
		this.hasPlan = hasPlan;
	}

	public String getHasUnit() {
		return hasUnit;
	}

	public void setHasUnit(String hasUnit) {
		this.hasUnit = hasUnit;
	}

	public String getHasQuestion() {
		return hasQuestion;
	}

	public void setHasQuestion(String hasQuestion) {
		this.hasQuestion = hasQuestion;
	}

	public String getHasStudent() {
		return hasStudent;
	}

	public void setHasStudent(String hasStudent) {
		this.hasStudent = hasStudent;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<AssignmentPlan> getAssignplan() {
		return assignplan;
	}

	public void setAssignplan(List<AssignmentPlan> assignplan) {
		this.assignplan = assignplan;
	}

	public List<AssignmentQuestion> getQuestionlist() {
		return questionlist;
	}

	public void setQuestionlist(List<AssignmentQuestion> questionlist) {
		this.questionlist = questionlist;
	}

	@Override
	public String toString() {
		return "AssignmentDetails [assignmentID=" + assignmentID + ", courseID=" + courseID + ", assignmentTitle="
				+ assignmentTitle + ", assignmentDescr=" + assignmentDescr + ", assignmentType=" + assignmentType
				+ ", dueDate=" + dueDate + ", level=" + level + ", hasPlan=" + hasPlan + ", hasUnit=" + hasUnit
				+ ", hasQuestion=" + hasQuestion + ", hasStudent=" + hasStudent + ", isPublished=" + isPublished
				+ ", maxMarks=" + maxMarks + ", createdby=" + createdby + ", responseStatus=" + responseStatus
				+ ", assignplan=" + assignplan + ", questionlist=" + questionlist + ", studentinfo=" + studentinfo
				+ "]";
	}
}