package in.co.srdt.myguruji.model;


public class CourseDetails {

	private long courseid;
	private long planid;
	private String coursecode;
	private String coursetitle;
	private String coursedescr;
	private String createdby;
	private String plancode;
	
	public CourseDetails() {
		
	}

	public CourseDetails(long courseid, long planid, String coursecode, String coursetitle, String coursedescr,
			String createdby, String plancode) {
		this.courseid = courseid;
		this.planid = planid;
		this.coursecode = coursecode;
		this.coursetitle = coursetitle;
		this.coursedescr = coursedescr;
		this.createdby = createdby;
		this.plancode = plancode;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public long getPlanid() {
		return planid;
	}

	public void setPlanid(long planid) {
		this.planid = planid;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCoursedescr() {
		return coursedescr;
	}

	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getPlancode() {
		return plancode;
	}

	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}

	@Override
	public String toString() {
		return "CourseDetails [courseid=" + courseid + ", planid=" + planid + ", coursecode=" + coursecode
				+ ", coursetitle=" + coursetitle + ", coursedescr=" + coursedescr + ", createdby=" + createdby
				+ ", plancode=" + plancode + "]";
	}
}
