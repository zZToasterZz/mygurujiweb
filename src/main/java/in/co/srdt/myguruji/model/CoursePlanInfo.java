package in.co.srdt.myguruji.model;

public class CoursePlanInfo {

    private String planid;
    private String plantitle;
    private String coursecode;
    private String createdby;
    private String questionstatus;
    private String batchcode;
    private String plancode;

    public CoursePlanInfo() {
    }

    public CoursePlanInfo(String planid, String plantitle, String coursecode, String createdby, String questionstatus, String batchcode, String plancode) {
        this.planid = planid;
        this.plantitle = plantitle;
        this.coursecode = coursecode;
        this.createdby = createdby;
        this.questionstatus = questionstatus;
        this.batchcode = batchcode;
        this.plancode = plancode;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getPlantitle() {
        return plantitle;
    }

    public void setPlantitle(String plantitle) {
        this.plantitle = plantitle;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getQuestionstatus() {
        return questionstatus;
    }

    public void setQuestionstatus(String questionstatus) {
        this.questionstatus = questionstatus;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }
}
