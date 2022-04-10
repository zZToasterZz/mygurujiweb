package in.co.srdt.myguruji.model.coursePlan;

public class TopicsModel
{
	private long topicid;
	private long courseid;
	private long batchid;
	private String topictitle;
	private String topicdescr;
	private String createdby;
	
	public TopicsModel(long topicid, long courseid, long batchid, String topictitle, String topicdescr,
                       String createdby) {
		this.topicid = topicid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.topictitle = topictitle;
		this.topicdescr = topicdescr;
		this.createdby = createdby;
	}
	public TopicsModel() {
	}
	public long getTopicid() {
		return topicid;
	}
	public void setTopicid(long topicid) {
		this.topicid = topicid;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getTopictitle() {
		return topictitle;
	}
	public void setTopictitle(String topictitle) {
		this.topictitle = topictitle;
	}
	public String getTopicdescr() {
		return topicdescr;
	}
	public void setTopicdescr(String topicdescr) {
		this.topicdescr = topicdescr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
}