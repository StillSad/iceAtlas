package com.thinkwage.homebundle.ui;

import android.app.Application;

import com.ice.api.bean.HomeBean;
import com.ice.library.di.scope.ActivityScope;
import com.ice.library.http.Subscriber.HttpSubscriber;
import com.ice.library.integration.AppManager;
import com.ice.library.mvp.BasePresenter;
import com.ice.library.utils.RxUtils;

import javax.inject.Inject;

/**
 * Created by ICE on 2018/1/22.
 */
@ActivityScope
public class HomeBundlePresenter extends BasePresenter<HomeBundleModel,HomeBundleContract.View>{

    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public HomeBundlePresenter(HomeBundleModel model, Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;
    }

    public void initData(){
        mModel.initData()
                .compose(RxUtils.<HomeBean>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<HomeBean>() {

                    @Override
                    public void resultSuccess(HomeBean homeBean) {
                        mRootView.initBanner(homeBean.banners);
                        mRootView.initArticleInfo(homeBean.articleInfo);
                        mRootView.initModule(homeBean.module);
                    }

                    @Override
                    public void resultError(String msg) {

                    }
                });
    }



}
