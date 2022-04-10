package in.co.srdt.myguruji.model;

import java.util.List;

public class GetSectionDetails
{
	private long sectionid;
	private String title;
	private int sectionmarks;
	private String sectionnote;
	private int totalquestion;
	private int attemptquestion;
	private String descr;
	private List<GetQuestionDetails> questions;
	
	public GetSectionDetails(long sectionid, String title, int sectionmarks, String sectionnote, int totalquestion,
			int attemptquestion, String descr, List<GetQuestionDetails> questions) {
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
	public GetSectionDetails() {
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
	public int getSectionmarks() {
		return sectionmarks;
	}
	public void setSectionmarks(int sectionmarks) {
		this.sectionmarks = sectionmarks;
	}
	public String getSectionnote() {
		return sectionnote;
	}
	public void setSectionnote(String sectionnote) {
		this.sectionnote = sectionnote;
	}
	public int getTotalquestion() {
		return totalquestion;
	}
	public void setTotalquestion(int totalquestion) {
		this.totalquestion = totalquestion;
	}
	public int getAttemptquestion() {
		return attemptquestion;
	}
	public void setAttemptquestion(int attemptquestion) {
		this.attemptquestion = attemptquestion;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public List<GetQuestionDetails> getQuestions() {
		return questions;
	}
	public void setQuestions(List<GetQuestionDetails> questions) {
		this.questions = questions;
	}
}