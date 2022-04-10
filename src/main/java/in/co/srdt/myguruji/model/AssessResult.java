package in.co.srdt.myguruji.model;

public class AssessResult
{
	private long studentid;
    private String firstname;
    private String lastname;
    private String loginid;
    private String password;
    private String emplid;
    private String emailid;
    private String primarycontact;
    private String course;
    private double optmarks;
    private long assessmentid;
    private String assesstitle;
    private long batchid;
    private String batchcode;
    private String batchsection;
    private String attendance;
    private String submission;
    
	public AssessResult(long studentid, String firstname, String lastname, String loginid, String password,
			String emplid, String emailid, String primarycontact, String course, double optmarks, long assessmentid,
			String assesstitle, long batchid, String batchcode, String batchsection, String attendance,
			String submission) {
		super();
		this.studentid = studentid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.loginid = loginid;
		this.password = password;
		this.emplid = emplid;
		this.emailid = emailid;
		this.primarycontact = primarycontact;
		this.course = course;
		this.optmarks = optmarks;
		this.assessmentid = assessmentid;
		this.assesstitle = assesstitle;
		this.batchid = batchid;
		this.batchcode = batchcode;
		this.batchsection = batchsection;
		this.attendance = attendance;
		this.submission = submission;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getSubmission() {
		return submission;
	}
	public void setSubmission(String submission) {
		this.submission = submission;
	}
	public AssessResult() {
		super();
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPrimarycontact() {
		return primarycontact;
	}
	public void setPrimarycontact(String primarycontact) {
		this.primarycontact = primarycontact;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public double getOptmarks() {
		return optmarks;
	}
	public void setOptmarks(double optmarks) {
		this.optmarks = optmarks;
	}
	public long getAssessmentid() {
		return assessmentid;
	}
	public void setAssessmentid(long assessmentid) {
		this.assessmentid = assessmentid;
	}
	public String getAssesstitle() {
		return assesstitle;
	}
	public void setAssesstitle(String assesstitle) {
		this.assesstitle = assesstitle;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getBatchsection() {
		return batchsection;
	}
	public void setBatchsection(String batchsection) {
		this.batchsection = batchsection;
	}
}