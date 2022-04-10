package in.co.srdt.myguruji.model;

import java.math.BigDecimal;
import java.util.List;

public class AddedQuestion {
	private long sectionid;
	private long questionid;
	private long assessmentquestionid;
	private BigDecimal marks;
	private String partialmarking = "N";
	private BigDecimal partialmarks;
	private double obtainedMarks;
	private String questext;
	private String qtype;
	private String issubques;
	private String opt1;
	private String opt2;
	private String opt3;
	private String opt4;
	private List<AddedQuestion> subquestions;
	private String quesresponce;
	private String correctresponse;
	private String blmtaxonomy;
	private String difficulty;
	private String topic;
	
	public AddedQuestion() {
		super();
	}

	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks, double obtainedMarks, String questext, String qtype,
			String issubques, String opt1, String opt2, String opt3, String opt4, List<AddedQuestion> subquestions,
			String quesresponce, String correctresponse, String blmtaxonomy, String difficulty, String topic) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
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
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
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

	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
	}
	
	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks, double obtainedMarks, String questext, String qtype,
			String issubques, String opt1, String opt2, String opt3, String opt4, List<AddedQuestion> subquestions,
			String quesresponce, String correctresponse) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
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

	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks, String questext, String qtype, String issubques,
			String opt1, String opt2, String opt3, String opt4, List<AddedQuestion> subquestions, String quesresponce,
			String correctresponse) {
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
	}

	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks,String qtext,String qtype) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = qtext;
		this.qtype = qtype;
	}
	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks,String qtext,String qtype,String issubques) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = qtext;
		this.qtype = qtype;
		this.issubques = issubques;
	}
	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks,String qtext,String qtype,String issubques,List<AddedQuestion> subquestions) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = qtext;
		this.qtype = qtype;
		this.issubques = issubques;
		this.subquestions = subquestions;
	}
	public AddedQuestion(long sectionid, long questionid, long assessmentquestionid, BigDecimal marks,
			String partialmarking, BigDecimal partialmarks,String qtext,String qtype,
			String opt1,String opt2,String opt3,String opt4) {
		super();
		this.sectionid = sectionid;
		this.questionid = questionid;
		this.assessmentquestionid = assessmentquestionid;
		this.marks = marks;
		this.partialmarking = partialmarking;
		this.partialmarks = partialmarks;
		this.questext = qtext;
		this.qtype = qtype;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
	}
	
	public String getQuesresponce() {
		return quesresponce != null ? quesresponce : "";
	}

	public void setQuesresponce(String quesresponce) {
		this.quesresponce = quesresponce;
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
	public List<AddedQuestion> getSubquestions() {
		return subquestions;
	}
	public void setSubquestions(List<AddedQuestion> subquestions) {
		this.subquestions = subquestions;
	}
	public String getIssubques() {
		return issubques;
	}
	public void setIssubques(String issubques) {
		this.issubques = issubques;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getQuestext() {
		return questext;
	}
	public void setQuestext(String questext) {
		this.questext = questext;
	}
	public BigDecimal getMarks() {
		return marks;
	}
	public void setMarks(BigDecimal marks) {
		this.marks = marks;
	}
	public String getPartialmarking() {
		return partialmarking;
	}
	public void setPartialmarking(String partialmarking) {
		this.partialmarking = partialmarking;
	}
	public BigDecimal getPartialmarks() {
		return partialmarks;
	}
	public void setPartialmarks(BigDecimal partialmarks) {
		this.partialmarks = partialmarks;
	}
	public long getAssessmentquestionid() {
		return assessmentquestionid;
	}
	public void setAssessmentquestionid(long assessmentquestionid) {
		this.assessmentquestionid = assessmentquestionid;
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
}
