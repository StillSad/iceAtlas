package com.thinkwage.revisebundle.services;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Params;
import com.thinkwage.library.bean.NetBody;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ICE on 2017/8/19.
 */
@ApiFactory
public interface ReviseService {

//    修改手机号
    @Params({"newmobile","validCode","newvalidCode"})
    @PUT("ModifyMobile/{mobile}")
    Observable<NetBody<String>> modifyMobile(@Path("mobile") String mobile, @Body HashMap<String,String> params);

//     修改密码接口
    @Params({"oldpassword","newpassword","confirmpassword"})
    @POST("ModifyPassword")
    Observable<NetBody<String>> modifyPassword(@Body HashMap<String,String> params);

//      修改昵称接口
    @Params({"newnickname"})
    @POST("ModifyNickName")
    Observable<NetBody<String>> modifyNickName(@Body HashMap<String,String> params);
}
