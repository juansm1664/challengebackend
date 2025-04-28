package com.juandavid.springboot.challengebackend.dto;

public class ErrorResponse {

    private String timestamp;
    private String message;
    private String status;
    private String error;
    private String path;

    public ErrorResponse() {
    }

    public ErrorResponse(String timestamp, String message, String error, String path, String status) {
        this.timestamp = timestamp;
        this.message = message;
        this.error = error;
        this.path = path;
        this.status = status;
    }

    public ErrorResponse(int value, String cálculoFallido, String message, String requestURI) {
        this.timestamp = String.valueOf(value);
        this.message = cálculoFallido;
        this.error = message;
        this.path = requestURI;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
