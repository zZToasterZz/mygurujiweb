package in.co.srdt.myguruji.model;

public class UserLoginSync {
	
	private String loginid;
	private String emplid;
	private String emailid;
	private String createdby;
	private String usertype;
	
	public UserLoginSync() {
		super();
	}

	public UserLoginSync(String loginid, String emplid, String emailid, String createdby, String usertype) {
		super();
		this.loginid = loginid;
		this.emplid = emplid;
		this.emailid = emailid;
		this.createdby = createdby;
		this.usertype = usertype;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "UserLoginSync [loginid=" + loginid + ", emplid=" + emplid + ", emailid=" + emailid + ", createdby="
				+ createdby + ", usertype=" + usertype + "]";
	}

}
