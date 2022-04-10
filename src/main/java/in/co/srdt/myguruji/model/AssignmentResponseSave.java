package in.co.srdt.myguruji.model;

import java.util.List;

public class AssignmentResponseSave
{
	private long assignmentResponseID;
	private long assignmentid;
	private long studentid;
	private long questionid;
	private String objectiveResponse;
	private String subjectiveResponse;
	private String marksObtained;
	private String responsestatus;
	private String hasAttachment;
	private List<AssignmentAttachment> attachments;
	
	public AssignmentResponseSave() {
		super();
	}
	public AssignmentResponseSave(long assignmentResponseID, long assignmentid, long studentid, long questionid,
			String objectiveResponse, String subjectiveResponse, String marksObtained,
			String responsestatus, String hasAttachment, List<AssignmentAttachment> attachments) {
		super();
		this.assignmentResponseID = assignmentResponseID;
		this.assignmentid = assignmentid;
		this.studentid = studentid;
		this.questionid = questionid;
		this.objectiveResponse = objectiveResponse;
		this.subjectiveResponse = subjectiveResponse;
		this.marksObtained = marksObtained;
		this.responsestatus = responsestatus;
		this.hasAttachment = hasAttachment;
		this.attachments = attachments;
	}
	public String getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public long getAssignmentResponseID() {
		return assignmentResponseID;
	}
	public void setAssignmentResponseID(long assignmentResponseID) {
		this.assignmentResponseID = assignmentResponseID;
	}
	public long getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
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
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
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
	@Override
	public String toString() {
		return "AssignmentResponseSave [assignmentResponseID=" + assignmentResponseID + ", assignmentid=" + assignmentid
				+ ", studentid=" + studentid + ", questionid=" + questionid + ", objectiveResponse=" + objectiveResponse
				+ ", subjectiveResponse=" + subjectiveResponse + ", marksObtained=" + marksObtained
				+ ", responsestatus=" + responsestatus + ", hasAttachment=" + hasAttachment + ", attachments="
				+ attachments + "]";
	}
	
}