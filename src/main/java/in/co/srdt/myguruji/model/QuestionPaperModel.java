package in.co.srdt.myguruji.model;

import java.util.List;

public class QuestionPaperModel
{
	private long courseid;
    private String assessmentid;
    private String templateid;
    private String strategy;
    private List<SectionQuestionMapModel> add;
    
	public QuestionPaperModel(long courseid, String assessmentid, String templateid, String strategy,
			List<SectionQuestionMapModel> add) {
		super();
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.templateid = templateid;
		this.strategy = strategy;
		this.add = add;
	}

	public QuestionPaperModel() {
		super();
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public String getAssessmentid() {
		return assessmentid;
	}

	public void setAssessmentid(String assessmentid) {
		this.assessmentid = assessmentid;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public List<SectionQuestionMapModel> getAdd() {
		return add;
	}

	public void setAdd(List<SectionQuestionMapModel> add) {
		this.add = add;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	@Override
	public String toString() {
		return "QuestionPaperModel [courseid=" + courseid + ", assessmentid=" + assessmentid + ", templateid="
				+ templateid + ", add=" + add + "]";
	}
}