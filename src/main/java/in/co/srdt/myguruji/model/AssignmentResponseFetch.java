package in.co.srdt.myguruji.model;

public class AssignmentResponseFetch
{
	private long assignmentresponseid;
	private long assignmentid;
	private long questionid;
	private long assignmentquestionid;
	private long courseid;
	private String qtype;
	private String objectiveresponse;
	private String subjectiveresponse;
	private String hasAttachment;
	private String type;
	
	public AssignmentResponseFetch(long assignmentresponseid, long assignmentid, long questionid,
			long assignmentquestionid, long courseid, String qtype, String objectiveresponse, String subjectiveresponse,
			String hasAttachment, String type) {
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
	}
	public long getAssignmentresponseid() {
		return assignmentresponseid;
	}
	public void setAssignmentresponseid(long assignmentresponseid) {
		this.assignmentresponseid = assignmentresponseid;
	}
	public AssignmentResponseFetch(long assignmentid, long questionid, long assignmentquestionid, long courseid,
			String qtype, String objectiveresponse, String subjectiveresponse, String hasAttachment, String type) {
		super();
		this.assignmentid = assignmentid;
		this.questionid = questionid;
		this.assignmentquestionid = assignmentquestionid;
		this.courseid = courseid;
		this.qtype = qtype;
		this.objectiveresponse = objectiveresponse;
		this.subjectiveresponse = subjectiveresponse;
		this.hasAttachment = hasAttachment;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AssignmentResponseFetch() {
		super();
	}
	public String getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public AssignmentResponseFetch(long assignmentid, long questionid, long assignmentquestionid, long courseid,
			String qtype, String objectiveresponse, String subjectiveresponse, String hasAttachment) {
		super();
		this.assignmentid = assignmentid;
		this.questionid = questionid;
		this.assignmentquestionid = assignmentquestionid;
		this.courseid = courseid;
		this.qtype = qtype;
		this.objectiveresponse = objectiveresponse;
		this.subjectiveresponse = subjectiveresponse;
		this.hasAttachment = hasAttachment;
	}
	public AssignmentResponseFetch(long questionid, long assignmentquestionid, long courseid, String qtype,
			String objectiveresponse, String subjectiveresponse) {
		super();
		this.questionid = questionid;
		this.assignmentquestionid = assignmentquestionid;
		this.courseid = courseid;
		this.qtype = qtype;
		this.objectiveresponse = objectiveresponse;
		this.subjectiveresponse = subjectiveresponse;
	}
	public AssignmentResponseFetch(long assignmentid, long questionid, long assignmentquestionid, long courseid,
			String qtype, String objectiveresponse, String subjectiveresponse) {
		super();
		this.assignmentid = assignmentid;
		this.questionid = questionid;
		this.assignmentquestionid = assignmentquestionid;
		this.courseid = courseid;
		this.qtype = qtype;
		this.objectiveresponse = objectiveresponse;
		this.subjectiveresponse = subjectiveresponse;
	}
	public long getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public long getAssignmentquestionid() {
		return assignmentquestionid;
	}
	public void setAssignmentquestionid(long assignmentquestionid) {
		this.assignmentquestionid = assignmentquestionid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getObjectiveresponse() {
		return objectiveresponse;
	}
	public void setObjectiveresponse(String objectiveresponse) {
		this.objectiveresponse = objectiveresponse;
	}
	public String getSubjectiveresponse() {
		return subjectiveresponse;
	}
	public void setSubjectiveresponse(String subjectiveresponse) {
		this.subjectiveresponse = subjectiveresponse;
	}
	@Override
	public String toString() {
		return "AssignmentResponseFetch [assignmentid=" + assignmentid + ", questionid=" + questionid
				+ ", assignmentquestionid=" + assignmentquestionid + ", courseid=" + courseid + ", qtype=" + qtype
				+ ", objectiveresponse=" + objectiveresponse + ", subjectiveresponse=" + subjectiveresponse
				+ ", hasAttachment=" + hasAttachment + "]";
	}
}