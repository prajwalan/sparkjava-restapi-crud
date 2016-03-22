package com.crm.api.integrationtest;

/**
 * Inspired from https://dzone.com/articles/building-simple-restful-api
 *
 */
public class TestHttpResponse {
    public final String body;
    public final int status;

    public TestHttpResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }
}
