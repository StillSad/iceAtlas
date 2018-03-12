package com.thinkwage.loginbundle.ui.Login;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.BaseView;
import com.thinkwage.library.mvp.IModel;
import com.thinkwage.loginbundle.bean.Kindergarten;
import com.thinkwage.loginbundle.bean.Login;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ICE on 2017/8/10.
 */

public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();
        void loginError(String errorMessage);
    }

    interface Model extends IModel {
        Observable<NetBody<Login>> login(String mobile, String password);
    }

}
