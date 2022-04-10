package in.co.srdt.myguruji.model.coursePlan;

import java.util.List;

public class CoursePlanDetailsGet
{
	private long courseplanid;
	private long courseid;
	private long batchid;
	private String coursetitle;
	private String batchcode;
	private String batchtitle;
	private String courseplantitle;
	private String courseplandescr;
	private String courseplancode;
	private String createdby;
	private long assignmentCount;
	private List<CoursePlanUnitDetails> units;
	private List<CoursePlanBookDetails> books;
	
	public long getAssignmentCount() {
		return assignmentCount;
	}

	public void setAssignmentCount(long assignmentCount) {
		this.assignmentCount = assignmentCount;
	}

	public CoursePlanDetailsGet(long courseplanid, long courseid, long batchid, String coursetitle, String batchcode,
			String batchtitle, String courseplantitle, String courseplandescr, String courseplancode, String createdby,
			long assignmentCount, List<CoursePlanUnitDetails> units, List<CoursePlanBookDetails> books) {
		super();
		this.courseplanid = courseplanid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.coursetitle = coursetitle;
		this.batchcode = batchcode;
		this.batchtitle = batchtitle;
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.courseplancode = courseplancode;
		this.createdby = createdby;
		this.assignmentCount = assignmentCount;
		this.units = units;
		this.books = books;
	}

	public CoursePlanDetailsGet(long courseplanid, long courseid, long batchid, String coursetitle, String batchcode,
			String batchtitle, String courseplantitle, String courseplandescr, String courseplancode, String createdby,
			List<CoursePlanUnitDetails> units, List<CoursePlanBookDetails> books) {
		super();
		this.courseplanid = courseplanid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.coursetitle = coursetitle;
		this.batchcode = batchcode;
		this.batchtitle = batchtitle;
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.courseplancode = courseplancode;
		this.createdby = createdby;
		this.units = units;
		this.books = books;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
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

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public String getCourseplancode() {
		return courseplancode;
	}

	public void setCourseplancode(String courseplancode) {
		this.courseplancode = courseplancode;
	}

	public CoursePlanDetailsGet() {
		super();
	}

	public long getCourseplanid() {
		return courseplanid;
	}

	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
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

	public List<CoursePlanUnitDetails> getUnits() {
		return units;
	}

	public void setUnits(List<CoursePlanUnitDetails> units) {
		this.units = units;
	}

	public List<CoursePlanBookDetails> getBooks() {
		return books;
	}

	public void setBooks(List<CoursePlanBookDetails> books) {
		this.books = books;
	}
}