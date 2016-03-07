package com.server.tictactoe.business.pojos;

/**
 * Created by klausvillaca on 3/6/16.
 */
public class ErrorToReturn {

    private String status;
    private String message;

    public ErrorToReturn() {
    }

    public ErrorToReturn(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ErrorToReturn{")
                .append("status='")
                .append(status).append("'")
                .append(", message='")
                .append(message)
                .append("'")
                .append("}");
        return sb.toString();
    }
}
