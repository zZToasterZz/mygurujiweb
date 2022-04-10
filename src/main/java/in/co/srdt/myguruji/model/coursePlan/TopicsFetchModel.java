package in.co.srdt.myguruji.model.coursePlan;

import java.util.List;

public class TopicsFetchModel
{
	private String courseid;
	private List<String> topicids;
	
	public TopicsFetchModel(String courseid, List<String> topicids) {
		super();
		this.courseid = courseid;
		this.topicids = topicids;
	}

	public TopicsFetchModel() {
		super();
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public List<String> getTopicids() {
		return topicids;
	}

	public void setTopicids(List<String> topicids) {
		this.topicids = topicids;
	}

	@Override
	public String toString() {
		return "TopicsFetchModel [courseid=" + courseid + ", topicids=" + topicids + "]";
	}
}