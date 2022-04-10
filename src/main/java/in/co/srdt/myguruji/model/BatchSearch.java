package in.co.srdt.myguruji.model;

/*
 * By : Shantanu Srivastava
 */
public class BatchSearch
{
	@Override
	public String toString() {
		return "BatchSearch [id=" + id + ", code=" + code + ", title=" + title + "]";
	}
	private String id;
	private String code;
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BatchSearch(String id, String code, String title) {
		super();
		this.id = id;
		this.code = code;
		this.title = title;
	}
	public BatchSearch() {
		super();
	}
	
}