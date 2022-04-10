package in.co.srdt.myguruji.model.coursePlan;

import java.util.List;

public class CoursePlanUnitDetails
{
	private String unittitle;
	private String unitdescr;
	private String createdby;
	
	private String topicsid;
	private String objectivesid;
	private String topics;
	private String objectives;
	
	private long unitid;
	private long courseplanid;
	
	private long docsCount;
	private long vidsCount;
	private long linksCount;
	private long classesCount;
	private long assignmentCount;
		
	private List<CoursePlanSubunitDetails> subUnits;
	
	public long getAssignmentCount() {
		return assignmentCount;
	}
	public void setAssignmentCount(long assignmentCount) {
		this.assignmentCount = assignmentCount;
	}
	public CoursePlanUnitDetails(String unittitle, String unitdescr, String createdby, String topicsid,
			String objectivesid, String topics, String objectives, long unitid, long courseplanid, long docsCount,
			long vidsCount, long linksCount, long classesCount, long assignmentCount,
			List<CoursePlanSubunitDetails> subUnits) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.topicsid = topicsid;
		this.objectivesid = objectivesid;
		this.topics = topics;
		this.objectives = objectives;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
		this.docsCount = docsCount;
		this.vidsCount = vidsCount;
		this.linksCount = linksCount;
		this.classesCount = classesCount;
		this.assignmentCount = assignmentCount;
		this.subUnits = subUnits;
	}
	public CoursePlanUnitDetails(String unittitle, String unitdescr, String createdby, String topicsid,
			String objectivesid, String topics, String objectives, long unitid, long courseplanid, long docsCount,
			long vidsCount, long linksCount, long classesCount, List<CoursePlanSubunitDetails> subUnits) {
		super();
		this.unittitle = unittitle;
		this.unitdescr = unitdescr;
		this.createdby = createdby;
		this.topicsid = topicsid;
		this.objectivesid = objectivesid;
		this.topics = topics;
		this.objectives = objectives;
		this.unitid = unitid;
		this.courseplanid = courseplanid;
		this.docsCount = docsCount;
		this.vidsCount = vidsCount;
		this.subUnits = subUnits;
		this.linksCount = linksCount;
		this.classesCount = classesCount;
	}
	public long getVidsCount() {
		return vidsCount;
	}
	public void setVidsCount(long vidsCount) {
		this.vidsCount = vidsCount;
	}
	public long getDocsCount() {
		return docsCount;
	}

	public void setDocsCount(long docsCount) {
		this.docsCount = docsCount;
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
	public long getUnitid() {
		return unitid;
	}

	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}

	public CoursePlanUnitDetails() {
		super();
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
		this.unitdescr = unitdescr;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getCourseplanid() {
		return courseplanid;
	}

	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}

	public List<CoursePlanSubunitDetails> getSubUnits() {
		return subUnits;
	}

	public void setSubUnits(List<CoursePlanSubunitDetails> subUnits) {
		this.subUnits = subUnits;
	}
	public long getLinksCount() {
		return linksCount;
	}
	public void setLinksCount(long linksCount) {
		this.linksCount = linksCount;
	}
	public long getClassesCount() {
		return classesCount;
	}
	public void setClassesCount(long classesCount) {
		this.classesCount = classesCount;
	}
	@Override
	public String toString() {
		return "CoursePlanUnitDetails [unittitle=" + unittitle + ", unitdescr=" + unitdescr + ", createdby=" + createdby
				+ ", topicsid=" + topicsid + ", objectivesid=" + objectivesid + ", topics=" + topics + ", objectives="
				+ objectives + ", unitid=" + unitid + ", courseplanid=" + courseplanid + ", docsCount=" + docsCount
				+ ", vidsCount=" + vidsCount + ", linksCount=" + linksCount + ", classesCount=" + classesCount
				+ ", subUnits=" + subUnits + "]";
	}
}