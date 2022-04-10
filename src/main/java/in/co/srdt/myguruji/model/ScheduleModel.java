package in.co.srdt.myguruji.model;

import java.util.List;

public class ScheduleModel
{
	private long scheduledid;
	private long courseid;
	private long assessmentid;
	private String startdate;
    private String enddate;
    private String startdatetime;
    private String enddatetime;
    private String descr;
    private String createdby;
    private List<Long> batchids;
    private long duration;
    private String comment;
    private String responsestatus;
    
	public ScheduleModel(long scheduledid, long courseid, long assessmentid, String startdate, String enddate,
			String startdatetime, String enddatetime, String descr, String createdby, List<Long> batchids,
			long duration, String comment, String responsestatus) {
		super();
		this.scheduledid = scheduledid;
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.descr = descr;
		this.createdby = createdby;
		this.batchids = batchids;
		this.duration = duration;
		this.comment = comment;
		this.responsestatus = responsestatus;
	}
	public String getResponsestatus() {
		return responsestatus;
	}
	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}
	public ScheduleModel(long scheduledid, long courseid, long assessmentid, String startdate, String enddate,
			String startdatetime, String enddatetime, String descr, String createdby, List<Long> batchids,
			long duration, String comment) {
		super();
		this.scheduledid = scheduledid;
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.descr = descr;
		this.createdby = createdby;
		this.batchids = batchids;
		this.duration = duration;
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ScheduleModel [scheduledid=" + scheduledid + ", courseid=" + courseid + ", assessmentid=" + assessmentid
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", startdatetime=" + startdatetime
				+ ", enddatetime=" + enddatetime + ", descr=" + descr + ", createdby=" + createdby + ", batchids="
				+ batchids + ", duration=" + duration + "]";
	}
	public ScheduleModel(long scheduledid, long courseid, long assessmentid, String startdate, String enddate,
			String startdatetime, String enddatetime, String descr, String createdby, List<Long> batchids,
			long duration) {
		super();
		this.scheduledid = (scheduledid == 0)? 0 : scheduledid;
		this.courseid = courseid;
		this.assessmentid = assessmentid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.descr = descr;
		this.createdby = createdby;
		this.batchids = batchids;
		this.duration = duration;
	}
	public long getScheduledid() {
		return scheduledid;
	}
	public void setScheduledid(long scheduledid) {
		this.scheduledid = scheduledid;
	}
	public ScheduleModel() {
		super();
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	public String getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public List<Long> getBatchids() {
		return batchids;
	}
	public void setBatchids(List<Long> batchids) {
		this.batchids = batchids;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}