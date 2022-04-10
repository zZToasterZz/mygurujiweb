package in.co.srdt.myguruji.model;

public class Topic {
	
	private long topicid;
	private long courseid;
	private long batchid;
	private String topictitle;
	private String topicdescr;
	private String createdby;
	private String sysunitid;
	private long outcomeid;
	private String isactive;

	public Topic() {
		super();
	}
	public Topic(long courseid, String topictitle, String topicdescr, String createdby,String sysunitid) {
		super();
		this.courseid = courseid;
		this.topictitle = topictitle;
		this.topicdescr = topicdescr;
		this.createdby = createdby;
		this.sysunitid = sysunitid;
	}
	public Topic(long topicid, long courseid, String topictitle, String topicdescr, String createdby,String sysunitid) {
		super();
		this.topicid = topicid;
		this.courseid = courseid;
		this.topictitle = topictitle;
		this.topicdescr = topicdescr;
		this.createdby = createdby;
	}
	
	public String getSysunitid() {
		return sysunitid;
	}
	public void setSysunitid(String sysunitid) {
		this.sysunitid = sysunitid;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
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

	public long getOutcomeid() {
		return outcomeid;
	}

	public void setOutcomeid(long outcomeid) {
		this.outcomeid = outcomeid;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	@Override
	public String toString() {
		return "Topic [topicid=" + topicid + ", courseid=" + courseid + ", batchid=" + batchid + ", topictitle="
				+ topictitle + ", topicdescr=" + topicdescr + ", createdby=" + createdby + ", sysunitid=" + sysunitid
				+ ", outcomeid=" + outcomeid + ", isactive=" + isactive + "]";
	}
}
