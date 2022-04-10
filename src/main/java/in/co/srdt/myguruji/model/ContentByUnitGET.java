package in.co.srdt.myguruji.model;

public class ContentByUnitGET
{
	private long contentid;
	private String title;
    private String descr;
    private String contentpath;
    private long unitid;
    private long courseid;
    private String createdby;
    private String contenttype;
    private long typeid;
    
	public long getContentid() {
		return contentid;
	}
	public void setContentid(long contentid) {
		this.contentid = contentid;
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
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public long getTypeid() {
		return typeid;
	}
	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}
	public ContentByUnitGET(long contentid, String title, String descr, String contentpath, long unitid, long courseid,
			String createdby, String contenttype, long typeid) {
		super();
		this.contentid = contentid;
		this.title = title;
		this.descr = descr;
		this.contentpath = contentpath;
		this.unitid = unitid;
		this.courseid = courseid;
		this.createdby = createdby;
		this.contenttype = contenttype;
		this.typeid = typeid;
	}
	@Override
	public String toString() {
		return "ContentByUnitGET [contentid=" + contentid + ", title=" + title + ", descr=" + descr + ", contentpath="
				+ contentpath + ", unitid=" + unitid + ", courseid=" + courseid + ", createdby=" + createdby
				+ ", contenttype=" + contenttype + ", typeid=" + typeid + "]";
	}
}