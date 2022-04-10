package in.co.srdt.myguruji.model.coursePlan;

public class CoursePlanSearch
{
	private long planid;
	private String courseid;
	private String plancode;
	private String plantitle;
	private String emplid;
	private String coursecode;
	private String coursetitle;
	
	public CoursePlanSearch(long planid, String courseid, String plancode, String plantitle, String emplid,
			String coursecode, String coursetitle) {
		super();
		this.planid = planid;
		this.courseid = courseid;
		this.plancode = plancode;
		this.plantitle = plantitle;
		this.emplid = emplid;
		this.coursecode = coursecode;
		this.coursetitle = coursetitle;
	}

	public CoursePlanSearch() {
		super();
	}

	public long getPlanid() {
		return planid;
	}

	public void setPlanid(long planid) {
		this.planid = planid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getPlancode() {
		return plancode;
	}

	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}

	public String getPlantitle() {
		return plantitle;
	}

	public void setPlantitle(String plantitle) {
		this.plantitle = plantitle;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
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
}