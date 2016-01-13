package com.server.tictactoe.persistence.enums;

/**
 * Created by kvillaca on 1/13/2016.
 */
public enum HTTPEnums {
    CODE_404("404", "NOT FOUND"), CODE_500("500", "INTERNAL SERVER ERROR"), CODE_200("200", "OK"), CODE_417("417", "EXPECTATION FAILED");

    private String code;
    private String message;

    private HTTPEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
