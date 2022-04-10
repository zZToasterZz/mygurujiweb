package in.co.srdt.myguruji.model;

import java.util.List;

public class BatchDetailsModel
{
	private Batch batch;
	private List<FacultyDetails> faculty;
	private List<Student> students;
	
	public BatchDetailsModel(Batch batch, List<FacultyDetails> faculty, List<Student> students) {
		super();
		this.batch = batch;
		this.faculty = faculty;
		this.students = students;
	}
	public BatchDetailsModel() {
		super();
	}
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public List<FacultyDetails> getFaculty() {
		return faculty;
	}
	public void setFaculty(List<FacultyDetails> faculty) {
		this.faculty = faculty;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
}