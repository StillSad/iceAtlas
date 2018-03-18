package com.ice.library.di.module;

import com.api.NetFactory;
import com.google.gson.Gson;
import com.ice.library.base.BaseApplication;
import com.ice.library.constant.AppConfig;
import com.ice.library.http.cacahe.CacheProvide;
import com.ice.library.http.interceptor.HttpCacheInterceptor;
import com.thinkwage.library.BuildConfig;

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
 * Created by ice on 18/3/16.
 */
@Module
public class ClientModule {
    @Singleton
    @Provides
    OkHttpClient.Builder provideOkhttpClintBuilder(){
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){

        builder.addNetworkInterceptor(new HttpCacheInterceptor()) //
                .cache(new CacheProvide(BaseApplication.getAppContext()).provideCache()) //缓存
                .retryOnConnectionFailure(false) //设置进行连接失败重试
                .connectTimeout(15, TimeUnit.SECONDS) //设置连接超时时间
                .readTimeout(8, TimeUnit.SECONDS)  //设置读取数据超时时间
                .writeTimeout(8, TimeUnit.SECONDS);//设置写入超时时间

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }


        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    public Retrofit provideRetrofit(Retrofit.Builder builder,OkHttpClient okHttpClient,Gson gson){

        builder.baseUrl(AppConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));



//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .serializeNulls()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(AppConfig.BASE_URL)
//                .build();

        return builder.build();
    }

    @Singleton
    @Provides
    public NetFactory provideNetFactory(Retrofit retrofit) {
        return new NetFactory(retrofit);
    }

}
