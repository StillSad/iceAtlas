package com.thinkwage.minutesbundle.service;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Params;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.minutesbundle.bean.EyeMinutes;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by ICE on 2017/9/8.
 */
@ApiFactory
public interface MinutesService {

//用户视力列表
    @GET("Eyesight")
    Observable<NetBody<EyeMinutes>> getEyesight();


    //用户身高体重列表
    @GET("HeightWeight")
    Observable<NetBody<String>> getHeightWeight();
    // 添加身高体重接口
    @Params({"weight","height"})
    @GET("HeightWeight")
    Observable<NetBody<String>> addHeightWeight(@Body HashMap<String,String> params);
}
