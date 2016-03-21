package com.demo.api;

import com.demo.util.CommonUtil;

public class ServiceMain {

    public static void main(String[] args) {
        new CustomerController(new CustomerService(), CommonUtil.getJsonConvertor());
    }

}