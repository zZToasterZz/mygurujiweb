package in.co.srdt.myguruji.model;

public class AssignmentUnit {
	
	private long courseid;
	private long batchid;
	private long assignmentid;
	private long courseplanid;
	private long assignmentunitid=0;
	private String topics;
	private String topicsids;
	private long unitid;
	
	public AssignmentUnit() {
		super();
	}

	public AssignmentUnit(long courseid, long batchid, long assignmentid, long courseplanid, long assignmentunitid,
			String topics, String topicsids, long unitid) {
		super();
		this.courseid = courseid;
		this.batchid = batchid;
		this.assignmentid = assignmentid;
		this.courseplanid = courseplanid;
		this.assignmentunitid = assignmentunitid;
		this.topics = topics;
		this.topicsids = topicsids;
		this.unitid = unitid;
	}

	public long getUnitid() {
		return unitid;
	}

	public void setUnitid(long unitid) {
		this.unitid = unitid;
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

	public long getAssignmentid() {
		return assignmentid;
	}

	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
	}

	public long getCourseplanid() {
		return courseplanid;
	}

	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public long getAssignmentunitid() {
		return assignmentunitid;
	}

	public void setAssignmentunitid(long assignmentunitid) {
		this.assignmentunitid = assignmentunitid;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getTopicsids() {
		return topicsids;
	}

	public void setTopicsids(String topicsids) {
		this.topicsids = topicsids;
	}

	@Override
	public String toString() {
		return "AssignmentUnit [courseid=" + courseid + ", batchid=" + batchid + ", assignmentid=" + assignmentid
				+ ", courseplanid=" + courseplanid + ", assignmentunitid=" + assignmentunitid + ", topics=" + topics
				+ ", topicsids=" + topicsids + ", unitid=" + unitid + "]";
	}

}
