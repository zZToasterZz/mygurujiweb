package in.co.srdt.myguruji.model;

public class AssessResultList
{
	private String assessmentid;
	private String studentid;
	private String emplid;
	private String campus_id;
	private String name;
	private String course_id;
	private String batch_id;
	private String batch_code;
	private String batch_type;
	private String marks;
	private String attendance;
	private String submission;
	private String totalmarks;
	private String status;
	
	public AssessResultList(String assessmentid, String studentid, String emplid, String campus_id, String name,
			String course_id, String batch_id, String batch_code, String batch_type, String marks, String attendance,
			String submission, String totalmarks, String status) {
		super();
		this.assessmentid = assessmentid;
		this.studentid = studentid;
		this.emplid = emplid;
		this.campus_id = campus_id;
		this.name = name;
		this.course_id = course_id;
		this.batch_id = batch_id;
		this.batch_code = batch_code;
		this.batch_type = batch_type;
		this.marks = marks;
		this.attendance = attendance;
		this.submission = submission;
		this.totalmarks = totalmarks;
		this.status = status;
	}
	public AssessResultList() {
		super();
	}
	public String getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(String assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getBatch_code() {
		return batch_code;
	}
	public void setBatch_code(String batch_code) {
		this.batch_code = batch_code;
	}
	public String getBatch_type() {
		return batch_type;
	}
	public void setBatch_type(String batch_type) {
		this.batch_type = batch_type;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getSubmission() {
		return submission;
	}
	public void setSubmission(String submission) {
		this.submission = submission;
	}
	public String getTotalmarks() {
		return totalmarks;
	}
	public void setTotalmarks(String totalmarks) {
		this.totalmarks = totalmarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCampus_id() {
		return campus_id;
	}
	public void setCampus_id(String campus_id) {
		this.campus_id = campus_id;
	}
	@Override
	public String toString() {
		return "AssessResultList [assessmentid=" + assessmentid + ", studentid=" + studentid + ", emplid=" + emplid
				+ ", campus_id=" + campus_id + ", name=" + name + ", course_id=" + course_id + ", batch_id=" + batch_id
				+ ", batch_code=" + batch_code + ", batch_type=" + batch_type + ", marks=" + marks + ", attendance="
				+ attendance + ", submission=" + submission + ", totalmarks=" + totalmarks + ", status=" + status + "]";
	}
}