package com.crm.api.response;

import java.util.List;

import com.google.gson.annotations.Expose;

public class ListResponse<T> extends BaseResponse {

    @Expose
    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
