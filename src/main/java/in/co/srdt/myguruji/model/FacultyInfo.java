package in.co.srdt.myguruji.model;

import java.util.List;

public class FacultyInfo {
	
	private FacultyDetails faculty;
	private List<Batch> batches;
	
	public FacultyInfo() {
		super();
	}

	public FacultyInfo(FacultyDetails faculty, List<Batch> batches) {
		super();
		this.faculty = faculty;
		this.batches = batches;
	}

	public FacultyDetails getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyDetails faculty) {
		this.faculty = faculty;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}	
}
