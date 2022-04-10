package in.co.srdt.myguruji.model;

import java.util.Arrays;

public class LinksModel
{
	private String starturl[];
	private String descr[];
	private String title[];
	private String createdby[];
	private long courseid[];
	private long unitid[];
	private String types[];
	
	public LinksModel(String[] starturl, String[] descr, String[] title, String[] createdby, long[] courseid,
			long[] unitid, String[] types) {
		super();
		this.starturl = starturl;
		this.descr = descr;
		this.title = title;
		this.createdby = createdby;
		this.courseid = courseid;
		this.unitid = unitid;
		this.types = types;
	}
	public LinksModel() {
		super();
	}
	public String[] getStarturl() {
		return starturl;
	}
	public void setStarturl(String[] starturl) {
		this.starturl = starturl;
	}
	public String[] getDescr() {
		return descr;
	}
	public void setDescr(String[] descr) {
		this.descr = descr;
	}
	public String[] getTitle() {
		return title;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public String[] getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String[] createdby) {
		this.createdby = createdby;
	}
	public long[] getCourseid() {
		return courseid;
	}
	public void setCourseid(long[] courseid) {
		this.courseid = courseid;
	}
	public long[] getUnitid() {
		return unitid;
	}
	public void setUnitid(long[] unitid) {
		this.unitid = unitid;
	}
	@Override
	public String toString() {
		return "LinksModel [starturl=" + Arrays.toString(starturl) + ", descr=" + Arrays.toString(descr) + ", title="
				+ Arrays.toString(title) + ", createdby=" + Arrays.toString(createdby) + ", courseid="
				+ Arrays.toString(courseid) + ", unitid=" + Arrays.toString(unitid) + "]";
	}
}