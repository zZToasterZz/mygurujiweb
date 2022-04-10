package in.co.srdt.myguruji.model;

public class BatchDetailsbySchedule {
	private String courseid;
	private String coursetitle;
	private String batchid;
	private String batchcode;
	private String batchtitle;
	private String scheduleid;
	private String startdatetime;
	private String enddatetime;
	private String duration;
	private String assessmentid;
	private String assessmenttitle;
	private String assessmentdescr;
	private String examstatus;
	private String studentid;
	
	public BatchDetailsbySchedule() {
		super();
	}

	public BatchDetailsbySchedule(String courseid, String coursetitle, String batchid, String batchcode,
			String batchtitle, String scheduleid, String startdatetime, String enddatetime, String duration,
			String assessmentid, String assessmenttitle, String assessmentdescr, String examstatus, String studentid) {
		super();
		this.courseid = courseid;
		this.coursetitle = coursetitle;
		this.batchid = batchid;
		this.batchcode = batchcode;
		this.batchtitle = batchtitle;
		this.scheduleid = scheduleid;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.assessmentid = assessmentid;
		this.assessmenttitle = assessmenttitle;
		this.assessmentdescr = assessmentdescr;
		this.examstatus = examstatus;
		this.studentid = studentid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public String getBatchtitle() {
		return batchtitle;
	}

	public void setBatchtitle(String batchtitle) {
		this.batchtitle = batchtitle;
	}

	public String getScheduleid() {
		return scheduleid;
	}

	public void setScheduleid(String scheduleid) {
		this.scheduleid = scheduleid;
	}

	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}

	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAssessmentid() {
		return assessmentid;
	}

	public void setAssessmentid(String assessmentid) {
		this.assessmentid = assessmentid;
	}

	public String getAssessmenttitle() {
		return assessmenttitle;
	}

	public void setAssessmenttitle(String assessmenttitle) {
		this.assessmenttitle = assessmenttitle;
	}

	public String getAssessmentdescr() {
		return assessmentdescr;
	}

	public void setAssessmentdescr(String assessmentdescr) {
		this.assessmentdescr = assessmentdescr;
	}

	public String getExamstatus() {
		return examstatus;
	}

	public void setExamstatus(String examstatus) {
		this.examstatus = examstatus;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	@Override
	public String toString() {
		return "BatchDetailsbySchedule [courseid=" + courseid + ", coursetitle=" + coursetitle + ", batchid=" + batchid
				+ ", batchcode=" + batchcode + ", batchtitle=" + batchtitle + ", scheduleid=" + scheduleid
				+ ", startdatetime=" + startdatetime + ", enddatetime=" + enddatetime + ", duration=" + duration
				+ ", assessmentid=" + assessmentid + ", assessmenttitle=" + assessmenttitle + ", assessmentdescr="
				+ assessmentdescr + ", examstatus=" + examstatus + ", studentid=" + studentid + "]";
	}
	
}
