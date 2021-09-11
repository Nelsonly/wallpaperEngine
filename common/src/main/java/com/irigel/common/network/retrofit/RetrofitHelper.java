package com.irigel.common.network.retrofit;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.irigel.common.BuildConfig;
import com.irigel.common.network.HttpConstants;
import com.irigel.common.network.interfaces.IHttpRequstCallBack;
import com.irigel.common.network.okhttp.OkHttpClientWrapper;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhangqing
 */
public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";

    /**
     * 获取service对象
     * @param clazz service字节码
     * @param hostType URL host
     * @param <T> 字节码类型
     * @return service对象
     */
    public static <T> T getService(Class<T> clazz, String hostType){
        String host = getHost(hostType);
        if(TextUtils.isEmpty(host)){
            return  null;
        }
       return createApi(clazz,OkHttpClientWrapper.getInstance().getOkHttpClient(),host);
    }


    /**
     * 获取host
     * @param hostType
     * @return
     */
    private static String getHost(String hostType){
        if(TextUtils.isEmpty(hostType)){
            return null;
        }
        switch (hostType) {
            case HttpConstants.TAG_MODE_HOST:
                if(BuildConfig.DEBUG) {
                    return HttpConstants.DevUrl.MODE_HOST;
                }else {
                    return HttpConstants.OnLineUrl.MODE_HOST;
                }
            case HttpConstants.TAG_UNSPLASH_HOST:
                return HttpConstants.OnLineUrl.UNSPLASH_HOST;
            default:return  "";
        }
    }

    /**
     * 发送网络请求
     * @param flowable rxjava 数据封装
     * @param callBack 请求回调
     * @param <T> 回调结果类型
     */
    @SuppressLint("CheckResult")
    public static  <T> Disposable sendRequest(Flowable<T> flowable, IHttpRequstCallBack<T> callBack){
        if(flowable != null && callBack != null){
            //将发送事件切换到子线程中去执行
            Disposable disposable = flowable.subscribeOn(Schedulers.io())
            //回调在主线程中执行
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<T>() {
                @Override
                public void accept(T result) throws Exception {
                    callBack.onSuccess(result);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    callBack.onFailure(throwable);
                }
            });

            return disposable;
        }
        return null;
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, OkHttpClient client, String baseUrl) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
