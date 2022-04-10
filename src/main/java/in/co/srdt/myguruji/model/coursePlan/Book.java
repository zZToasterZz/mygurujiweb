package in.co.srdt.myguruji.model.coursePlan;

public class Book
{
	private long courseplanid = 0;
	private long bookid = 0;
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
	public String getBooktitle() {
		return booktitle;
	}
	
	public Book(long courseplanid, long bookid, String booktitle, String bookdescr, String bookauthor,
			String booktype) {
		super();
		this.courseplanid = courseplanid;
		this.bookid = bookid;
		this.booktitle = booktitle;
		//this.bookdescr = bookdescr;
		this.bookauthor = bookauthor;
		this.booktype = booktype;
		
		String dirtyDescr = bookdescr;
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	this.bookdescr = bookdescr;
	}
	@Override
	public String toString() {
		return "Book [courseplanid=" + courseplanid + ", bookid=" + bookid + ", booktitle=" + booktitle + ", bookdescr="
				+ bookdescr + ", bookauthor=" + bookauthor + ", booktype=" + booktype + "]";
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	public String getBookdescr() {
		return bookdescr;
	}
	public void setBookdescr(String bookdescr) {
		String dirtyDescr = bookdescr;
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	this.bookdescr = bookdescr;
	}
	public String getBookauthor() {
		return bookauthor;
	}
	public Book() {
		super();
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
}