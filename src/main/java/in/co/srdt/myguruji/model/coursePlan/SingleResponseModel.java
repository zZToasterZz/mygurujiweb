package in.co.srdt.myguruji.model.coursePlan;

public class SingleResponseModel
{
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SingleResponseModel(String message) {
		super();
		this.message = message;
	}

	public SingleResponseModel() {
		super();
	}

	@Override
	public String toString() {
		return "SingleResponseModel [message=" + message + "]";
	}
}