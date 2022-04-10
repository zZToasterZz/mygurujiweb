package in.co.srdt.myguruji.model;

public class QuestionDetails {
	private long questionid;
	private long courseid;
	private long topicid;
	private long difficultyid;
	private long typeid;
    private String questiontext;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    private String opt5;
    private String opt6;
    private String currectopt;
    private String createdby;
    private String file;
    private String blmtaxonomy;
    private String courseobj;
    private String referid;
    private String topictitle;
    private String coursetitle;
    private String difficultytitle;
    private String typetitle;
    
	public QuestionDetails() {

	}

	public QuestionDetails(long questionid, long courseid, long topicid, long difficultyid, long typeid,
			String questiontext, String opt1, String opt2, String opt3, String opt4, String opt5, String opt6,
			String currectopt, String createdby, String file, String blmtaxonomy, String courseobj, String referid,
			String topictitle, String coursetitle, String difficultytitle, String typetitle) {
		super();
		this.questionid = questionid;
		this.courseid = courseid;
		this.topicid = topicid;
		this.difficultyid = difficultyid;
		this.typeid = typeid;
		this.questiontext = questiontext;
		this.opt1 = opt1;
		this.opt2 = opt2;
		this.opt3 = opt3;
		this.opt4 = opt4;
		this.opt5 = opt5;
		this.opt6 = opt6;
		this.currectopt = currectopt;
		this.createdby = createdby;
		this.file = file;
		this.blmtaxonomy = blmtaxonomy;
		this.courseobj = courseobj;
		this.referid = referid;
		this.topictitle = topictitle;
		this.coursetitle = coursetitle;
		this.difficultytitle = difficultytitle;
		this.typetitle = typetitle;
	}
	
	public String getBlmtaxonomy() {
		return blmtaxonomy;
	}

	public void setBlmtaxonomy(String blmtaxonomy) {
		this.blmtaxonomy = blmtaxonomy;
	}

	public String getCourseobj() {
		return courseobj;
	}

	public void setCourseobj(String courseobj) {
		this.courseobj = courseobj;
	}

	public String getReferid() {
		return referid;
	}

	public void setReferid(String referid) {
		this.referid = referid;
	}

	public String getTopictitle() {
		return topictitle;
	}

	public void setTopictitle(String topictitle) {
		this.topictitle = topictitle;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getDifficultytitle() {
		return difficultytitle;
	}

	public void setDifficultytitle(String difficultytitle) {
		this.difficultytitle = difficultytitle;
	}

	public String getTypetitle() {
		return typetitle;
	}

	public void setTypetitle(String typetitle) {
		this.typetitle = typetitle;
	}

	public long getQuestionid() {
		return questionid;
	}

	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public long getTopicid() {
		return topicid;
	}

	public void setTopicid(long topicid) {
		this.topicid = topicid;
	}

	public long getDifficultyid() {
		return difficultyid;
	}

	public void setDifficultyid(long difficultyid) {
		this.difficultyid = difficultyid;
	}

	public long getTypeid() {
		return typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
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

	public String getOpt5() {
		return opt5;
	}

	public void setOpt5(String opt5) {
		this.opt5 = opt5;
	}

	public String getOpt6() {
		return opt6;
	}

	public void setOpt6(String opt6) {
		this.opt6 = opt6;
	}

	public String getCurrectopt() {
		return currectopt;
	}

	public void setCurrectopt(String currectopt) {
		this.currectopt = currectopt;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "QuestionDetails [questionid=" + questionid + ", courseid=" + courseid + ", topicid=" + topicid
				+ ", difficultyid=" + difficultyid + ", typeid=" + typeid + ", questiontext=" + questiontext + ", opt1="
				+ opt1 + ", opt2=" + opt2 + ", opt3=" + opt3 + ", opt4=" + opt4 + ", opt5=" + opt5 + ", opt6=" + opt6
				+ ", currectopt=" + currectopt + ", createdby=" + createdby + ", file=" + file + ", blmtaxonomy="
				+ blmtaxonomy + ", courseobj=" + courseobj + ", referid=" + referid + ", topictitle=" + topictitle
				+ ", coursetitle=" + coursetitle + ", difficultytitle=" + difficultytitle + ", typetitle=" + typetitle
				+ "]";
	}
	
}
