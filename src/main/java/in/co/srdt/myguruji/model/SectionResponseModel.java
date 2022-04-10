package in.co.srdt.myguruji.model;

public class SectionResponseModel
{
	private long sectionid;
	private String title;
    private String sectionmarks;
    private String sectionnote;
    private String totalquestion;
    private String attemptquestion;
    private String descr;
    private QuestionResponseModel[] questions;
    
	public SectionResponseModel(long sectionid, String title, String sectionmarks, String sectionnote,
			String totalquestion, String attemptquestion, String descr, QuestionResponseModel[] questions) {
		super();
		this.sectionid = sectionid;
		this.title = title;
		this.sectionmarks = sectionmarks;
		this.sectionnote = sectionnote;
		this.totalquestion = totalquestion;
		this.attemptquestion = attemptquestion;
		this.descr = descr;
		this.questions = questions;
	}
	public SectionResponseModel() {
		super();
	}
	public long getSectionid() {
		return sectionid;
	}
	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSectionmarks() {
		return sectionmarks;
	}
	public void setSectionmarks(String sectionmarks) {
		this.sectionmarks = sectionmarks;
	}
	public String getSectionnote() {
		return sectionnote;
	}
	public void setSectionnote(String sectionnote) {
		this.sectionnote = sectionnote;
	}
	public String getTotalquestion() {
		return totalquestion;
	}
	public void setTotalquestion(String totalquestion) {
		this.totalquestion = totalquestion;
	}
	public String getAttemptquestion() {
		return attemptquestion;
	}
	public void setAttemptquestion(String attemptquestion) {
		this.attemptquestion = attemptquestion;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public QuestionResponseModel[] getQuestions() {
		return questions;
	}
	public void setQuestions(QuestionResponseModel[] questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "SectionResponseModel [sectionid=" + sectionid + ", title=" + title + ", sectionmarks=" + sectionmarks
				+ ", sectionnote=" + sectionnote + ", totalquestion=" + totalquestion + ", attemptquestion="
				+ attemptquestion + ", descr=" + descr + ", questions=" + questions + "]";
	}
}