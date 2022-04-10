package in.co.srdt.myguruji.model.coursePlan;

public class CoursePlanGet
{
	private String id;
	private String title;
	private String code;
	private String descr;
	
	public CoursePlanGet(String id, String title, String code, String descr) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		this.descr = descr;
	}
	public CoursePlanGet() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
}