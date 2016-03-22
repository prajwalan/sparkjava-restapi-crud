package com.crm.model;

import com.google.gson.annotations.Expose;

public class UserLogin {

    @Expose
    private String username;
    @Expose
    private String password;

    public UserLogin() {

    }

    public UserLogin(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
