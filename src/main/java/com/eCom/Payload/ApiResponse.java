package com.eCom.Payload;

public class ApiResponse {

    private String message;
    private boolean success;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public ApiResponse() {
    }
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    @Override
    public String toString() {
        return "ApiResponse [message=" + message + ", success=" + success + "]";
    }

    
    
}
