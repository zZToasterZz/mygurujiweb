package in.co.srdt.myguruji.model;

public class QuestionsGetParamModel
{
		private String courseid;
		private String topicid;
		private String difficultyid;
		private String typeid;
		
		public QuestionsGetParamModel(String courseid, String topicid, String difficultyid, String typeid) {
			super();
			this.courseid = courseid;
			this.topicid = topicid;
			this.difficultyid = difficultyid;
			this.typeid = typeid;
		}

		public QuestionsGetParamModel() {
			super();
		}

		@Override
		public String toString() {
			return "QuestionsGetParamModel [courseid=" + courseid + ", topicid=" + topicid + ", difficultyid="
					+ difficultyid + ", typeid=" + typeid + "]";
		}

		public String getCourseid() {
			return courseid;
		}

		public void setCourseid(String courseid) {
			this.courseid = courseid;
		}

		public String getTopicid() {
			return topicid;
		}

		public void setTopicid(String topicid) {
			this.topicid = topicid;
		}

		public String getDifficultyid() {
			return difficultyid;
		}

		public void setDifficultyid(String difficultyid) {
			this.difficultyid = difficultyid;
		}

		public String getTypeid() {
			return typeid;
		}

		public void setTypeid(String typeid) {
			this.typeid = typeid;
		}
		
}