package in.co.srdt.myguruji.model;

public class AssessmentByCourseIdGET
{
	private long assessmentid;
	private long courseid;
	private long templateid;
	private String title;
	private String code;
	private String assessmendescr;
	private String createdby;
	private String coursedescr;
	private String templatedescr;
	private String descr;
	private String schedulestatus;
	private String type;
	private String createdon;
	private String coursetitle;
	private String coursecode;
	
	public AssessmentByCourseIdGET() {
		super();
	}

	public AssessmentByCourseIdGET(long assessmentid, long courseid, long templateid, String title, String code,
			String assessmendescr, String createdby, String coursedescr, String templatedescr, String descr,
			String schedulestatus, String type, String createdon, String coursetitle, String coursecode) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.createdby = createdby;
		this.coursedescr = coursedescr;
		this.templatedescr = templatedescr;
		this.descr = descr;
		this.schedulestatus = schedulestatus;
		this.type = type;
		this.createdon = createdon;
		this.coursetitle = coursetitle;
		this.coursecode = coursecode;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public AssessmentByCourseIdGET(long assessmentid, long courseid, long templateid, String title, String code,
			String assessmendescr, String createdby, String coursedescr, String templatedescr, String descr,
			String schedulestatus, String type, String createdon) {
		super();
		this.assessmentid = assessmentid;
		this.courseid = courseid;
		this.templateid = templateid;
		this.title = title;
		this.code = code;
		this.assessmendescr = assessmendescr;
		this.createdby = createdby;
		this.coursedescr = coursedescr;
		this.templatedescr = templatedescr;
		this.descr = descr;
		this.schedulestatus = schedulestatus;
		this.type = type;
		this.createdon = createdon;
	}

	public long getAssessmentid() {
		return assessmentid;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
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

	public String getAssessmendescr() {
		return assessmendescr;
	}

	public void setAssessmendescr(String assessmendescr) {
		this.assessmendescr = assessmendescr;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCoursedescr() {
		return coursedescr;
	}

	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
	}

	public String getTemplatedescr() {
		return templatedescr;
	}

	public void setTemplatedescr(String templatedescr) {
		this.templatedescr = templatedescr;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getSchedulestatus() {
		return schedulestatus;
	}

	public void setSchedulestatus(String schedulestatus) {
		this.schedulestatus = schedulestatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AssessmentByCourseIdGET [assessmentid=" + assessmentid + ", courseid=" + courseid + ", templateid="
				+ templateid + ", title=" + title + ", code=" + code + ", assessmendescr=" + assessmendescr
				+ ", createdby=" + createdby + ", coursedescr=" + coursedescr + ", templatedescr=" + templatedescr
				+ ", descr=" + descr + ", schedulestatus=" + schedulestatus + ", type=" + type + "]";
	}
	
}