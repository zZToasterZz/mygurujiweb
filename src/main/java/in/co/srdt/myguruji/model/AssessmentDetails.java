package in.co.srdt.myguruji.model;

public class AssessmentDetails
{
	private long assessmentid;
    private long courseid;
    private long templateid;
    private String title;
    private String code;
    private String descr;
    private String type;
    private String createdby;
    
	public AssessmentDetails() {
		
	}
	public AssessmentDetails(long assessmentid, long courseid, long templateid, String title, String code, String descr,
			String type, String createdby) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.descr = descr;
		this.type = type;
		this.createdby = createdby;
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

	public long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(long templateid) {
		this.templateid = templateid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		return "AssessmentDetails [assessmentid=" + assessmentid + ", courseid=" + courseid + ", templateid="
				+ templateid + ", title=" + title + ", code=" + code + ", descr=" + descr + ", type=" + type
				+ ", createdby=" + createdby + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}