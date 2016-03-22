package com.crm.api;

import com.crm.util.CommonUtil;

public class ServiceMain {

    public static void main(String[] args) {
        new CustomerController(new CustomerService(), CommonUtil.getJsonConvertor());
    }

}