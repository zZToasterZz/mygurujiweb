package in.co.srdt.myguruji.model;

import java.util.Arrays;

public class ResponseWrapper
{
	private String[] assignmentresponseid;
	private String[] assignmentid;
	private String[] questionid;
	private String[] assignmentquestionid;
	private String[] courseid;
	private String[] qtype;
	private String[] objectiveresponse;
	private String[] subjectiveresponse;
	private String[] hasAttachment;
	private String[] type;
	private String[] filesTotal;
	
	public ResponseWrapper(String[] assignmentresponseid, String[] assignmentid, String[] questionid,
			String[] assignmentquestionid, String[] courseid, String[] qtype, String[] objectiveresponse,
			String[] subjectiveresponse, String[] hasAttachment, String[] type, String[] filesTotal) {
		super();
		this.assignmentresponseid = assignmentresponseid;
		this.assignmentid = assignmentid;
		this.questionid = questionid;
		this.assignmentquestionid = assignmentquestionid;
		this.courseid = courseid;
		this.qtype = qtype;
		this.objectiveresponse = objectiveresponse;
		this.subjectiveresponse = subjectiveresponse;
		this.hasAttachment = hasAttachment;
		this.type = type;
		this.filesTotal = filesTotal;
	}
	public ResponseWrapper() {
		super();
	}
	public String[] getFilesTotal() {
		return filesTotal;
	}
	public void setFilesTotal(String[] filesTotal) {
		this.filesTotal = filesTotal;
	}
	public String[] getAssignmentresponseid() {
		return assignmentresponseid;
	}
	public void setAssignmentresponseid(String[] assignmentresponseid) {
		this.assignmentresponseid = assignmentresponseid;
	}
	public String[] getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(String[] assignmentid) {
		this.assignmentid = assignmentid;
	}
	public String[] getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String[] questionid) {
		this.questionid = questionid;
	}
	public String[] getAssignmentquestionid() {
		return assignmentquestionid;
	}
	public void setAssignmentquestionid(String[] assignmentquestionid) {
		this.assignmentquestionid = assignmentquestionid;
	}
	public String[] getCourseid() {
		return courseid;
	}
	public void setCourseid(String[] courseid) {
		this.courseid = courseid;
	}
	public String[] getQtype() {
		return qtype;
	}
	public void setQtype(String[] qtype) {
		this.qtype = qtype;
	}
	public String[] getObjectiveresponse() {
		return objectiveresponse;
	}
	public void setObjectiveresponse(String[] objectiveresponse) {
		this.objectiveresponse = objectiveresponse;
	}
	public String[] getSubjectiveresponse() {
		return subjectiveresponse;
	}
	public void setSubjectiveresponse(String[] subjectiveresponse) {
		this.subjectiveresponse = subjectiveresponse;
	}
	public String[] getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(String[] hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ResponseWrapper [assignmentresponseid=" + Arrays.toString(assignmentresponseid) + ", assignmentid="
				+ Arrays.toString(assignmentid) + ", questionid=" + Arrays.toString(questionid)
				+ ", assignmentquestionid=" + Arrays.toString(assignmentquestionid) + ", courseid="
				+ Arrays.toString(courseid) + ", qtype=" + Arrays.toString(qtype) + ", objectiveresponse="
				+ Arrays.toString(objectiveresponse) + ", subjectiveresponse=" + Arrays.toString(subjectiveresponse)
				+ ", hasAttachment=" + Arrays.toString(hasAttachment) + ", type=" + Arrays.toString(type) + "]";
	}
}