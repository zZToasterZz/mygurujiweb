package in.co.srdt.myguruji.model;

public class GradebookResponseModel {

    private long gradbookid;
    private String batchid;
    private String courseid;
    private String pslamtype;
    private String assid;
    private String asstype;
    private double marks;
    private String emplid;
    private String freezestatus;

    public GradebookResponseModel() {
    }

	public GradebookResponseModel(long gradbookid, String batchid, String courseid, String pslamtype, String assid,
			String asstype, double marks, String emplid, String freezestatus) {
		super();
		this.gradbookid = gradbookid;
		this.batchid = batchid;
		this.courseid = courseid;
		this.pslamtype = pslamtype;
		this.assid = assid;
		this.asstype = asstype;
		this.marks = marks;
		this.emplid = emplid;
		this.freezestatus = freezestatus;
	}

	public long getGradbookid() {
		return gradbookid;
	}

	public void setGradbookid(long gradbookid) {
		this.gradbookid = gradbookid;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
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

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public String getFreezestatus() {
		return freezestatus;
	}

	public void setFreezestatus(String freezestatus) {
		this.freezestatus = freezestatus;
	}

	@Override
	public String toString() {
		return "GradebookResponseModel [gradbookid=" + gradbookid + ", batchid=" + batchid + ", courseid=" + courseid
				+ ", pslamtype=" + pslamtype + ", assid=" + assid + ", asstype=" + asstype + ", marks=" + marks
				+ ", emplid=" + emplid + ", freezestatus=" + freezestatus + "]";
	}

}
