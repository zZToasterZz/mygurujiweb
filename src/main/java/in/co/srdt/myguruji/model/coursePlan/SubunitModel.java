package in.co.srdt.myguruji.model.coursePlan;

public class SubunitModel
{
	private long unitid;
	private String subunittitle;
	private String subunitdescr;
	private String createdby;
	
	public SubunitModel(long unitid, String subunittitle, String subunitdescr, String createdby) {
		super();
		this.unitid = unitid;
		this.subunittitle = subunittitle;
		this.subunitdescr = subunitdescr;
		this.createdby = createdby;
	}
	public SubunitModel() {
		super();
	}
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
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
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	@Override
	public String toString() {
		return "SubunitModel [unitid=" + unitid + ", subunittitle=" + subunittitle + ", subunitdescr=" + subunitdescr
				+ ", createdby=" + createdby + "]";
	}
}