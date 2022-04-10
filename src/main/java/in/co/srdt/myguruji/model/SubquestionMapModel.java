package in.co.srdt.myguruji.model;

public class SubquestionMapModel
{
	private String sectionid;
	private String questionid;
	private String assessmentquestionid = "0";
	private String marks;
	private String partialmarking = "N";
	private String partialmarks = "0";
	private int sqr;
	
	
	
	public SubquestionMapModel(String sectionid, String questionid, String assessmentquestionid, String marks,
			String partialmarking, String partialmarks, int sqr, String issubques) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.sqr = sqr;
		this.issubques = issubques;
	}
	public int getSqr() {
		return sqr;
	}
	public void setSqr(int sqr) {
		this.sqr = sqr;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getAssessmentquestionid() {
		return assessmentquestionid;
	}
	public void setAssessmentquestionid(String assessmentquestionid) {
		this.assessmentquestionid = assessmentquestionid;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getPartialmarking() {
		return partialmarking;
	}
	public void setPartialmarking(String partialmarking) {
		this.partialmarking = partialmarking;
	}
	public String getPartialmarks() {
		return partialmarks;
	}
	public void setPartialmarks(String partialmarks) {
		this.partialmarks = partialmarks;
	}
	public String getIssubques() {
		return issubques;
	}
	public void setIssubques(String issubques) {
		this.issubques = issubques;
	}
	public SubquestionMapModel() {
		super();
	}
	@Override
	public String toString() {
		return "SubquestionMapModel [sectionid=" + sectionid + ", questionid=" + questionid + ", assessmentquestionid="
				+ assessmentquestionid + ", marks=" + marks + ", partialmarking=" + partialmarking + ", partialmarks="
				+ partialmarks + ", issubques=" + issubques + "]";
	}
	private String issubques = "N";
}