package in.co.srdt.myguruji.model;

public class QuestionCount {
	private long courseid;
    private long topicid;
    private long typeid;
    private String coursecode;
    private String questiontype;
    private String topicdescr;
    private long count;
    private String coursedescr;
   
    
	public QuestionCount() {

	}
	
	
	public QuestionCount(long courseid, long topicid, long typeid, String coursecode, String questiontype,
			String topicdescr, long count, String coursedescr) {
		this.courseid = courseid;
		this.topicid = topicid;
		this.typeid = typeid;
		this.coursecode = coursecode;
		this.questiontype = questiontype;
		this.topicdescr = topicdescr;
		this.count = count;
		this.coursedescr = coursedescr;
	}


	public String getCoursedescr() {
		return coursedescr;
	}


	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
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
	public long getTypeid() {
		return typeid;
	}
	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getQuestiontype() {
		return questiontype;
	}
	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}
	public String getTopicdescr() {
		return topicdescr;
	}
	public void setTopicdescr(String topicdescr) {
		this.topicdescr = topicdescr;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
