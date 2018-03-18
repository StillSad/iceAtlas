package com.ice.api.bean;

/**
 * Created by ICE on 2018/1/24.
 */

public class NetBaseResult {

    //返回码,一般0000为操作正常 错误时返回其他返回码 ,
    public String code = "0000";
    //错误信息,一般情况下返回码不为0000时有值 ,
    public String message;
    //错误信息描述信息,一般情况下返回码不为0000时有值
    public String description;
}
