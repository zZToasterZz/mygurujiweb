package in.co.srdt.myguruji.controllers;

public class ContentUnitPostModel
{
	private String courseid;
	private String typeid;
	private String unitid;
	private String title;
	private String descr;
	private String contentpath;
	private String createdby;
	
	public ContentUnitPostModel(String courseid, String typeid, String unitid, String title, String descr, String contentpath,
			String createdby) {
		super();
		this.courseid = courseid;
		this.typeid = typeid;
		this.unitid = unitid;
		this.title = title;
		this.descr = descr;
		this.contentpath = contentpath;
		this.createdby = createdby;
	}
	public ContentUnitPostModel() {
		super();
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getContentpath() {
		return contentpath;
	}
	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	@Override
	public String toString() {
		return "ContentUnitPostModel [courseid=" + courseid + ", typeid=" + typeid + ", unitid=" + unitid + ", title="
				+ title + ", descr=" + descr + ", contentpath=" + contentpath + ", createdby=" + createdby + "]";
	}
}