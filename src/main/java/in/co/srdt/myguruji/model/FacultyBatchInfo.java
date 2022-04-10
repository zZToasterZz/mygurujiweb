package in.co.srdt.myguruji.model;

public class FacultyBatchInfo {

    private String emplid;
    private String facultyname;
    private String batchcode;
    private String batchdescr;

    public FacultyBatchInfo() {
    }

    public FacultyBatchInfo(String emplid, String facultyname, String batchcode, String batchdescr) {
        this.emplid = emplid;
        this.facultyname = facultyname;
        this.batchcode = batchcode;
        this.batchdescr = batchdescr;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getBatchdescr() {
        return batchdescr;
    }

    public void setBatchdescr(String batchdescr) {
        this.batchdescr = batchdescr;
    }
}
