package com.thinkwage.homebundle.ui;

import android.app.Application;

import com.api.HomeServiceFactory;
import com.ice.library.constant.AppConfig;
import com.ice.library.mvp.BaseModel;
import com.thinkwage.homebundle.BuildConfig;
import com.thinkwage.homebundle.bean.HomeBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2018/1/22.
 */

public class HomeBundleModel extends BaseModel implements HomeBundleContract.Model{
    private Application mApplication;

    @Inject
    public HomeBundleModel(Application application) {

        this.mApplication = application;
    }


    @Override
    public Observable<HomeBean> initData() {
        return HomeServiceFactory.homeIndex("1", AppConfig.APP_NAME,"1","1", BuildConfig.FLAVOR,"android","1","1","1","1.0.0")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

    @Override
    public void onDestory() {

    }

}
