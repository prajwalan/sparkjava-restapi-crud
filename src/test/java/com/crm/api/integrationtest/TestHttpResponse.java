package com.crm.api.integrationtest;

public class TestHttpResponse {
    public final String body;
    public final int status;

    public TestHttpResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }
}
