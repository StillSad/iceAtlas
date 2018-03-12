package com.thinkwage.userinfobundle.service;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Encrypted;
import com.app.annotation.apt.Params;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.userinfobundle.bean.UserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ICE on 2017/8/10.
 */
@ApiFactory
public interface UserInfoService {

     //个人信息接口
    @GET("Profile")
    Observable<NetBody<UserInfo>> profile();

}
