package in.co.srdt.myguruji.model;

public class GradebookSaveMarks {

	private String courseid;
	private String batchid;	
	private String emplid;
	private String pslamtype;
	private String assid;
	private String asstype;
	private String marks;
	private String class_nbr;
	private String strm;
	private String lam_type;
	private String descrshort;
	private String sequence_no;
	private String marks_out_of;
	private String student_grade;
	private String instructor_id;
	
	public GradebookSaveMarks() {
		super();
	}

	public GradebookSaveMarks(String courseid, String batchid, String emplid, String pslamtype, String assid,
			String asstype, String marks) {
		super();
		this.courseid = courseid;
		this.batchid = batchid;
		this.emplid = emplid;
		this.pslamtype = pslamtype;
		this.assid = assid;
		this.asstype = asstype;
		this.marks = marks;
	}
	
	public String getClass_nbr() {
		return class_nbr;
	}

	public void setClass_nbr(String class_nbr) {
		this.class_nbr = class_nbr;
	}

	public String getStrm() {
		return strm;
	}

	public void setStrm(String strm) {
		this.strm = strm;
	}

	public String getLam_type() {
		return lam_type;
	}

	public void setLam_type(String lam_type) {
		this.lam_type = lam_type;
	}

	public String getDescrshort() {
		return descrshort;
	}

	public void setDescrshort(String descrshort) {
		this.descrshort = descrshort;
	}

	public String getSequence_no() {
		return sequence_no;
	}

	public void setSequence_no(String sequence_no) {
		this.sequence_no = sequence_no;
	}

	public String getMarks_out_of() {
		return marks_out_of;
	}

	public void setMarks_out_of(String marks_out_of) {
		this.marks_out_of = marks_out_of;
	}

	public String getStudent_grade() {
		return student_grade;
	}

	public void setStudent_grade(String student_grade) {
		this.student_grade = student_grade;
	}

	public GradebookSaveMarks(String emplid, String pslamtype, String class_nbr, String strm, String lam_type,
			String descrshort, String sequence_no, String marks_out_of, String student_grade, String instructor_id) {
		super();
		this.emplid = emplid;
		this.pslamtype = pslamtype;
		this.class_nbr = class_nbr;
		this.strm = strm;
		this.lam_type = lam_type;
		this.descrshort = descrshort;
		this.sequence_no = sequence_no;
		this.marks_out_of = marks_out_of;
		this.student_grade = student_grade;
		this.instructor_id = instructor_id;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public String getPslamtype() {
		return pslamtype;
	}

	public void setPslamtype(String pslamtype) {
		this.pslamtype = pslamtype;
	}

	public String getAssid() {
		return assid;
	}

	public void setAssid(String assid) {
		this.assid = assid;
	}

	public String getAsstype() {
		return asstype;
	}

	public void setAsstype(String asstype) {
		this.asstype = asstype;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}
	
	public String getInstructor_id() {
		return instructor_id;
	}

	public void setInstructor_id(String instructor_id) {
		this.instructor_id = instructor_id;
	}

	@Override
	public String toString() {
		return "GradebookSaveMarks [courseid=" + courseid + ", batchid=" + batchid + ", emplid=" + emplid
				+ ", pslamtype=" + pslamtype + ", assid=" + assid + ", asstype=" + asstype + ", marks=" + marks
				+ ", class_nbr=" + class_nbr + ", strm=" + strm + ", lam_type=" + lam_type + ", descrshort="
				+ descrshort + ", sequence_no=" + sequence_no + ", marks_out_of=" + marks_out_of + ", student_grade="
				+ student_grade + ", instructor_id=" + instructor_id + "]";
	}
	
}
