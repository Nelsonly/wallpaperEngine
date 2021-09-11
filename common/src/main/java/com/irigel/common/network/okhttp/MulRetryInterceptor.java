package com.irigel.common.network.okhttp;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author zhangqing
 * @date 2/20/21
 * 重试请求拦截器
 */

public class MulRetryInterceptor implements Interceptor {

    private static final String TAG = "MulRetryInterceptor";

    /**
     * 最大重试请求次数
     */
    private final int maxRetry;
    private int retryNum = 0;

    public MulRetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    /**
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        Response response = chain.proceed(request);

        retryNum = 0;
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            response.close();
            response = chain.proceed(request);
            Log.d(TAG, "response=" + response.isSuccessful()+",and code=" + response.code()+"," +retryNum);
        }
        return response;
    }
}
