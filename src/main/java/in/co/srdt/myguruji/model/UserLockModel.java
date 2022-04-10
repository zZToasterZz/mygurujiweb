package in.co.srdt.myguruji.model;

public class UserLockModel
{
	private long courseid;
	private long batchid;
	private long studentid;
	private String reason;
	private String resolve;
	private String createdby;
	private String lockstatus;
	
	public UserLockModel(long courseid, long batchid, long studentid, String reason, String resolve, String createdby, String lockstatus) {
		this.courseid = courseid;
		this.batchid = batchid;
		this.studentid = studentid;
		this.reason = reason;
		this.resolve = resolve;
		this.createdby = createdby;
		this.lockstatus = lockstatus;
	}
	public UserLockModel() {
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
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
	@Override
	public String toString() {
		return "UserLockModel [courseid=" + courseid + ", batchid=" + batchid + ", studentid=" + studentid + ", reason="
				+ reason + ", resolve=" + resolve + "]";
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getLockstatus() {
		return lockstatus;
	}
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}
}