package in.co.srdt.myguruji.model;

public class CourseCreate {
	
	private long courseid;
	private String coursecode;
	private String coursetitle;
	private String coursedescr;
	private String createdby;
	
	public CourseCreate() {

	}
	
	public CourseCreate(long courseid, String coursecode, String coursetitle, String coursedescr, String createdby) {
		this.courseid = courseid;
		this.coursecode = coursecode;
		this.coursetitle = coursetitle;
		this.coursedescr = coursedescr;
		this.createdby = createdby;
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
	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	@Override
	public String toString() {
		return "CourseCreate [courseid=" + courseid + ", coursecode=" + coursecode + ", coursetitle=" + coursetitle
				+ ", coursedescr=" + coursedescr + ", createdby=" + createdby + "]";
	}

}
