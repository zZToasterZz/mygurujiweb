package in.co.srdt.myguruji.model;

import java.util.List;

public class GetQuestionPaperDetails
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
	private String evaluationstrategy;
	List<GetSectionDetails> sectiondetails;

	public String getEvaluationstrategy() {
		return evaluationstrategy;
	}

	public void setEvaluationstrategy(String evaluationstrategy) {
		this.evaluationstrategy = evaluationstrategy;
	}

	public GetQuestionPaperDetails(long assessmentid, long courseid, long templateid, String title, String code,
			String assessmendescr, String createdby, String coursedescr, String templatedescr,
			String evaluationstrategy, List<GetSectionDetails> sectiondetails) {
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
		this.evaluationstrategy = evaluationstrategy;
		this.sectiondetails = sectiondetails;
	}

	@Override
	public String toString() {
		return "GetQuestionPaperDetails [assessmentid=" + assessmentid + ", courseid=" + courseid + ", templateid="
				+ templateid + ", title=" + title + ", code=" + code + ", assessmendescr=" + assessmendescr
				+ ", createdby=" + createdby + ", coursedescr=" + coursedescr + ", templatedescr=" + templatedescr
				+ ", evaluationstrategy=" + evaluationstrategy + ", sectiondetails=" + sectiondetails + "]";
	}

	public GetQuestionPaperDetails() {
		super();
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

	public List<GetSectionDetails> getSectiondetails() {
		return sectiondetails;
	}

	public void setSectiondetails(List<GetSectionDetails> sectiondetails) {
		this.sectiondetails = sectiondetails;
	}
}