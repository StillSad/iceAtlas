package com.ice.library.http.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ice.library.constant.AppConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ICE on 2017/7/14.
 */

public class RetrofitProvider {
    static Retrofit instance;

    public static Retrofit retrofit() {
        if (instance == null) {
            synchronized (RetrofitProvider.class) {
                if (instance == null) {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                            .serializeNulls()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(OkhttpProvider.okHttpClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .baseUrl(AppConfig.BASE_URL)
                            .build();
                    instance = retrofit;
                }

            }

        }
        return instance;

    }
}
