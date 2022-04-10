package in.co.srdt.myguruji.model.coursePlan;

public class CourseObjGET
{
	private long id;
    private String title;
    private String code;
    private String descr;
    private String courseid;
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((descr == null) ? 0 : descr.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseObjGET other = (CourseObjGET) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (descr == null) {
			if (other.descr != null)
				return false;
		} else if (!descr.equals(other.descr))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	public CourseObjGET(long id, String title, String code, String descr)
	{
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		this.descr = descr;
	}
	public CourseObjGET() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public CourseObjGET(long id, String title, String code, String descr, String courseid) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		this.descr = descr;
		this.courseid = courseid;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	@Override
	public String toString() {
		return "CourseObjGET [id=" + id + ", title=" + title + ", code=" + code + ", descr=" + descr + ", courseid="
				+ courseid + "]";
	}
	
}