package com.ice.library.di.component;

import android.app.Application;

import com.ice.library.base.delegate.AppDelegate;
import com.ice.library.di.module.AppModule;

import com.ice.library.integration.AppManager;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Application application();


    //用于管理所有activity
    AppManager appManager();


    void inject(AppDelegate delegate);
}
