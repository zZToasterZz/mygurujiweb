package in.co.srdt.myguruji.model;

import java.util.List;

public class GetQuestionDetails
{
	private long sectionid;
	private long questionid;
	private long assessmentquestionid;
	private double marks;
	private String partialmarking;
	private double partialmarks;
	private String questext;
	private String qtype;
	private String issubques;
	private String opt1;
	private String opt2;
	private String opt3;
	private String opt4;
	private String blmtaxonomy;
	private String difficulty;
	private String correctresponse;
	private List<GetQuestionDetails> subquestions;
	
	public GetQuestionDetails(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques, String opt1,
			String opt2, String opt3, String opt4, String blmtaxonomy, String difficulty, String correctresponse,
			List<GetQuestionDetails> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = questext;
		this.qtype = qtype;
		this.issubques = issubques;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.blmtaxonomy = blmtaxonomy;
		this.difficulty = difficulty;
		this.correctresponse = correctresponse;
		this.subquestions = subquestions;
	}
	public String getCorrectresponse() {
		return correctresponse;
	}
	public void setCorrectresponse(String correctresponse) {
		this.correctresponse = correctresponse;
	}
	public GetQuestionDetails(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques, String opt1,
			String opt2, String opt3, String opt4, String blmtaxonomy, String difficulty,
			List<GetQuestionDetails> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = questext;
		this.qtype = qtype;
		this.issubques = issubques;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.blmtaxonomy = blmtaxonomy;
		this.difficulty = difficulty;
		this.subquestions = subquestions;
	}
	public String getBlmtaxonomy() {
		return blmtaxonomy;
	}
	public void setBlmtaxonomy(String blmtaxonomy) {
		this.blmtaxonomy = blmtaxonomy;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public GetQuestionDetails(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques, String opt1,
			String opt2, String opt3, String opt4, List<GetQuestionDetails> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = questext;
		this.qtype = qtype;
		this.issubques = issubques;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.subquestions = subquestions;
	}
	public String getOpt1() {
		return opt1;
	}
	public void setOpt1(String opt1) {
		this.opt1 = opt1;
	}
	public String getOpt2() {
		return opt2;
	}
	public void setOpt2(String opt2) {
		this.opt2 = opt2;
	}
	public String getOpt3() {
		return opt3;
	}
	public void setOpt3(String opt3) {
		this.opt3 = opt3;
	}
	public String getOpt4() {
		return opt4;
	}
	public void setOpt4(String opt4) {
		this.opt4 = opt4;
	}
	@Override
	public String toString() {
		return "GetQuestionDetails [sectionid=" + sectionid + ", questionid=" + questionid + ", assessmentquestionid="
				+ assessmentquestionid + ", marks=" + marks + ", partialmarking=" + partialmarking + ", partialmarks="
				+ partialmarks + ", questext=" + questext + ", qtype=" + qtype + ", issubques=" + issubques
				+ ", subquestions=" + subquestions + "]";
	}
	public long getSectionid() {
		return sectionid;
	}
	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public long getAssessmentquestionid() {
		return assessmentquestionid;
	}
	public void setAssessmentquestionid(long assessmentquestionid) {
		this.assessmentquestionid = assessmentquestionid;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public String getPartialmarking() {
		return partialmarking;
	}
	public void setPartialmarking(String partialmarking) {
		this.partialmarking = partialmarking;
	}
	public double getPartialmarks() {
		return partialmarks;
	}
	public void setPartialmarks(double partialmarks) {
		this.partialmarks = partialmarks;
	}
	public String getQuestext() {
		return questext;
	}
	public void setQuestext(String questext) {
		this.questext = questext;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getIssubques() {
		return issubques;
	}
	public void setIssubques(String issubques) {
		this.issubques = issubques;
	}
	public List<GetQuestionDetails> getSubquestions() {
		return subquestions;
	}
	public void setSubquestions(List<GetQuestionDetails> subquestions) {
		this.subquestions = subquestions;
	}
	public GetQuestionDetails(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques,
			List<GetQuestionDetails> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = questext;
		this.qtype = qtype;
		this.issubques = issubques;
		this.subquestions = subquestions;
	}
	public GetQuestionDetails() {
		super();
	}
}