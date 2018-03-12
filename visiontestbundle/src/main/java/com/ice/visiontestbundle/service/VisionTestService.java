package com.thinkwage.visiontestbundle.service;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Params;
import com.thinkwage.library.bean.NetBody;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ICE on 2017/8/21.
 */
@ApiFactory
public interface VisionTestService {
    // 用户视力添加
    @Params({"left_eye","right_eye"})
    @POST("Eyesight")
    Observable<NetBody<String>> addEyesight(@Body HashMap<String,String> params);


}
