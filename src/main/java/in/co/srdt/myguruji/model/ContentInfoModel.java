package in.co.srdt.myguruji.model;

import java.util.Arrays;

public class ContentInfoModel
{
	String location;
	String courseid;
	String courseplanid;
	String unitid;
	String level;
	String typeid;
	String title[];
	String descr[];
	
	public ContentInfoModel(String location, String courseid, String courseplanid, String unitid, String level,
			String typeid, String[] title, String[] descr) {
		super();
		this.location = location;
		this.courseid = courseid;
		this.courseplanid = courseplanid;
		this.unitid = unitid;
		this.level = level;
		this.typeid = typeid;
		this.title = title;
		this.descr = descr;
	}
	public ContentInfoModel() {
		super();
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(String courseplanid) {
		this.courseplanid = courseplanid;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public String[] getDescr() {
		return descr;
	}
	public void setDescr(String[] descr) {
		this.descr = descr;
	}
	@Override
	public String toString() {
		return "ContentInfoModel [location=" + location + ", courseid=" + courseid + ", courseplanid=" + courseplanid
				+ ", unitid=" + unitid + ", level=" + level + ", typeid=" + typeid + ", title=" + Arrays.toString(title)
				+ ", descr=" + Arrays.toString(descr) + "]";
	}
}