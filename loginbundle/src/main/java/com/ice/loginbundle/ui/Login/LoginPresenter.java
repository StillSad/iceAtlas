package com.thinkwage.loginbundle.ui.Login;

import android.app.Application;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.integration.AppManager;
import com.thinkwage.library.mvp.BasePresenter;
import com.thinkwage.library.utils.DataHelper;
import com.thinkwage.library.utils.RxUtils;
import com.thinkwage.loginbundle.bean.Kindergarten;
import com.thinkwage.loginbundle.bean.Login;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ICE on 2017/8/10.
 */

public class LoginPresenter extends BasePresenter<LoginModel, LoginContract.View> {

    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginModel model, Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void login(String mobile, String password) {
        mModel.login(mobile, password)
                .compose(RxUtils.<NetBody<Login>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<Login>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<Login> result) {
                        if (200 == result.getCode()){
                            DataHelper.setStringSF(DataKey.TOKEN, result.getData().getUser_token());
                            DataHelper.setStringSF(DataKey.USER_ID, result.getData().getUser_id());
                            mRootView.loginSuccess();
                        } else {
                            mRootView.loginError(result.getErrorMsg());
                        }
                    }
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}
