package com.ice.library.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ice.library.base.BaseApplication;
import com.ice.library.constant.AppConfig;
import com.ice.library.http.Provider.OkhttpProvider;
import com.ice.library.http.Provider.RetrofitProvider;
import com.ice.library.http.cacahe.CacheProvide;
import com.ice.library.http.interceptor.HttpCacheInterceptor;
import com.thinkwage.library.BuildConfig;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jess on 8/4/16.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    GsonBuilder provideGsonBuilder(){
        return new GsonBuilder();
    }

    @Singleton
    @Provides
    Gson provideGson(GsonBuilder builder){
        builder
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls();
        return builder.create();
    }




    @Singleton
    @Provides
    public Map<String, Object> provideExtras(){
        return new ArrayMap<>();
    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

}
