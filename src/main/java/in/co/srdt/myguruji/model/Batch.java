package in.co.srdt.myguruji.model;

public class Batch {

	private long courseid;
	private long batchid;
	private String title;
	private String batchcode;
	private String descr;
	private String createdby;
	private String type;
	private String year;
	private String seq;
	
	public Batch() {
		
	}
	
	public Batch(long courseid, long batchid, String title, String batchcode, String descr, String createdby,
			String type, String year, String seq) {
		this.courseid = courseid;
		this.batchid = batchid;
		this.title = title;
		this.batchcode = batchcode;
		this.descr = descr;
		this.createdby = createdby;
		this.type = type;
		this.year = year;
		this.seq = seq;
	}

	public long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
