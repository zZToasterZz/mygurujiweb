package in.co.srdt.myguruji.model;

public class AssessmentStrategyModel
{
	private long strategyid;
	private String strategy;
	private long assessmentid;
	public AssessmentStrategyModel(long strategyid, String strategy, long assessmentid) {
		super();
		this.strategyid = strategyid;
		this.strategy = strategy;
		this.assessmentid = assessmentid;
	}
	public AssessmentStrategyModel() {
		super();
	}
	public long getStrategyid() {
		return strategyid;
	}
	public void setStrategyid(long strategyid) {
		this.strategyid = strategyid;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
}