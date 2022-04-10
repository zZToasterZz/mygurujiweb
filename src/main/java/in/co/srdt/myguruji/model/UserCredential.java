package in.co.srdt.myguruji.model;

public class UserCredential {
	private String loginid;
	private String pwd;
	public UserCredential() {
		super();
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
