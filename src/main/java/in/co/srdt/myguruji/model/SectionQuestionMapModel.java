package in.co.srdt.myguruji.model;

import java.util.List;

public class SectionQuestionMapModel
{
	private String sectionid;
	private String questionid;
	private String assessmentquestionid;
	private String marks;
	private String partialmarking;
	private String partialmarks;
	private String issubques;
	private String questext;
	private int sqr;
	private List<SubquestionMapModel> subquestions;
	
	
	
	public SectionQuestionMapModel(String sectionid, String questionid, String assessmentquestionid, String marks,
			String partialmarking, String partialmarks, String issubques, String questext, int sqr,
			List<SubquestionMapModel> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.issubques = issubques;
		this.questext = questext;
		this.sqr = sqr;
		this.subquestions = subquestions;
	}
	public int getSqr() {
		return sqr;
	}
	public void setSqr(int sqr) {
		this.sqr = sqr;
	}
	public List<SubquestionMapModel> getSubquestions() {
		return subquestions;
	}
	public void setSubquestions(List<SubquestionMapModel> subquestions) {
		this.subquestions = subquestions;
	}
	public SectionQuestionMapModel() {
		super();
	}
	public String getIssubques() {
		return issubques;
	}
	public void setIssubques(String issubques) {
		this.issubques = issubques;
	}
	public String getQuestext() {
		return questext;
	}
	public void setQuestext(String questext) {
		this.questext = questext;
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
	public void setPartialmarking(String artialmarking) {
		this.partialmarking = artialmarking;
	}
	public String getPartialmarks() {
		return partialmarks;
	}
	public void setPartialmarks(String partialmarks) {
		this.partialmarks = partialmarks;
	}
	@Override
	public String toString() {
		return "SectionQuestionMapModel [sectionid=" + sectionid + ", questionid=" + questionid
				+ ", assessmentquestionid=" + assessmentquestionid + ", marks=" + marks + ", partialmarking="
				+ partialmarking + ", partialmarks=" + partialmarks + ", issubques=" + issubques + ", questext="
				+ questext + ", subquestions=" + subquestions + "]";
	}
}