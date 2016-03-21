package com.demo.api.response;

import com.demo.util.Constants;
import com.google.gson.annotations.Expose;

public class Response {

    @Expose
    private int code;
    @Expose
    private String message;
    @Expose
    private Object result;

    public Response() {

    }

    public Response(Object result) {
        this.code = Constants.RESPONSE_OK;
        this.message = "";
        this.result = result;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public Response(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
