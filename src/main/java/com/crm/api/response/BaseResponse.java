package com.crm.api.response;

import com.google.gson.annotations.Expose;

public class BaseResponse {

    @Expose
    protected int code;
    @Expose
    protected String message;

    public BaseResponse() {

    }

    public BaseResponse(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
