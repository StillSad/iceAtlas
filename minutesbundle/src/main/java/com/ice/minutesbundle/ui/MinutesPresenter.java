package com.thinkwage.minutesbundle.ui;

import android.app.Application;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.integration.AppManager;
import com.thinkwage.library.mvp.BasePresenter;
import com.thinkwage.library.utils.RxUtils;
import com.thinkwage.minutesbundle.bean.EyeMinutes;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ICE on 2017/7/17.
 */

public class MinutesPresenter extends BasePresenter<MinutesModel,MinutesContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public MinutesPresenter(MinutesModel model, RxErrorHandler handler
            , Application application, AppManager appManager) {
        super(model);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mAppManager = appManager;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void eyesight() {
        mModel.eyesight()
                .compose(RxUtils.<NetBody<EyeMinutes>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<EyeMinutes>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<EyeMinutes> stringNetBody) {

                    }
                });
    }
    public void heightWeight() {
        mModel.heightWeight()
                .compose(RxUtils.<NetBody<String>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> stringNetBody) {

                    }
                });
    }
    public void teeth() {
        mModel.teeth()
                .compose(RxUtils.<NetBody<String>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> stringNetBody) {

                    }
                });
    }
}
