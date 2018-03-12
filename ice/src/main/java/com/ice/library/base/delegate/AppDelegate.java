package com.ice.library.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import com.bumptech.glide.Glide;
import com.ice.library.di.component.AppComponent;
import com.ice.library.integration.ActivityLifecycle;
import com.ice.library.base.App;
import com.ice.library.di.component.DaggerAppComponent;
import com.ice.library.di.module.AppModule;

import javax.inject.Inject;

/**
 * AppDelegate可以代理Application的生命周期,在对应的生命周期,执行对应的逻辑,因为Java只能单继承
 * 而我的框架要求Application要继承于BaseApplication
 * 所以当遇到某些三方库需要继承于它的Application的时候,就只有自定义Application继承于三方库的Application
 * 再将BaseApplication的代码复制进去,而现在就不用再复制代码,只用在对应的生命周期调用AppDelegate对应的方法(Application一定要实现APP接口)
 * <p>
 * Created by jess on 24/04/2017 09:44
 * Contact with jess.yan.effort@gmail.com
 */

public class AppDelegate implements App {
    private Application mApplication;
    private AppComponent mAppComponent;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private ComponentCallbacks2 mComponentCallback;

    public AppDelegate(Application application) {
        this.mApplication = application;
    }

    public void onCreate() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication))//提供application
                .build();
        mAppComponent.inject(this);
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
        mComponentCallback = new AppComponentCallbacks(mApplication, mAppComponent);
        mApplication.registerComponentCallbacks(mComponentCallback);
    }

    public void onTerminate() {

        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }

        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mComponentCallback = null;
        this.mApplication = null;
    }

    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */

    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public interface Lifecycle {
        void onCreate(Application application);

        void onTerminate(Application application);
    }

    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;
        private AppComponent mAppComponent;

        public AppComponentCallbacks(Application application,AppComponent appComponent) {
            this.mApplication = application;
            this.mAppComponent = appComponent;
        }

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {
            //内存不足时清理图片请求框架的内存缓存
//            LoaderFactory.getLoader().clearMemoryCache(mApplication);
            Glide.get(mApplication).clearDiskCache();
        }
    }
}
