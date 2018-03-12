package com.thinkwage.homebundle.ui;

import android.app.Application;

import com.ice.library.di.scope.ActivityScope;
import com.ice.library.di.scope.FragmentScope;
import com.ice.library.http.Subscriber.HttpSubscriber;
import com.ice.library.integration.AppManager;
import com.ice.library.mvp.BasePresenter;
import com.ice.library.utils.RxUtils;
import com.thinkwage.homebundle.bean.HomeBean;

import javax.inject.Inject;

/**
 * Created by ICE on 2018/1/22.
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeModel,HomeContract.View>{

    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public HomePresenter(HomeModel model, Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;
    }

    public void homeIndex(){
        mModel.homeIndex()
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
