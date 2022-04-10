package in.co.srdt.myguruji.model;

public class SchedulesByBatch {
    private long assessmentid;
    private long courseid;
    private String assessmentitle;
    private String assessmencode;
    private String assessmendescr;
    private String coursecode;
    private String coursedescr;
    private String startdate;
    private String enddate;
    private String startdatetime;
    private String enddatetime;
    private int duration;
    private int maxmarks;
    private String sectionDetails;
    private String studentcount;
    private String isActive;
    private String createdby;
    private String batchcodes;
    private String batchid;

    public SchedulesByBatch() {
    }

    public SchedulesByBatch(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String sectionDetails,
			String studentcount, String isActive, String createdby, String batchcodes) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.sectionDetails = sectionDetails;
		this.studentcount = studentcount;
		this.isActive = isActive;
		this.createdby = createdby;
		this.batchcodes = batchcodes;
	}

	public String getBatchcodes() {
		return batchcodes;
	}

	public void setBatchcodes(String batchcodes) {
		this.batchcodes = batchcodes;
	}

	@Override
	public String toString() {
		return "SchedulesByBatch [assessmentid=" + assessmentid + ", courseid=" + courseid + ", assessmentitle="
				+ assessmentitle + ", assessmencode=" + assessmencode + ", assessmendescr=" + assessmendescr
				+ ", coursecode=" + coursecode + ", coursedescr=" + coursedescr + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", startdatetime=" + startdatetime + ", enddatetime=" + enddatetime
				+ ", duration=" + duration + ", maxmarks=" + maxmarks + ", sectionDetails=" + sectionDetails
				+ ", studentcount=" + studentcount + ", isActive=" + isActive + ", createdby=" + createdby + "]";
	}

	public SchedulesByBatch(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String sectionDetails,
			String studentcount, String isActive, String createdby) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.sectionDetails = sectionDetails;
		this.studentcount = studentcount;
		this.isActive = isActive;
		this.createdby = createdby;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	public SchedulesByBatch(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String sectionDetails,
			String studentcount, String isActive, String createdby, String batchcodes, String batchid) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.sectionDetails = sectionDetails;
		this.studentcount = studentcount;
		this.isActive = isActive;
		this.createdby = createdby;
		this.batchcodes = batchcodes;
		this.batchid = batchid;
	}

	public SchedulesByBatch(long assessmentid, long courseid, String assessmentitle, String assessmencode,
			String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate,
			String startdatetime, String enddatetime, int duration, int maxmarks, String sectionDetails,
			String studentcount, String isActive) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.assessmentitle = assessmentitle;
		this.assessmencode = assessmencode;
		this.assessmendescr = assessmendescr;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startdatetime = startdatetime;
		this.enddatetime = enddatetime;
		this.duration = duration;
		this.maxmarks = maxmarks;
		this.sectionDetails = sectionDetails;
		this.studentcount = studentcount;
		this.isActive = isActive;
	}

	public String getStudentcount() {
		return studentcount;
	}

	public void setStudentcount(String studentcount) {
		this.studentcount = studentcount;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public SchedulesByBatch(long assessmentid, long courseid, String assessmentitle, String assessmencode, String assessmendescr, String coursecode, String coursedescr, String startdate, String enddate, String startdatetime, String enddatetime, int duration, int maxmarks, String sectionDetails) {
        this.assessmentid = assessmentid;
        this.courseid = courseid;
        this.assessmentitle = assessmentitle;
        this.assessmencode = assessmencode;
        this.assessmendescr = assessmendescr;
        this.coursecode = coursecode;
        this.coursedescr = coursedescr;
        this.startdate = startdate;
        this.enddate = enddate;
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
        this.duration = duration;
        this.maxmarks = maxmarks;
        this.sectionDetails = sectionDetails;
    }

    public long getAssessmentid() {
        return assessmentid;
    }

    public void setAssessmentid(long assessmentid) {
        this.assessmentid = assessmentid;
    }

    public long getCourseid() {
        return courseid;
    }

    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    public String getAssessmentitle() {
        return assessmentitle;
    }

    public void setAssessmentitle(String assessmentitle) {
        this.assessmentitle = assessmentitle;
    }

    public String getAssessmencode() {
        return assessmencode;
    }

    public void setAssessmencode(String assessmencode) {
        this.assessmencode = assessmencode;
    }

    public String getAssessmendescr() {
        return assessmendescr;
    }

    public void setAssessmendescr(String assessmendescr) {
        this.assessmendescr = assessmendescr;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursedescr() {
        return coursedescr;
    }

    public void setCoursedescr(String coursedescr) {
        this.coursedescr = coursedescr;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxmarks() {
        return maxmarks;
    }

    public void setMaxmarks(int maxmarks) {
        this.maxmarks = maxmarks;
    }

    public String getSectionDetails() {
        return sectionDetails;
    }

    public void setSectionDetails(String sectionDetails) {
        this.sectionDetails = sectionDetails;
    }

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

}
