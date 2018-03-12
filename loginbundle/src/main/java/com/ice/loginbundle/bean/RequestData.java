package com.thinkwage.loginbundle.bean;

/**
 * Created by ICE on 2017/9/7.
 */

public class RequestData {

    /**
     * mobile : 17682302022
     * validCode : string
     * password : string
     */

    private String mobile;
    private String validCode;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
