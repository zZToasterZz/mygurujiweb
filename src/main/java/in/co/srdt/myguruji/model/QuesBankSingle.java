package in.co.srdt.myguruji.model;

public class QuesBankSingle {

	private String faculty_code;
	private String full_name;
	private String course_descr;
	private String topic_count;
	
	public QuesBankSingle() {
		super();
	}

	public QuesBankSingle(String faculty_code, String full_name, String course_descr, String topic_count) {
		super();
		this.faculty_code = faculty_code;
		this.full_name = full_name;
		this.course_descr = course_descr;
		this.topic_count = topic_count;
	}

	public String getFaculty_code() {
		return faculty_code;
	}

	public void setFaculty_code(String faculty_code) {
		this.faculty_code = faculty_code;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getCourse_descr() {
		return course_descr;
	}

	public void setCourse_descr(String course_descr) {
		this.course_descr = course_descr;
	}

	public String getTopic_count() {
		return topic_count;
	}

	public void setTopic_count(String topic_count) {
		this.topic_count = topic_count;
	}
	
}
