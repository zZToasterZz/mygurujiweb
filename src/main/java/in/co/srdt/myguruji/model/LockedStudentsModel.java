package in.co.srdt.myguruji.model;

public class LockedStudentsModel {
	
	private String lockstatusid;
	private String courseid;
	private String batchid;
	private String studentid;
	private String lockstatus;
	private String lockId;
	private String reason;
	private String resolve;
	private String createdby;
	private String campusid;
	private String fullname;
	
	public LockedStudentsModel() {
		super();
	}

	public LockedStudentsModel(String lockstatusid, String courseid, String batchid, String studentid,
			String lockstatus, String lockId, String reason, String resolve, String createdby, String campusid,
			String fullname) {
		super();
		this.lockstatusid = lockstatusid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.studentid = studentid;
		this.lockstatus = lockstatus;
		this.lockId = lockId;
		this.reason = reason;
		this.resolve = resolve;
		this.createdby = createdby;
		this.campusid = campusid;
		this.fullname = fullname;
	}

	public String getLockstatusid() {
		return lockstatusid;
	}

	public void setLockstatusid(String lockstatusid) {
		this.lockstatusid = lockstatusid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getLockstatus() {
		return lockstatus;
	}

	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResolve() {
		return resolve;
	}

	public void setResolve(String resolve) {
		this.resolve = resolve;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCampusid() {
		return campusid;
	}

	public void setCampusid(String campusid) {
		this.campusid = campusid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}
