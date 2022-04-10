package in.co.srdt.myguruji.model;

public class PlanByBatchGET
{
	private String courseplantitle;
	private long courseplanid;
	private long batchid;
	private String batchdescr;
	
	public PlanByBatchGET(String courseplantitle, long courseplanid, long batchid, String batchdescr) {
		super();
		this.courseplantitle = courseplantitle;
		this.courseplanid = courseplanid;
		this.batchid = batchid;
		this.batchdescr = batchdescr;
	}
	public PlanByBatchGET(PlanByBatchGET plan) {
		super();
		this.courseplantitle = plan.courseplantitle;
		this.courseplanid = plan.courseplanid;
		this.batchid = plan.batchid;
		this.batchdescr = plan.batchdescr;
	}
	public PlanByBatchGET() {
		super();
	}
	public String getCourseplantitle() {
		return courseplantitle;
	}
	public void setCourseplantitle(String courseplantitle) {
		this.courseplantitle = courseplantitle;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getBatchdescr() {
		return batchdescr;
	}
	public void setBatchdescr(String batchdescr) {
		this.batchdescr = batchdescr;
	}
	@Override
	public String toString() {
		return "PlanByBatchGET [courseplantitle=" + courseplantitle + ", courseplanid=" + courseplanid + ", batchid="
				+ batchid + ", batchdescr=" + batchdescr + "]";
	}
}