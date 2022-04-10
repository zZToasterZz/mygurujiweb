package in.co.srdt.myguruji.model;

import java.util.List;

public class TemplateModel
{
	private long templateid = 0;
    public long getTemplateid() {
		return templateid;
	}
	public void setTemplateid(long templateid) {
		this.templateid = templateid;
	}
	private String templatecode;
    private String title;
    private String descr;
    private long noofsection;
    private String createdby;
    private List<SectionModel> sectiondetails;
    
	public TemplateModel() {
		super();
	}
	
	@Override
	public String toString() {
		return "TemplateModel [templateid=" + templateid + ", templatecode=" + templatecode + ", title=" + title
				+ ", descr=" + descr + ", noofsection=" + noofsection + ", createdby=" + createdby + ", sectiondetails="
				+ sectiondetails + "]";
	}
	public TemplateModel(long templateid, String templatecode, String title, String descr, long noofsection,
			String createdby, List<SectionModel> sectiondetails) {
		super();
		this.templateid = templateid;
		this.templatecode = templatecode;
		this.title = title;
		this.descr = descr;
		this.noofsection = noofsection;
		this.createdby = createdby;
		this.sectiondetails = sectiondetails;
	}
	public String getTemplatecode() {
		return templatecode;
	}
	public void setTemplatecode(String templatecode) {
		this.templatecode = templatecode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public long getNoofsection() {
		return noofsection;
	}
	public void setNoofsection(long noofsection) {
		this.noofsection = noofsection;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public List<SectionModel> getSectiondetails() {
		return sectiondetails;
	}
	public void setSectiondetails(List<SectionModel> sectiondetails) {
		this.sectiondetails = sectiondetails;
	}
}