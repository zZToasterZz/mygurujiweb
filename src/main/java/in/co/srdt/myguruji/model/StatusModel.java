package in.co.srdt.myguruji.model;

public class StatusModel
{
	private long statusid;
	private long assessmentid;
	private long studentid;
	private String status;
	
	public StatusModel(long statusid, long assessmentid, long studentid, String status) {
		super();
		this.statusid = statusid;
		this.assessmentid = assessmentid;
		this.studentid = studentid;
		this.status = status;
	}
	public StatusModel() {
		super();
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "StatusModel [statusid=" + statusid + ", assessmentid=" + assessmentid + ", studentid=" + studentid
				+ ", status=" + status + "]";
	}
}