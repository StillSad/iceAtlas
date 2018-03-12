package com.thinkwage.userinfobundle.ui;

import android.app.Application;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.integration.AppManager;
import com.thinkwage.library.mvp.BasePresenter;
import com.thinkwage.library.utils.DataHelper;
import com.thinkwage.userinfobundle.bean.UserInfo;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ICE on 2017/7/17.
 */

public class BaseInfoPresenter extends BasePresenter<BaseInfoModel, BaseInfoContract.View> {

    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public BaseInfoPresenter(BaseInfoModel model, Application application, AppManager appManager) {
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

    public void profile() {
        mModel.profile()
                .subscribe(new HttpSubscriber<NetBody<UserInfo>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<UserInfo> userInfoNetBody) {
                        if (200 == userInfoNetBody.getCode()) {
                            DataHelper.setStringSF(DataKey.MOBILE,userInfoNetBody.getData().mobile);
                            mRootView.setUserInfo(userInfoNetBody.getData());
                        }
                    }
                });
    }
}
