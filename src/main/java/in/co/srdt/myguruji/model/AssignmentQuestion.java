package in.co.srdt.myguruji.model;

import java.util.List;

public class AssignmentQuestion
{
	private long sectionid;
	private long questionid;
	private long assessmentquestionid;
	private String marks;
	private String artialmarking;
	private String partialmarks;
	private String obtainedMarks;
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
    private String blmtaxonomy;
    private String difficulty;
    private String topic;
    private AssignmentResponseGET assignresponse;
	List<AssignmentAttachment> attachments;
	
	public AssignmentQuestion() {
		super();
	}

	public AssignmentQuestion(long sectionid, long questionid, long assessmentquestionid, String marks,
			String artialmarking, String partialmarks, String obtainedMarks, String questext, String qtype,
			String issubques, String opt1, String opt2, String opt3, String opt4, String subquestions,
			String quesresponce, String correctresponse, String blmtaxonomy, String difficulty, String topic,
			AssignmentResponseGET assignresponse, List<AssignmentAttachment> attachments) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.artialmarking = artialmarking;
		this.partialmarks = partialmarks;
		this.obtainedMarks = obtainedMarks;
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
		this.blmtaxonomy = blmtaxonomy;
		this.difficulty = difficulty;
		this.topic = topic;
		this.assignresponse = assignresponse;
		this.attachments = attachments;
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

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getArtialmarking() {
		return artialmarking;
	}

	public void setArtialmarking(String artialmarking) {
		this.artialmarking = artialmarking;
	}

	public String getPartialmarks() {
		return partialmarks;
	}

	public void setPartialmarks(String partialmarks) {
		this.partialmarks = partialmarks;
	}

	public String getObtainedMarks() {
		return obtainedMarks;
	}

	public void setObtainedMarks(String obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
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

	public String getCorrectresponse() {
		return correctresponse;
	}

	public void setCorrectresponse(String correctresponse) {
		this.correctresponse = correctresponse;
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public AssignmentResponseGET getAssignresponse() {
		return assignresponse;
	}

	public void setAssignresponse(AssignmentResponseGET assignresponse) {
		this.assignresponse = assignresponse;
	}

	public List<AssignmentAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AssignmentAttachment> attachments) {
		this.attachments = attachments;
	}
}