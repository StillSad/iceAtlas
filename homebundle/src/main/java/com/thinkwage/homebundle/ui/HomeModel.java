package com.thinkwage.homebundle.ui;

import android.app.Application;

import com.api.NetFactory;
import com.ice.library.constant.AppConfig;
import com.ice.library.mvp.BaseModel;
import com.thinkwage.homebundle.BuildConfig;
import com.ice.api.bean.HomeBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2018/1/22.
 */

public class HomeModel extends BaseModel implements HomeContract.Model{
    private Application mApplication;
    private NetFactory mNetFactory;

    @Inject
    public HomeModel(Application application,NetFactory net) {

        this.mApplication = application;
        this.mNetFactory = net;
    }


    @Override
    public Observable<HomeBean> homeIndex() {
        return mNetFactory
                .HomeService()
                .homeIndex("1", AppConfig.APP_NAME,"1","1", BuildConfig.FLAVOR,"android","1","1","1","1.0.0")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
//        return HomeServiceFactory.homeIndex("1", AppConfig.APP_NAME,"1","1", BuildConfig.FLAVOR,"android","1","1","1","1.0.0")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io());

    }

    @Override
    public void onDestory() {

    }
}
