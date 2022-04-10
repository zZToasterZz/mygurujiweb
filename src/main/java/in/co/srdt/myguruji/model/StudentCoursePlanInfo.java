package in.co.srdt.myguruji.model;

public class StudentCoursePlanInfo {

    private String classnumber;
    private String coursecode;
    private String coursedescr;
    private String batchtype;
    private String facultyname;
    private String courseplanstatus;
    private String batchtitle;

    public StudentCoursePlanInfo() {
    }

	public StudentCoursePlanInfo(String classnumber, String coursecode, String coursedescr, String batchtype,
			String facultyname, String courseplanstatus, String batchtitle) {
		super();
		this.classnumber = classnumber;
		this.coursecode = coursecode;
		this.coursedescr = coursedescr;
		this.batchtype = batchtype;
		this.facultyname = facultyname;
		this.courseplanstatus = courseplanstatus;
		this.batchtitle = batchtitle;
	}

	public String getClassnumber() {
		return classnumber;
	}

	public void setClassnumber(String classnumber) {
		this.classnumber = classnumber;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursedescr() {
		return coursedescr;
	}

	public void setCoursedescr(String coursedescr) {
		this.coursedescr = coursedescr;
	}

	public String getBatchtype() {
		return batchtype;
	}

	public void setBatchtype(String batchtype) {
		this.batchtype = batchtype;
	}

	public String getFacultyname() {
		return facultyname;
	}

	public void setFacultyname(String facultyname) {
		this.facultyname = facultyname;
	}

	public String getCourseplanstatus() {
		return courseplanstatus;
	}

	public void setCourseplanstatus(String courseplanstatus) {
		this.courseplanstatus = courseplanstatus;
	}

	public String getBatchtitle() {
		return batchtitle;
	}

	public void setBatchtitle(String batchtitle) {
		this.batchtitle = batchtitle;
	}
}
