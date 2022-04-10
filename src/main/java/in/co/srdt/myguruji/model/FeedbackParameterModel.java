package in.co.srdt.myguruji.model;

public class FeedbackParameterModel {

    private long feedbackparameterid;
    private long feedbackparentid;
    private String feedbackchildid;
    private String feedbackquestion;
    private String createdby;
    private FeedbackInfoModel feedbackParameterchild;
    
    public FeedbackParameterModel() {
    }
    public FeedbackParameterModel(long feedbackparameterid, long feedbackparentid, String feedbackchildid, String feedbackquestion, String createdby) {
        this.feedbackparameterid = feedbackparameterid;
        this.feedbackparentid = feedbackparentid;
        this.feedbackchildid = feedbackchildid;
        this.feedbackquestion = feedbackquestion;
        this.createdby = createdby;
    }

    public FeedbackInfoModel getFeedbackParameterchild() {
		return feedbackParameterchild;
	}
	public void setFeedbackParameterchild(FeedbackInfoModel feedbackParameterchild) {
		this.feedbackParameterchild = feedbackParameterchild;
	}
	public FeedbackParameterModel(long feedbackparameterid, long feedbackparentid, String feedbackchildid,
			String feedbackquestion, String createdby, FeedbackInfoModel feedbackParameterchild) {
		super();
		this.feedbackparameterid = feedbackparameterid;
		this.feedbackparentid = feedbackparentid;
		this.feedbackchildid = feedbackchildid;
		this.feedbackquestion = feedbackquestion;
		this.createdby = createdby;
		this.feedbackParameterchild = feedbackParameterchild;
	}
	public long getFeedbackparameterid() {
        return feedbackparameterid;
    }

    public void setFeedbackparameterid(long feedbackparameterid) {
        this.feedbackparameterid = feedbackparameterid;
    }

    public long getFeedbackparentid() {
        return feedbackparentid;
    }

    public void setFeedbackparentid(long feedbackparentid) {
        this.feedbackparentid = feedbackparentid;
    }

    public String getFeedbackchildid() {
        return feedbackchildid;
    }

    public void setFeedbackchildid(String feedbackchildid) {
        this.feedbackchildid = feedbackchildid;
    }

    public String getFeedbackquestion() {
        return feedbackquestion;
    }

    public void setFeedbackquestion(String feedbackquestion) {
        this.feedbackquestion = feedbackquestion;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

	@Override
	public String toString() {
		return "FeedbackParameterModel [feedbackparameterid=" + feedbackparameterid + ", feedbackparentid="
				+ feedbackparentid + ", feedbackchildid=" + feedbackchildid + ", feedbackquestion=" + feedbackquestion
				+ ", createdby=" + createdby + "]";
	}
}