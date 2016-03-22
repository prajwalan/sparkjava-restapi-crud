package com.crm.api.response;

import com.google.gson.annotations.Expose;

public class EntityResponse<T> extends BaseResponse {

    @Expose
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
