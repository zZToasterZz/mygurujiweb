package in.co.srdt.myguruji.model.coursePlan;

public class UnitsGET
{
	private long id;
	private String title;
	private String code;
	private String descr;

	public UnitsGET(long id, String title, String code, String descr) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		this.descr = descr;
	}
	@Override
	public String toString() {
		return "UnitsGET [id=" + id + ", title=" + title + ", code=" + code + ", descr=" + descr + "]";
	}
	public UnitsGET() {
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
}