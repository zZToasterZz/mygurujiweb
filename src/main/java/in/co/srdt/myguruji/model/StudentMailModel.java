package in.co.srdt.myguruji.model;

public class StudentMailModel
{
	private long id;
	private String emailid;
	private String subject;
	private String body;
	
	public StudentMailModel(long id, String emailid, String subject, String body) {
		super();
		this.id = id;
		this.emailid = emailid;
		this.subject = subject;
		this.body = body;
	}
	public StudentMailModel() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
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
	@Override
	public String toString() {
		return "StudentMailModel [id=" + id + ", emailid=" + emailid + ", subject=" + subject + ", body=" + body + "]";
	}
}