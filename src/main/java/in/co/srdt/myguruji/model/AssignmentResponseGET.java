package in.co.srdt.myguruji.model;

import java.util.List;

public class AssignmentResponseGET
{
	private String assignmentResponseID;
	private String assignmentid;
	private String studentid;
	private String questionid;
	private String objectiveResponse;
	private String subjectiveResponse;
	private String hasAttachment;
	private String marksObtained;
	private String emplid;
	private String responsestatus;
	private List<AssignmentAttachment> attachments;
	
	public String getAssignmentResponseID() {
		return assignmentResponseID;
	}
	public void setAssignmentResponseID(String assignmentResponseID) {
		this.assignmentResponseID = assignmentResponseID;
	}
	public String getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(String assignmentid) {
		this.assignmentid = assignmentid;
	}
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getObjectiveResponse() {
		return objectiveResponse;
	}
	public void setObjectiveResponse(String objectiveResponse) {
		this.objectiveResponse = objectiveResponse;
	}
	public String getSubjectiveResponse() {
		return subjectiveResponse;
	}
	public void setSubjectiveResponse(String subjectiveResponse) {
		this.subjectiveResponse = subjectiveResponse;
	}
	public String getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getResponsestatus() {
		return responsestatus;
	}
	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	public List<AssignmentAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AssignmentAttachment> attachments) {
		this.attachments = attachments;
	}
	public AssignmentResponseGET(String assignmentResponseID, String assignmentid, String studentid, String questionid,
			String objectiveResponse, String subjectiveResponse, String hasAttachment, String marksObtained,
			String emplid, String responsestatus, List<AssignmentAttachment> attachments) {
		super();
		this.assignmentResponseID = assignmentResponseID;
		this.assignmentid = assignmentid;
		this.studentid = studentid;
		this.questionid = questionid;
		this.objectiveResponse = objectiveResponse;
		this.subjectiveResponse = subjectiveResponse;
		this.hasAttachment = hasAttachment;
		this.marksObtained = marksObtained;
		this.emplid = emplid;
		this.responsestatus = responsestatus;
		this.attachments = attachments;
	}
	public AssignmentResponseGET() {
		super();
	}
	@Override
	public String toString() {
		return "AssignmentResponseGET [assignmentResponseID=" + assignmentResponseID + ", assignmentid=" + assignmentid
				+ ", studentid=" + studentid + ", questionid=" + questionid + ", objectiveResponse=" + objectiveResponse
				+ ", subjectiveResponse=" + subjectiveResponse + ", hasAttachment=" + hasAttachment + ", marksObtained="
				+ marksObtained + ", emplid=" + emplid + ", responsestatus=" + responsestatus + ", attachments="
				+ attachments + "]";
	}
}