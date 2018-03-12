package com.thinkwage.minutesbundle.ui;

import android.app.Application;

import com.api.MinutesServiceFactory;
import com.google.gson.Gson;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.BaseModel;
import com.thinkwage.minutesbundle.bean.EyeMinutes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/7/17.
 */

public class MinutesModel extends BaseModel implements MinutesContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MinutesModel(Gson gson, Application application) {

        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<NetBody<EyeMinutes>> eyesight() {
        return MinutesServiceFactory.getEyesight()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<NetBody<String>> heightWeight() {
        return MinutesServiceFactory.getHeightWeight()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<NetBody<String>> teeth() {
        return null;
    }
}

