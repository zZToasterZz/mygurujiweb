package in.co.srdt.myguruji.model;

import java.util.List;

public class StudentDetailsModel
{
	Student student;
	List<Batch> batches;
	
	public StudentDetailsModel(Student student, List<Batch> batches) {
		super();
		this.student = student;
		this.batches = batches;
	}
	public StudentDetailsModel() {
		super();
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Batch> getBatches() {
		return batches;
	}
	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
	@Override
	public String toString() {
		return "StudentDetailsModel [student=" + student + ", batches=" + batches + "]";
	}
}