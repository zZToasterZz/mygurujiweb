package in.co.srdt.myguruji.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Login {
	private String loginid;
	private String pwd;
	private String access_tocken;
	private long id;
	private String emplid;
	private String emailid;
	private String isactive;
	private String createdby;
	private String usertype;
	
	public Login() {
		super();
	}

	public String getAccess_tocken() {
		return access_tocken;
	}

	public void setAccess_tocken(String access_tocken) {
		this.access_tocken = access_tocken;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "Login [loginid=" + loginid + ", pwd=" + pwd + ", access_tocken=" + access_tocken + ", id=" + id
				+ ", emplid=" + emplid + ", emailid=" + emailid + ", isactive=" + isactive + ", createdby=" + createdby
				+ ", usertype=" + usertype + "]";
	}
	
}
