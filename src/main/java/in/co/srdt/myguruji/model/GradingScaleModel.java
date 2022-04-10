package in.co.srdt.myguruji.model;

public class GradingScaleModel
{
	private String gradingscaleid;
	private String gradingname;
	private String gradingpoint;
	private String gradingpointvalue;
	private String createdby;
	
	private String lowestgradevalue;
	private String lowergradevalue;
	private String avggradevalue;
	private String highergradevalue;
	private String highestgradevalue;
	
	public GradingScaleModel(String gradingscaleid, String gradingname, String gradingpoint, String gradingpointvalue,
			String createdby, String lowestgradevalue, String lowergradevalue, String avggradevalue,
			String highergradevalue, String highestgradevalue) {
		super();
		this.gradingscaleid = gradingscaleid;
		this.gradingname = gradingname;
		this.gradingpoint = gradingpoint;
		this.gradingpointvalue = gradingpointvalue;
		this.createdby = createdby;
		this.lowestgradevalue = lowestgradevalue;
		this.lowergradevalue = lowergradevalue;
		this.avggradevalue = avggradevalue;
		this.highergradevalue = highergradevalue;
		this.highestgradevalue = highestgradevalue;
	}

	public GradingScaleModel() {
		super();
	}

	public String getGradingscaleid() {
		return gradingscaleid;
	}

	public void setGradingscaleid(String gradingscaleid) {
		this.gradingscaleid = gradingscaleid;
	}

	public String getGradingname() {
		return gradingname;
	}

	public void setGradingname(String gradingname) {
		this.gradingname = gradingname;
	}

	public String getGradingpoint() {
		return gradingpoint;
	}

	public void setGradingpoint(String gradingpoint) {
		this.gradingpoint = gradingpoint;
	}

	public String getGradingpointvalue() {
		return gradingpointvalue;
	}

	public void setGradingpointvalue(String gradingpointvalue) {
		this.gradingpointvalue = gradingpointvalue;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getLowestgradevalue() {
		return lowestgradevalue;
	}

	public void setLowestgradevalue(String lowestgradevalue) {
		this.lowestgradevalue = lowestgradevalue;
	}

	public String getLowergradevalue() {
		return lowergradevalue;
	}

	public void setLowergradevalue(String lowergradevalue) {
		this.lowergradevalue = lowergradevalue;
	}

	public String getAvggradevalue() {
		return avggradevalue;
	}

	public void setAvggradevalue(String avggradevalue) {
		this.avggradevalue = avggradevalue;
	}

	public String getHighergradevalue() {
		return highergradevalue;
	}

	public void setHighergradevalue(String highergradevalue) {
		this.highergradevalue = highergradevalue;
	}

	public String getHighestgradevalue() {
		return highestgradevalue;
	}

	public void setHighestgradevalue(String highestgradevalue) {
		this.highestgradevalue = highestgradevalue;
	}

	@Override
	public String toString() {
		return "GradingScaleModel [gradingscaleid=" + gradingscaleid + ", gradingname=" + gradingname
				+ ", gradingpoint=" + gradingpoint + ", gradingpointvalue=" + gradingpointvalue + ", createdby="
				+ createdby + ", lowestgradevalue=" + lowestgradevalue + ", lowergradevalue=" + lowergradevalue
				+ ", avggradevalue=" + avggradevalue + ", highergradevalue=" + highergradevalue + ", highestgradevalue="
				+ highestgradevalue + "]";
	}
}