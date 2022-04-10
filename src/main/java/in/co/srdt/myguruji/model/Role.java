package in.co.srdt.myguruji.model;

public class Role {
	private long roleId;
	private String rolename;
	private String createdby;
	private String isactive;
	
	public Role() {
		
	}
	
	public Role(String rolename, String createdby) {
		this.rolename = rolename;
		this.createdby = createdby;
	}
	
	public Role(long roleId, String rolename, String createdby, String isactive) {
		this.roleId = roleId;
		this.rolename = rolename;
		this.createdby = createdby;
		this.isactive = isactive;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", rolename=" + rolename + ", createdby=" + createdby + ", isactive="
				+ isactive + "]";
	}
}
