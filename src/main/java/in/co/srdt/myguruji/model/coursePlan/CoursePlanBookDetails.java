package in.co.srdt.myguruji.model.coursePlan;

public class CoursePlanBookDetails
{
	private long bookid;
	private long courseplanid;
	private String booktitle;
	private String bookdescr;
	private String bookauthor;
	private String booktype;
	public long getBookid() {
		return bookid;
	}
	public void setBookid(long bookid) {
		this.bookid = bookid;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}
	public String getBooktitle() {
		return booktitle;
	}
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	public String getBookdescr() {
		return bookdescr;
	}
	public void setBookdescr(String bookdescr) {
		this.bookdescr = bookdescr;
	}
	public String getBookauthor() {
		return bookauthor;
	}
	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public CoursePlanBookDetails(long bookid, long courseplanid, String booktitle, String bookdescr, String bookauthor,
			String booktype) {
		super();
		this.bookid = bookid;
		this.courseplanid = courseplanid;
		this.booktitle = booktitle;
		this.bookdescr = bookdescr;
		this.bookauthor = bookauthor;
		this.booktype = booktype;
	}
	public CoursePlanBookDetails() {
		super();
	}
}