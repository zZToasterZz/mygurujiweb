package in.co.srdt.myguruji.model;

public class ActivityRegisterSingle {

	private String faculty_code;
	private String full_name;
	private String crse_descr;
	private String crse_title;
	private String batch_codes;
	private String batch_types;
	private String student_count;
	
	public ActivityRegisterSingle() {
		super();
	}

	public ActivityRegisterSingle(String faculty_code, String full_name, String crse_descr, String crse_title,
			String batch_codes, String batch_types, String student_count) {
		super();
		this.faculty_code = faculty_code;
		this.full_name = full_name;
		this.crse_descr = crse_descr;
		this.crse_title = crse_title;
		this.batch_codes = batch_codes;
		this.batch_types = batch_types;
		this.student_count = student_count;
	}
	
	public String getCrse_title() {
		return crse_title;
	}

	public void setCrse_title(String crse_title) {
		this.crse_title = crse_title;
	}

	public String getCrse_descr() {
		return crse_descr;
	}

	public void setCrse_descr(String crse_descr) {
		this.crse_descr = crse_descr;
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

	public String getBatch_codes() {
		return batch_codes;
	}

	public void setBatch_codes(String batch_codes) {
		this.batch_codes = batch_codes;
	}

	public String getBatch_types() {
		return batch_types;
	}

	public void setBatch_types(String batch_types) {
		this.batch_types = batch_types;
	}

	public String getStudent_count() {
		return student_count;
	}

	public void setStudent_count(String student_count) {
		this.student_count = student_count;
	}

	@Override
	public String toString() {
		return "ActivityRegisterSingle [faculty_code=" + faculty_code + ", full_name=" + full_name + ", crse_descr="
				+ crse_descr + ", batch_codes=" + batch_codes + ", batch_types=" + batch_types + ", student_count="
				+ student_count + "]";
	}
	
}
