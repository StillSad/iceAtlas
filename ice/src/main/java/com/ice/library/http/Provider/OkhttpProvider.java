package com.ice.library.http.Provider;


import com.thinkwage.library.BuildConfig;
import com.ice.library.base.BaseApplication;
import com.ice.library.http.cacahe.CacheProvide;
import com.ice.library.http.interceptor.HttpCacheInterceptor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 耿 on 2016/8/27.
 */
//创建OkHttpClient
public class OkhttpProvider {
    static OkHttpClient okHttpClient;

    public static OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkhttpProvider.class) {
                if (okHttpClient == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addNetworkInterceptor(new HttpCacheInterceptor()) //
                            .cache(new CacheProvide(BaseApplication.getAppContext()).provideCache()) //缓存
                            .retryOnConnectionFailure(false) //设置进行连接失败重试
                            .connectTimeout(15, TimeUnit.SECONDS) //设置连接超时时间
                            .readTimeout(8, TimeUnit.SECONDS)  //设置读取数据超时时间
                            .writeTimeout(8, TimeUnit.SECONDS) //设置写入超时时间
                            .build();
                    if (BuildConfig.DEBUG) {//printf logs while  debug
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        client = client.newBuilder().addInterceptor(logging).build();
                    }
                    okHttpClient = client;
                }

            }

        }
        return okHttpClient;

    }
}
