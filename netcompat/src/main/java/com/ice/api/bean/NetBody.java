package com.ice.api.bean;

/**
 * Created by ICE on 2017/9/6.
 */

public class NetBody<T> {

    /**
     * code : 400
     * success : false
     * errorMsg : 幼儿园列表为空
     * data :
     */

    private int code;
    private String success;
    private String errorMsg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
