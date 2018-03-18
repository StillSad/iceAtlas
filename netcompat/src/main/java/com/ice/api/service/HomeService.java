package com.ice.api.service;

import com.app.annotation.apt.ApiService;
import com.app.annotation.apt.Params;
import com.ice.api.bean.HomeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ICE on 2018/1/24.
 */
@ApiService
public interface HomeService {

 /*   advertisement (string, optional): 广告信息 ,
    appName (string): 学历 education, 征信 credit, 上海个税 shanghaiTax, 个税管家 gsgj, 薪资管家 xzgj, 个税计算器 gsjsq, 北京 beijing, 广州 guangzhou, 51个税 gs51, 51征信 credit51, 51个税管家 gsgj51 ,
    article (string, optional): 发现文章 ,
    banner (string, optional): banner图 ,
    channelName (string, optional): 渠道名称 ,
    city (string, optional): 城市信息 ,
    deviceType (string, optional): 设备类型 android ios ,
    modules (string, optional): 功能模块 ,
    notice (string, optional): 公告 ,
    version (string, optional): 版本信息 ,
    versionNo (string, optional): 版本号*/
    @Params({"advertisement","appName","article","banner",
            "channelName","deviceType","modules",
            "notice","version","versionNo"})
    @POST("/api/v2/home/homeIndex")
    Observable<HomeBean> homeIndex(@Body HashMap<String, String> params);

    @GET
    Observable<HomeBean> aaa(@Query("aaa") String params);

   @GET
   Observable<HomeBean> bbb(@Query("aaa") String params);

}
