package in.co.srdt.myguruji.model;

import java.util.List;

public class DoubleResponseModel {

    private String message;
    private String status;
    List<DoubleResponseModel> questiondetail;

    public DoubleResponseModel() {
    }

    public DoubleResponseModel(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DoubleResponseModel> getQuestiondetail() {
        return questiondetail;
    }

    public void setQuestiondetail(List<DoubleResponseModel> questiondetail) {
        this.questiondetail = questiondetail;
    }
}
