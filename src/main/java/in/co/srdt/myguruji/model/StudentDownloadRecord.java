package in.co.srdt.myguruji.model;

public class StudentDownloadRecord {
	
	private long studentdownloadrecordid; 
	private long contentid;
	private String downloadtime;
	private long courseid;
	private long batchid;
	private long unitid;
	private String typedecr;
	private long courseplanid;
	private long studentid;
	private String uploadtime;
	private String status;
	private double marks;
	
	public StudentDownloadRecord() {
		super();
	}


	public StudentDownloadRecord(long studentdownloadrecordid, long contentid, String downloadtime, long courseid, long batchid, long unitid, String typedecr, long courseplanid, long studentid, String uploadtime, String status, double marks) {
		this.studentdownloadrecordid = studentdownloadrecordid;
		this.contentid = contentid;
		this.downloadtime = downloadtime;
		this.courseid = courseid;
		this.batchid = batchid;
		this.unitid = unitid;
		this.typedecr = typedecr;
		this.courseplanid = courseplanid;
		this.studentid = studentid;
		this.uploadtime = uploadtime;
		this.status = status;
		this.marks = marks;
	}

	public long getStudentdownloadrecordid() {
		return studentdownloadrecordid;
	}
	public void setStudentdownloadrecordid(long studentdownloadrecordid) {
		this.studentdownloadrecordid = studentdownloadrecordid;
	}
	public long getContentid() {
		return contentid;
	}
	public void setContentid(long contentid) {
		this.contentid = contentid;
	}
	public String getDownloadtime() {
		return downloadtime;
	}
	public void setDownloadtime(String downloadtime) {
		this.downloadtime = downloadtime;
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
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}
	public String getTypedecr() {
		return typedecr;
	}
	public void setTypedecr(String typedecr) {
		this.typedecr = typedecr;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public long getStudentid() {
		return studentid;
	}

	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}

	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}


	@Override
	public String toString() {
		return "StudentDownloadRecord [studentdownloadrecordid=" + studentdownloadrecordid + ", contentid=" + contentid
				+ ", downloadtime=" + downloadtime + ", courseid=" + courseid + ", batchid=" + batchid + ", unitid="
				+ unitid + ", typedecr=" + typedecr + ", courseplanid=" + courseplanid + ", studentid=" + studentid
				+ ", uploadtime=" + uploadtime + ", status=" + status + ", marks=" + marks + "]";
	}
	
}
