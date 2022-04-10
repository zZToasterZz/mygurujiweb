package in.co.srdt.myguruji.model;

import java.util.Date;

public class Assignment
{
	private long CourseID;
	private long AssignmentID;
	private String AssignmentTitle;
	private String AssignmentDescr;
	private String AssignmentType;
	private String DueDate;
	private String HasPlan = "N";
	private String HasUnit = "N";
	private String HasQuestion = "N";
	private String IsPublished = "N";
	private double MaxMarks;
	
	public Assignment() {
		super();
	}
	public Assignment(long courseID, long assignmentID, String assignmentTitle, String assignmentDescr,
			String assignmentType, String dueDate, String hasPlan, String hasUnit, String hasQuestion, String isPublished,
			double maxMarks) {
		super();
		CourseID = courseID;
		AssignmentID = assignmentID;
		AssignmentTitle = assignmentTitle;
		AssignmentDescr = assignmentDescr;
		AssignmentType = assignmentType;
		DueDate = dueDate;
		HasPlan = hasPlan;
		HasUnit = hasUnit;
		HasQuestion = hasQuestion;
		IsPublished = isPublished;
		MaxMarks = maxMarks;
	}
	public long getCourseID() {
		return CourseID;
	}
	public void setCourseID(long courseID) {
		CourseID = courseID;
	}
	public long getAssignmentID() {
		return AssignmentID;
	}
	public void setAssignmentID(long assignmentID) {
		AssignmentID = assignmentID;
	}
	public String getAssignmentTitle() {
		return AssignmentTitle;
	}
	public void setAssignmentTitle(String assignmentTitle) {
		AssignmentTitle = assignmentTitle;
	}
	public String getAssignmentDescr() {
		return AssignmentDescr;
	}
	public void setAssignmentDescr(String assignmentDescr) {
		AssignmentDescr = assignmentDescr;
	}
	public String getAssignmentType() {
		return AssignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		AssignmentType = assignmentType;
	}
	public String getDueDate() {
		return DueDate;
	}
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	public String getHasPlan() {
		return HasPlan;
	}
	public void setHasPlan(String hasPlan) {
		HasPlan = hasPlan;
	}
	public String getHasUnit() {
		return HasUnit;
	}
	public void setHasUnit(String hasUnit) {
		HasUnit = hasUnit;
	}
	public String getHasQuestion() {
		return HasQuestion;
	}
	public void setHasQuestion(String hasQuestion) {
		HasQuestion = hasQuestion;
	}
	public String getIsPublished() {
		return IsPublished;
	}
	public void setIsPublished(String isPublished) {
		IsPublished = isPublished;
	}
	public double getMaxMarks() {
		return MaxMarks;
	}
	public void setMaxMarks(double maxMarks) {
		MaxMarks = maxMarks;
	}
	@Override
	public String toString() {
		return "Assignment [CourseID=" + CourseID + ", AssignmentID=" + AssignmentID + ", AssignmentTitle="
				+ AssignmentTitle + ", AssignmentDescr=" + AssignmentDescr + ", AssignmentType=" + AssignmentType
				+ ", DueDate=" + DueDate + ", HasPlan=" + HasPlan + ", HasUnit=" + HasUnit + ", HasQuestion="
				+ HasQuestion + ", IsPublished=" + IsPublished + ", MaxMarks=" + MaxMarks + "]";
	}
}