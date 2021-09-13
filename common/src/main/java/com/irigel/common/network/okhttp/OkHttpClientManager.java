package com.irigel.common.network.okhttp;

import com.irigel.common.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author zhangqing
 * @date 2/20/21
 * okhttpClient封装类
 */
public class OkHttpClientManager {

    private OkHttpClient okHttpClient;

    private static class OkHttpClientManagerHolder {
        private static final OkHttpClientManager INSTANCE = new OkHttpClientManager();
    }

    private OkHttpClientManager(){
        if (okHttpClient == null) {
            okHttpClient = createOkHttpBuilder()
            .addInterceptor(new MulRetryInterceptor(3))
            .build();
        }
    }

    /**
     * 返回封装单例对象
     * @return
     */
    public static OkHttpClientManager getInstance(){
        return OkHttpClientManagerHolder.INSTANCE;
    }

    /**
     * 返回okHttpClient对象
     * @return
     */
    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    /**
     * 创建 OkHttpBuilder并设置超时
     * @return
     */
    private OkHttpClient.Builder createOkHttpBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS);
    }
}
