package in.co.srdt.myguruji.model.coursePlan;

public class CoursePlanModel
{
	private String courseplantitle;
	private String courseplandescr;
	private String createdby;
	private String plancode;
	private long courseid;
	
	public String getCourseplantitle() {
		return courseplantitle;
	}
	public void setCourseplantitle(String courseplantitle) {
		this.courseplantitle = courseplantitle;
	}

	public CoursePlanModel(String courseplantitle, String courseplandescr, String createdby, String plancode,
			long courseid) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplandescr = courseplandescr;
		this.createdby = createdby;
		this.plancode = plancode;
		this.courseid = courseid;
	}
	public CoursePlanModel() {
		super();
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
}