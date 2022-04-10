package in.co.srdt.myguruji.model;

public class AnnounceBatchModel
{
	String batchid;
	String subject;
	String body;
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public AnnounceBatchModel(String batchid, String subject, String body) {
		super();
		this.batchid = batchid;
		this.subject = subject;
		this.body = body;
	}
	public AnnounceBatchModel() {
		super();
	}
}