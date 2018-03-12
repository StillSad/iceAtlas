package com.thinkwage.userinfobundle.ui;

import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.mvp.IModel;
import com.thinkwage.library.mvp.IView;
import com.thinkwage.userinfobundle.bean.UserInfo;

import io.reactivex.Observable;

/**
 * Created by ICE on 2017/7/17.
 */

public class BaseInfoContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
       void setUserInfo(UserInfo userInfo);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<NetBody<UserInfo>> profile();
    }
}
