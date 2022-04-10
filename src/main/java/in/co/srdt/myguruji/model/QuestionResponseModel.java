package in.co.srdt.myguruji.model;

public class QuestionResponseModel
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
    private String subquestions;
    private String quesresponce;
    private String correctresponse;
    private double obtainedMarks;
    private String blmtaxonomy;
    private String difficulty;
    
	public QuestionResponseModel(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques, String opt1,
			String opt2, String opt3, String opt4, String subquestions, String quesresponce, String correctresponse,
			double obtainedMarks, String blmtaxonomy, String difficulty) {
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
		this.quesresponce = quesresponce;
		this.correctresponse = correctresponse;
		this.obtainedMarks = obtainedMarks;
		this.blmtaxonomy = blmtaxonomy;
		this.difficulty = difficulty;
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
	public QuestionResponseModel(long sectionid, long questionid, long assessmentquestionid, double marks,
			String partialmarking, double partialmarks, String questext, String qtype, String issubques, String opt1,
			String opt2, String opt3, String opt4, String subquestions, String quesresponce, String correctresponse,
			double obtainedMarks) {
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
		this.quesresponce = quesresponce;
		this.correctresponse = correctresponse;
		this.obtainedMarks = obtainedMarks;
	}
	public double getObtainedMarks() {
		return obtainedMarks;
	}
	public void setObtainedMarks(double obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}
	public String getCorrectresponse() {
		return correctresponse;
	}
	public void setCorrectresponse(String correctresponse) {
		this.correctresponse = correctresponse;
	}
	public QuestionResponseModel() {
		super();
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
	public String getSubquestions() {
		return subquestions;
	}
	public void setSubquestions(String subquestions) {
		this.subquestions = subquestions;
	}
	public String getQuesresponce() {
		return quesresponce;
	}
	public void setQuesresponce(String quesresponce) {
		this.quesresponce = quesresponce;
	}
	@Override
	public String toString() {
		return "QuestionResponseModel [sectionid=" + sectionid + ", questionid=" + questionid
				+ ", assessmentquestionid=" + assessmentquestionid + ", marks=" + marks + ", partialmarking="
				+ partialmarking + ", partialmarks=" + partialmarks + ", questext=" + questext + ", qtype=" + qtype
				+ ", issubques=" + issubques + ", opt1=" + opt1 + ", opt2=" + opt2 + ", opt3=" + opt3 + ", opt4=" + opt4
				+ ", subquestions=" + subquestions + ", quesresponce=" + quesresponce + ", correctresponse="
				+ correctresponse + ", obtainedMarks=" + obtainedMarks + "]";
	}
}