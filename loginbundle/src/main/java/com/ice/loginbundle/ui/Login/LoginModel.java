package com.thinkwage.loginbundle.ui.Login;

import android.app.Application;

import com.api.LoginServiceFactory;
import com.google.gson.Gson;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.loginbundle.bean.Kindergarten;
import com.thinkwage.loginbundle.bean.Login;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/8/10.
 */

public class LoginModel implements LoginContract.Model {

    private Application mApplication;

    @Inject
    public LoginModel(Application application) {

        this.mApplication = application;
    }

    @Override
    public void onDestory() {
        this.mApplication = null;
    }

    @Override
    public Observable<NetBody<Login>> login(String mobile, String password) {
        return LoginServiceFactory.login(mobile, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
