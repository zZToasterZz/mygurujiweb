package in.co.srdt.myguruji.model.coursePlan;

public class Unit
{
	private long courseplanid = 0;
	private long unitid = 0;
	private String unittitle;
	private String unitdescr;
	private String topics;
	private String objectives;
	private String topicsid;
	private String objectivesid;

	public Unit(long courseplanid, long unitid, String unittitle, String unitdescr, String topics, String objectives,
				String topicsid, String objectivesid) {
		super();
		this.courseplanid = courseplanid;
		this.unitid = unitid;
		this.unittitle = unittitle;
		//this.unitdescr = unitdescr;
		this.topics = topics;
		this.objectives = objectives;
		this.topicsid = topicsid;
		this.objectivesid = objectivesid;
		
		String dirtyDescr = unitdescr;
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	this.unitdescr = unitdescr;
	}
	public Unit() {
		super();
	}
	@Override
	public String toString() {
		return "Unit [courseplanid=" + courseplanid + ", unitid=" + unitid + ", unittitle=" + unittitle + ", unitdescr="
				+ unitdescr + ", topics=" + topics + ", objectives=" + objectives + ", topicsid=" + topicsid
				+ ", objectivesid=" + objectivesid + "]";
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}
	public String getUnittitle() {
		return unittitle;
	}
	public void setUnittitle(String unittitle) {
		this.unittitle = unittitle;
	}
	public String getUnitdescr() {
		return unitdescr;
	}
	public void setUnitdescr(String unitdescr) {
		String dirtyDescr = unitdescr;
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	this.unitdescr = unitdescr;
	}
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}
	public String getTopicsid() {
		return topicsid;
	}
	public void setTopicsid(String topicsid) {
		this.topicsid = topicsid;
	}
	public String getObjectivesid() {
		return objectivesid;
	}
	public void setObjectivesid(String objectivesid) {
		this.objectivesid = objectivesid;
	}
}