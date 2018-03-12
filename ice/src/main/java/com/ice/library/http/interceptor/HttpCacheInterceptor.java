package com.ice.library.http.interceptor;

import android.os.Build;
import android.util.Log;

import com.ice.library.constant.AppConfig;
import com.ice.library.utils.DataHelper;
import com.ice.library.base.BaseApplication;
import com.ice.library.constant.DataKey;
import com.ice.library.utils.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ICE on 2017/7/14.
 */

public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        if (!NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
            oldRequest = oldRequest.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.e("Okhttp", "no network");
        }
        // 新的请求,添加参数
        Request newRequest = addParam(oldRequest);
        Response originalResponse = chain.proceed(newRequest);

        if (NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = oldRequest.cacheControl().toString();
            return originalResponse.newBuilder()
                    .addHeader("Cache-Control", cacheControl)
                    .addHeader("Content-Type","application/json")
                    .addHeader("Accept","application/json")
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        Log.e("Okhttp", "添加请求参数");
        //连接地址后添加参数
        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder();
//                .setEncodedQueryParameter("user_token", DeviceUtil.getDeviceId())
//                .setEncodedQueryParameter("app_token", DeviceUtil.getTime());
//                .setEncodedQueryParameter("_token", "");
        //在header中添加参数
        Request.Builder headerBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(urlBuilder.build())
                .addHeader("user_token", DataHelper.getStringSF(DataKey.TOKEN))
                .addHeader("app_token", AppConfig.APP_TOKEN);
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            headerBuilder.addHeader("Connection", "close");
        }
        return headerBuilder.build();
    }
}
