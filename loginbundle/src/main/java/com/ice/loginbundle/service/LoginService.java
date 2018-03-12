package com.thinkwage.loginbundle.service;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Encrypted;
import com.app.annotation.apt.Params;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.loginbundle.bean.Kindergarten;
import com.thinkwage.loginbundle.bean.Login;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ICE on 2017/8/10.
 */
@ApiFactory
public interface LoginService {


    //登录
    @Params({"mobile","password"})
    @POST("Login")
    Observable<NetBody<Login>> login(@Body HashMap<String,String> params);

    //找回密码
    @Params({"validCode","password"})
    @PUT("ForgetPassword/{mobile}")
    Observable<NetBody<String>> forgetPassword(@Path("mobile") String mobile,@Body HashMap<String,String> params);

    //获取幼儿园列表
    @GET("Register")
    Observable<NetBody<List<Kindergarten>>> getKindergartenList();
    //注册第一步
    @Multipart
    @POST("Register")
    Observable<NetBody<String>> register(@Part() List<MultipartBody.Part > files);
    //注册第二步
    @Params({"mobile","validCode","password"})
    @PUT("Register/{userId}")
    Observable<NetBody<String>> registerNext(@Path("userId") String userId,@Body HashMap<String,String> params);

}
