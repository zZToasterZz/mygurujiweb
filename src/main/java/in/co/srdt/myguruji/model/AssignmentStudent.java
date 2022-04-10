package in.co.srdt.myguruji.model;

public class AssignmentStudent {
	private long assignmentstudentmapid;
	private long assignmentplanmapid;
	private long assignmentid;
	private long courseid;
	private long batchid;
	private long courseplanid;
	private String emplid;
	private String createdby;
	private String ispublished;
	
	public AssignmentStudent() {
		super();
	}

	public AssignmentStudent(long assignmentstudentmapid, long assignmentplanmapid, long assignmentid, long courseid,
			long batchid, long courseplanid) {
		super();
		this.assignmentstudentmapid = assignmentstudentmapid;
		this.assignmentplanmapid = assignmentplanmapid;
		this.assignmentid = assignmentid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.courseplanid = courseplanid;
	}
	
	public AssignmentStudent(long assignmentstudentmapid, long assignmentplanmapid, long assignmentid, long courseid,
			long batchid, long courseplanid, String emplid, String createdby) {
		super();
		this.assignmentstudentmapid = assignmentstudentmapid;
		this.assignmentplanmapid = assignmentplanmapid;
		this.assignmentid = assignmentid;
		this.courseid = courseid;
		this.batchid = batchid;
		this.courseplanid = courseplanid;
		this.emplid = emplid;
		setCreatedby(createdby);
	}

	public long getAssignmentstudentmapid() {
		return assignmentstudentmapid;
	}

	public void setAssignmentstudentmapid(long assignmentstudentmapid) {
		this.assignmentstudentmapid = assignmentstudentmapid;
	}

	public long getAssignmentplanmapid() {
		return assignmentplanmapid;
	}

	public void setAssignmentplanmapid(long assignmentplanmapid) {
		this.assignmentplanmapid = assignmentplanmapid;
	}

	public long getAssignmentid() {
		return assignmentid;
	}

	public void setAssignmentid(long assignmentid) {
		this.assignmentid = assignmentid;
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

	public long getCourseplanid() {
		return courseplanid;
	}

	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public String getIspublished() {
		return ispublished;
	}


	public void setIspublished(String ispublished) {
		this.ispublished = ispublished;
	}

	@Override
	public String toString() {
		return "AssignmentStudent [assignmentstudentmapid=" + assignmentstudentmapid + ", assignmentplanmapid="
				+ assignmentplanmapid + ", assignmentid=" + assignmentid + ", courseid=" + courseid + ", batchid="
				+ batchid + ", courseplanid=" + courseplanid + ", emplid=" + emplid + ", createdby=" + createdby
				+ ", ispublished=" + ispublished + "]";
	}	
}
