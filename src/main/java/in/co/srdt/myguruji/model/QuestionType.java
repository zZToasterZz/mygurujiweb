package in.co.srdt.myguruji.model;

public class QuestionType {
	private long id;
	private String title;
	private String code;
	private String descr;
	
	public QuestionType() {
		
	}

	public QuestionType(long id, String title, String code, String descr) {
		this.id = id;
		this.title = title;
		this.code = code;
		this.descr = descr;
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
