package in.co.srdt.myguruji.model;

public class ResponseMessage
{
	private String message;

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}
	public ResponseMessage() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}