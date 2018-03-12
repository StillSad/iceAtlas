package com.thinkwage.loginbundle.ui.Register;

import android.app.Application;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.integration.AppManager;
import com.thinkwage.library.mvp.BasePresenter;
import com.thinkwage.library.utils.RxUtils;
import com.thinkwage.loginbundle.bean.Kindergarten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by ICE on 2017/8/10.
 */

public class RegisterStepOnePresenter extends BasePresenter<RegisterModel, RegisterStepOneContract.View> {

    private Application mApplication;
    private AppManager mAppManager;
    private List<Kindergarten> kindergartens;

    @Inject
    public RegisterStepOnePresenter(RegisterModel model, Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;

    }

    public void getKindergartenList() {
        mModel.getKindergartenList()
                .compose(RxUtils.<NetBody<List<Kindergarten>>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<List<Kindergarten>>>() {

                    @Override
                    public void onSuccess(@NonNull NetBody<List<Kindergarten>> result) {
                        kindergartens = result.getData();
                        String[] kindergartenNames = new String[kindergartens.size()];
                        for (int i = 0; i < kindergartenNames.length; i++) {
                            kindergartenNames[i] = kindergartens.get(i).getSchool_name();
                        }
                        mRootView.setKindergarten(kindergartenNames);
                    }
                });

    }

    public void register(String schoolName, String truename, String schoolno, String birthdate, String photoPath) {

        mModel.register(getSchoolid(schoolName), truename, schoolno, birthdate, photoPath)
                .compose(RxUtils.<NetBody<String>>bindToLifecycle(mRootView))
                .subscribe(new HttpSubscriber<NetBody<String>>() {

                    @Override
                    public void onSuccess(@NonNull NetBody<String> result) {

                        mRootView.next(result.getData());
                    }
                });
    }

    private String getSchoolid(String schoolName) {
        for (Kindergarten kindergarten : kindergartens) {
            if (kindergarten.getSchool_name().equals(schoolName))
                return kindergarten.getSchool_id();
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }

}
