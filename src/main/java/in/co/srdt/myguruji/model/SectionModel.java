package in.co.srdt.myguruji.model;

public class SectionModel
{
	private long sectionid;
	private String title;
	private String sectionmarks;
	private String sectionnote;
	private String totalquestion;
	private String attemptquestion;
	private String descr;
	
	
	
	@Override
	public String toString() {
		return "SectionModel [sectionid=" + sectionid + ", title=" + title + ", sectionmarks=" + sectionmarks
				+ ", sectionnote=" + sectionnote + ", totalquestion=" + totalquestion + ", attemptquestion="
				+ attemptquestion + ", descr=" + descr + "]";
	}
	public long getSectionid() {
		return sectionid;
	}
	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}
	public SectionModel(long sectionid, String title, String sectionmarks, String sectionnote, String totalquestion,
			String attemptquestion, String descr) {
		super();
		this.sectionid = sectionid;
		this.title = title;
		this.sectionmarks = sectionmarks;
		this.sectionnote = sectionnote;
		this.totalquestion = totalquestion;
		this.attemptquestion = attemptquestion;
		this.descr = descr;
	}
	public SectionModel() {
		super();
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
}