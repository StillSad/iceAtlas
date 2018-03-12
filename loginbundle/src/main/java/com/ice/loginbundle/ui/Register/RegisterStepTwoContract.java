package com.thinkwage.loginbundle.ui.Register;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.BaseView;
import com.thinkwage.library.mvp.IModel;

import io.reactivex.Observable;

/**
 * Created by ICE on 2017/8/10.
 */

public interface RegisterStepTwoContract {
    interface View extends BaseView {
        void countDown();
        void registerSuccess();
        void registerError(String errorMsg);
    }

    interface Model extends IModel {
        Observable<NetBody<String>> registerNext(String userId,String mobile,String validCode,String password);
    }

}
