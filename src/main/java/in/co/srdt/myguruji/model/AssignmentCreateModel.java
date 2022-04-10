package in.co.srdt.myguruji.model;

import java.util.List;

public class AssignmentCreateModel {
	private long courseID;
	private long assignmentID=0;
	private String assignmentTitle;
	private String assignmentDescr;
	private String assignmentType;
	private String dueDate;
	private String hasPlan="N";
	private String hasUnit="N";
	private String hasQuestion="N";
	private String isPublished="N";
	private double maxMarks=0.0;
	private String createdby;
	private String level;
	private List<AssignmentPlan> assignplan;
	private List<AddedQuestion> questionlist;
	
	public AssignmentCreateModel() {
		super();
	}

	public AssignmentCreateModel(long courseID, long assignmentID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String hasPlan, String hasUnit, String hasQuestion,
			String isPublished, double maxMarks, String createdby, String level, List<AssignmentPlan> assignplan,
			List<AddedQuestion> questionlist) {
		super();
		this.courseID = courseID;
		this.assignmentID = assignmentID;
		this.assignmentTitle = assignmentTitle;
		this.assignmentDescr = assignmentDescr;
		this.assignmentType = assignmentType;
		this.dueDate = dueDate;
		this.hasPlan = hasPlan;
		this.hasUnit = hasUnit;
		this.hasQuestion = hasQuestion;
		this.isPublished = isPublished;
		this.maxMarks = maxMarks;
		this.createdby = createdby;
		this.level = level;
		this.assignplan = assignplan;
		this.questionlist = questionlist;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public long getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(long assignmentID) {
		this.assignmentID = assignmentID;
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

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<AssignmentPlan> getAssignplan() {
		return assignplan;
	}

	public void setAssignplan(List<AssignmentPlan> assignplan) {
		this.assignplan = assignplan;
	}

	public List<AddedQuestion> getQuestionlist() {
		return questionlist;
	}

	public void setQuestionlist(List<AddedQuestion> questionlist) {
		this.questionlist = questionlist;
	}

	@Override
	public String toString() {
		return "AssignmentCreateModel [courseID=" + courseID + ", assignmentID=" + assignmentID + ", assignmentTitle="
				+ assignmentTitle + ", assignmentDescr=" + assignmentDescr + ", assignmentType=" + assignmentType
				+ ", dueDate=" + dueDate + ", hasPlan=" + hasPlan + ", hasUnit=" + hasUnit + ", hasQuestion="
				+ hasQuestion + ", isPublished=" + isPublished + ", maxMarks=" + maxMarks + ", createdby=" + createdby
				+ ", level=" + level + ", assignplan=" + assignplan + ", questionlist=" + questionlist + "]";
	}

}
