package in.co.srdt.myguruji.model;

import java.util.List;

public class CatgContentByUnitGET
{
	private String contenttype;
	private long contentcount;
	private List<ContentDetails> content;
	
	public CatgContentByUnitGET(String contenttype, long contentcount, List<ContentDetails> content)
	{
		this.contenttype = contenttype;
		this.contentcount = contentcount;
		this.content = content;
	}
	public CatgContentByUnitGET() {
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public long getContentcount() {
		return contentcount;
	}
	public void setContentcount(long contentcount) {
		this.contentcount = contentcount;
	}
	public List<ContentDetails> getContent() {
		return content;
	}
	public void setContent(List<ContentDetails> content) {
		this.content = content;
	}
}