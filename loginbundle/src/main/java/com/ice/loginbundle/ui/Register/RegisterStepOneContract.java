package com.thinkwage.loginbundle.ui.Register;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.BaseView;
import com.thinkwage.library.mvp.IModel;
import com.thinkwage.loginbundle.bean.Kindergarten;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by ICE on 2017/8/10.
 */

public interface RegisterStepOneContract {
    interface View extends BaseView {
        void next(String id);
        void setKindergarten(String[] kindergartens);
    }

    interface Model extends IModel {
        Observable<NetBody<List<Kindergarten>>> getKindergartenList();
        Observable<NetBody<String>> register(String schoolid,String truename,String schoolno,String birthdate,String mPhotoPath);
        Observable<NetBody<String>> getCode(String mobil);
    }

}
