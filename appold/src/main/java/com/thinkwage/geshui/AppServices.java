package com.thinkwage.geshui;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Params;
import com.thinkwage.geshui.model.HomeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;

import retrofit2.http.POST;

/**
 * Created by ICE on 2017/7/14.
 */
@ApiFactory
public interface AppServices {
    @Params({"appName","article","banner","modules","notice","pageIndex","pageSize"})
    @POST("v2/home/index")
    Observable<HomeBean> homeIndex(@Body HashMap<String,String> params);


}
