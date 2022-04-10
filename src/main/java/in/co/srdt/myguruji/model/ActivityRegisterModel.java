package in.co.srdt.myguruji.model;

public class ActivityRegisterModel {
	private String faculty_code;
	private String fullname;
	private String course_code;
	private String course_descr;
	private String batch_code;
	private String batch_type;
	private String rn;
	private String unit_title;
	private String docs_count;
	private String vids_count;
	private String links_count;
	private String students_count;
	private String total;
	
	public ActivityRegisterModel() {
		super();
	}

	public ActivityRegisterModel(String faculty_code, String fullname, String course_code, String course_descr,
			String batch_code, String batch_type, String rn, String unit_title, String docs_count, String vids_count,
			String links_count, String students_count, String total) {
		super();
		this.faculty_code = faculty_code;
		this.fullname = fullname;
		this.course_code = course_code;
		this.course_descr = course_descr;
		this.batch_code = batch_code;
		this.batch_type = batch_type;
		this.rn = rn;
		this.unit_title = unit_title;
		this.docs_count = docs_count;
		this.vids_count = vids_count;
		this.links_count = links_count;
		this.students_count = students_count;
		this.total = total;
	}


	public ActivityRegisterModel(String faculty_code, String fullname, String course_code, String course_descr, String batch_code,
			String batch_type, String rn, String unit_title, String docs_count, String vids_count, String links_count,
			String students_count) {
		super();
		this.faculty_code = faculty_code;
		this.fullname = fullname;
		this.course_code = course_code;
		this.course_descr = course_descr;
		this.batch_code = batch_code;
		this.batch_type = batch_type;
		this.rn = rn;
		this.unit_title = unit_title;
		this.docs_count = docs_count;
		this.vids_count = vids_count;
		this.links_count = links_count;
		this.students_count = students_count;
	}

	public String getFaculty_code() {
		return faculty_code;
	}

	public void setFaculty_code(String faculty_code) {
		this.faculty_code = faculty_code;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_descr() {
		return course_descr;
	}

	public void setCourse_descr(String course_descr) {
		this.course_descr = course_descr;
	}

	public String getBatch_code() {
		return batch_code;
	}

	public void setBatch_code(String batch_code) {
		this.batch_code = batch_code;
	}

	public String getBatch_type() {
		return batch_type;
	}

	public void setBatch_type(String batch_type) {
		this.batch_type = batch_type;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getUnit_title() {
		return unit_title;
	}

	public void setUnit_title(String unit_title) {
		this.unit_title = unit_title;
	}

	public String getDocs_count() {
		return docs_count;
	}

	public void setDocs_count(String docs_count) {
		this.docs_count = docs_count;
	}

	public String getVids_count() {
		return vids_count;
	}

	public void setVids_count(String vids_count) {
		this.vids_count = vids_count;
	}

	public String getLinks_count() {
		return links_count;
	}

	public void setLinks_count(String links_count) {
		this.links_count = links_count;
	}

	public String getStudents_count() {
		return students_count;
	}

	public void setStudents_count(String students_count) {
		this.students_count = students_count;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
}
