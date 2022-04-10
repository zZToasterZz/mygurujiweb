package in.co.srdt.myguruji.model.coursePlan;

import java.util.List;

public class CoursePlanWrapper
{
	private String courseplantitle;
	private String courseplandescr;
	private String createdby;
	private String plancode;
	private long courseid;
	private long courseplanid = 0;
	private long batchid;
	private List<Book> books;
	private List<Unit> units;
	
	@Override
	public String toString() {
		return "CoursePlanWrapper [courseplantitle=" + courseplantitle + ", courseplandescr=" + courseplandescr
				+ ", createdby=" + createdby + ", plancode=" + plancode + ", courseid=" + courseid + ", courseplanid="
				+ courseplanid + ", batchid=" + batchid + "]";
	}
	

	public CoursePlanWrapper(String courseplantitle, String courseplandescr, String createdby, String plancode,
			long courseid, long courseplanid, long batchid, List<Book> books, List<Unit> units) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.plancode = plancode;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.batchid = batchid;
		this.books = books;
		this.units = units;
	}


	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public CoursePlanWrapper() {
		super();
	}

	public long getCourseplanid() {
		return courseplanid;
	}

	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public String getCourseplantitle() {
		return courseplantitle;
	}

	public void setCourseplantitle(String courseplantitle) {
		this.courseplantitle = courseplantitle;
	}

	public String getCourseplandescr() {
		return courseplandescr;
	}

	public void setCourseplandescr(String courseplandescr) {
		this.courseplandescr = courseplandescr;
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

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public List<Unit> getUnits() {
		return units;
	}
	public void setUnits(List<Unit> units) {
		this.units = units;
	}
}