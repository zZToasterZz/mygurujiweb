package in.co.srdt.myguruji.model.coursePlan;

public class CoursePlanSubunitDetails
{
	private String subunittitle;
	private String subunitdescr;
	private long subunitid;
	private String createdby;
	private long unitid;
	
	public CoursePlanSubunitDetails() {
		super();
	}

	public CoursePlanSubunitDetails(String subunittitle, String subunitdescr, long subunitid, String createdby,
			long unitid) {
		super();
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.subunitid = subunitid;
		this.createdby = createdby;
		this.unitid = unitid;
	}

	public String getSubunittitle() {
		return subunittitle;
	}

	public void setSubunittitle(String subunittitle) {
		this.subunittitle = subunittitle;
	}

	public String getSubunitdescr() {
		return subunitdescr;
	}

	public void setSubunitdescr(String subunitdescr) {
		this.subunitdescr = subunitdescr;
	}

	public long getSubunitid() {
		return subunitid;
	}

	public void setSubunitid(long subunitid) {
		this.subunitid = subunitid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getUnitid() {
		return unitid;
	}

	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}
}