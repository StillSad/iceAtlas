package com.ice.api.service;

import com.app.annotation.apt.ApiService;
import com.app.annotation.apt.Params;
import com.ice.api.bean.NetBody;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ICE on 2017/9/8.
 */
@ApiService
public interface PublicService {
    //获取验证码
    @Params({"mobile"})
    @POST("Validcode")
    Observable<NetBody<String>> getCode(@Body HashMap<String, String> params);
}
