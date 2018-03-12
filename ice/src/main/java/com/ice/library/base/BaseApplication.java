package com.ice.library.base;

import android.app.Activity;
import android.app.Application;
//import android.support.multidex.MultiDex;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.ice.library.Loader.BoxingGlideLoader;
import com.ice.library.Loader.BoxingUcrop;
import com.ice.library.base.delegate.AppDelegate;
import com.ice.library.di.component.AppComponent;
//import com.tencent.bugly.Bugly;
//import com.tencent.bugly.beta.Beta;
//import com.tencent.tinker.loader.app.TinkerApplication;
//import com.tencent.tinker.loader.shareutil.ShareConstants;



/**
 * Created by ICE on 2017/7/7.
 */

public class BaseApplication extends Application implements App{
    private AppDelegate mAppDelegate;
    private static BaseApplication mApp;



    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        this.mAppDelegate = new AppDelegate(this);
        this.mAppDelegate.onCreate();
        //初始化数据库
        initDatabase();
        //初始化图片选择器
        initBoxing();


    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate();
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return mAppDelegate.getAppComponent();
    }

    public static BaseApplication getAppContext() {
        return mApp;
    }

    //获取当前activity
    public Activity getCurActivity() {
        return mAppDelegate.getAppComponent().appManager().getActivityStack().lastElement();
    }

    /**
     * 设置Realm
     */
    private void initDatabase() {
//        Realm.init(this);
    }

}
