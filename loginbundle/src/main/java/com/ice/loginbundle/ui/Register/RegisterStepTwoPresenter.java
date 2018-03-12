package com.thinkwage.loginbundle.ui.Register;

import android.app.Application;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.integration.AppManager;
import com.thinkwage.library.mvp.BasePresenter;
import com.thinkwage.library.utils.LogUtils;
import com.thinkwage.library.utils.RxUtils;
import com.thinkwage.loginbundle.bean.Kindergarten;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ICE on 2017/8/10.
 */

public class RegisterStepTwoPresenter extends BasePresenter<RegisterModel, RegisterStepTwoContract.View> {

    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public RegisterStepTwoPresenter(RegisterModel model,Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;

    }


    public void registerNext(String userId, String mobile, String validCode, String password) {
        mModel.registerNext(userId, mobile, validCode, password)
                .compose(RxUtils.<NetBody<String>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<String>>() {

                    @Override
                    public void onSuccess(@NonNull NetBody<String> result) {
                        if (200 == result.getCode()) {
                            mRootView.registerSuccess();
                        } else {
                            mRootView.registerError(result.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void getCode(String mobile) {
        LogUtils.d("获取手机验证码");
        mModel.getCode(mobile)
                .compose(RxUtils.<NetBody<String>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<String>>() {

                    @Override
                    public void onSuccess(@NonNull NetBody<String> s) {
                        mRootView.countDown();
                    }
                });
    }
}
