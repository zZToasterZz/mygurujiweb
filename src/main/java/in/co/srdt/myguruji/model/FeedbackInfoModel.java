package in.co.srdt.myguruji.model;

public class FeedbackInfoModel
{
	private String feedbackgradeparentid;
	private String feedbacktypename;
	private String feedbackparentpath;
	private String feedbackparent;
	private String createdby;
	
	public FeedbackInfoModel() {
		super();
	}
	public FeedbackInfoModel(String feedbackgradeparentid, String feedbacktypename, String feedbackparentpath,
			String feedbackparent, String createdby) {
		super();
		this.feedbackgradeparentid = feedbackgradeparentid;
		this.feedbacktypename = feedbacktypename;
		this.feedbackparentpath = feedbackparentpath;
		this.feedbackparent = feedbackparent;
		this.createdby = createdby;
	}
	public String getFeedbackgradeparentid() {
		return feedbackgradeparentid;
	}
	public void setFeedbackgradeparentid(String feedbackgradeparentid) {
		this.feedbackgradeparentid = feedbackgradeparentid;
	}
	public String getFeedbacktypename() {
		return feedbacktypename;
	}
	public void setFeedbacktypename(String feedbacktypename) {
		this.feedbacktypename = feedbacktypename;
	}
	public String getFeedbackparentpath() {
		return feedbackparentpath;
	}
	public void setFeedbackparentpath(String feedbackparentpath) {
		this.feedbackparentpath = feedbackparentpath;
	}
	public String getFeedbackparent() {
		return feedbackparent;
	}
	public void setFeedbackparent(String feedbackparent) {
		this.feedbackparent = feedbackparent;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	@Override
	public String toString() {
		return "FeedbackInfoModel [feedbackgradeparentid=" + feedbackgradeparentid + ", feedbacktypename="
				+ feedbacktypename + ", feedbackparentpath=" + feedbackparentpath + ", feedbackparent=" + feedbackparent
				+ ", createdby=" + createdby + "]";
	}
}