package in.co.srdt.myguruji.model;

public class AssignmentStudentSubmissionStatusList 
{
	private String campusid;
	private String name;
	private double marksobtained;
	private String timelysubmission;
	private String evaluationstatus;
	private String submitstatus;
	private double maxmarks;
	private String studentid;
	
	public AssignmentStudentSubmissionStatusList() {
		super();
	}

	public AssignmentStudentSubmissionStatusList(String campusid, String name, double marksobtained,
			String timelysubmission, String evaluationstatus, String submitstatus, double maxmarks, String studentid) {
		super();
		this.campusid = campusid;
		this.name = name;
		this.marksobtained = marksobtained;
		this.timelysubmission = timelysubmission;
		this.evaluationstatus = evaluationstatus;
		this.submitstatus = submitstatus;
		this.maxmarks = maxmarks;
		this.studentid = studentid;
	}

	public String getCampusid() {
		return campusid;
	}

	public void setCampusid(String campusid) {
		this.campusid = campusid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMarksobtained() {
		return marksobtained;
	}

	public void setMarksobtained(double marksobtained) {
		this.marksobtained = marksobtained;
	}

	public String getTimelysubmission() {
		return timelysubmission;
	}

	public void setTimelysubmission(String timelysubmission) {
		this.timelysubmission = timelysubmission;
	}

	public String getEvaluationstatus() {
		return evaluationstatus;
	}

	public void setEvaluationstatus(String evaluationstatus) {
		this.evaluationstatus = evaluationstatus;
	}

	public String getSubmitstatus() {
		return submitstatus;
	}

	public void setSubmitstatus(String submitstatus) {
		this.submitstatus = submitstatus;
	}

	public double getMaxmarks() {
		return maxmarks;
	}

	public void setMaxmarks(double maxmarks) {
		this.maxmarks = maxmarks;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	@Override
	public String toString() {
		return "AssignmentStudentSubmissionStatusList [campusid=" + campusid + ", name=" + name + ", marksobtained="
				+ marksobtained + ", timelysubmission=" + timelysubmission + ", evaluationstatus=" + evaluationstatus
				+ ", submitstatus=" + submitstatus + ", maxmarks=" + maxmarks + ", studentid=" + studentid + "]";
	}
}
