package com.example.dinoghost.model.response;

public class Response<T> {
    protected Boolean success;
    protected String message;
    protected T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response() {
    }

    public Response(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
