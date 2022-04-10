package in.co.srdt.myguruji.model.coursePlan;

import java.util.List;

public class SubunitPost
{
	List<SubunitModel> subunit;

	public SubunitPost(List<SubunitModel> subunit) {
		super();
		this.subunit = subunit;
	}

	public SubunitPost() {
		super();
	}

	public List<SubunitModel> getSubunit() {
		return subunit;
	}

	public void setSubunit(List<SubunitModel> subunit) {
		this.subunit = subunit;
	}

	@Override
	public String toString() {
		return "SubunitPost [subunit=" + subunit + "]";
	}
}