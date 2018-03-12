package com.thinkwage.userinfobundle.ui;

import android.app.Application;

import com.api.UserInfoServiceFactory;
import com.google.gson.Gson;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.BaseModel;
import com.thinkwage.userinfobundle.bean.UserInfo;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/7/17.
 */

public class BaseInfoModel extends BaseModel implements BaseInfoContract.Model {

    private Application mApplication;

    @Inject
    public BaseInfoModel(Application application) {

        this.mApplication = application;
    }

    @Override
    public Observable<NetBody<UserInfo>> profile() {
        return UserInfoServiceFactory.profile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
