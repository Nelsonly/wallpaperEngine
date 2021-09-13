package com.irigel.common.network.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.irigel.common.BuildConfig;
import com.irigel.common.Constants;
import com.irigel.common.R;
import com.irigel.common.network.HttpConstants;
import com.irigel.common.network.interfaces.IHttpRequstCallBack;
import com.irigel.common.network.interfaces.IOTask;
import com.irigel.common.network.okhttp.OkHttpClientManager;
import com.irigel.common.utils.AppFileUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.irigel.common.network.HttpConstants.TAG_CARTOON_HOST;
import static com.irigel.common.network.HttpConstants.TAG_MODE_ZIP_HOST;

/**
 * @author zhangqing
 */
public class HttpRequestManager {

    private static final String TAG = "HttpRequestManager";
    public static final int STATUS_CODE_UNZIP_SUCESS = 0;
    private static final int CARTOON_REQEUST_MAX_TRY_COUNT = 30;
    private static final int CARTOON_INTERVAL_WAIT_TIME = 5000;
    private int cartCount;
    private String lock = "lock";

    private static class RequestHolder {
        private static HttpRequestManager INSTANCE = new HttpRequestManager();
    }

    private HttpRequestManager() {

    }

    public static HttpRequestManager getInstance() {
        return RequestHolder.INSTANCE;
    }


    /**
     * 创建retrofitService对象
     * @param clazz retrofit Service 类字节码
     * @param hostType 主机类型
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clazz, String hostType) {
        String baseUrl = getHost(hostType);
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClientManager.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 发送网络请求,在io线程中callback
     *
     * @param callBack 请求回调
     * @param <T>      回调结果类型
     */
    @SuppressLint("CheckResult")
    <T> Disposable sendRequestOnIOCallback(Flowable flowable, IHttpRequstCallBack<T> callBack) {
        if (flowable != null && callBack != null) {
            //将发送事件切换到子线程中去执行
            Disposable disposable = flowable.subscribeOn(Schedulers.io())
                    //回调在子线程中执行
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<T>() {
                        @Override
                        public void accept(T result) {
                            if(callBack != null){
                                callBack.onSuccess(result);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            if(callBack != null){
                                callBack.onFailure(throwable);
                            }
                        }
                    });

            return disposable;
        }
        return null;
    }


    /**
     * 发送网络请求，并在UI线程回调
     *
     * @param callBack 请求回调
     * @param <T>      回调结果类型
     */
    @SuppressLint("CheckResult")
    <T> Disposable sendRequestOnUICallback(Flowable flowable, IHttpRequstCallBack<T> callBack) {
        if (flowable != null && callBack != null) {
            //将发送事件切换到子线程中去执行
            Disposable disposable = flowable.subscribeOn(Schedulers.io())
                    //回调在子线程中执行
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<T>() {
                        @Override
                        public void accept(T result){
                            if(callBack != null && result != null){
                                callBack.onSuccess(result);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable){
                            if(callBack != null){
                                callBack.onFailure(throwable);
                            }
                        }
                    });

            return disposable;
        }
        return null;
    }


    /**
     * 发送下载文件请求，并在Ui线程回调
     * @param flowable
     * @param callBack    UI线程回调
     * @param ioTask      IO线程回调
     * @param FilePath     压缩路径
     * @param FileName 压缩包名称
     * @return
     * @throws Exception
     */
    public Disposable sendLoadFileRequestOnUiCallBack(Flowable<ResponseBody> flowable, IHttpRequstCallBack<Boolean> callBack, IOTask ioTask, String FilePath, String FileName) throws Exception {
        if (flowable == null) {
            return null;
        }
        Disposable disposable = flowable
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ResponseBody responseBody){
                        InputStream inputStream = responseBody.byteStream();
                        if (inputStream == null) {
                            return false;
                        }
                        //io线程保存文件
                        AppFileUtils.saveFile(inputStream, FilePath, FileName);

                        //执行其他异步任务
                        if (ioTask != null) {
                            ioTask.doTask();
                        }
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                //回调在子线程中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean requestSuccess) {
                        if (callBack != null) {
                            callBack.onSuccess(requestSuccess);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable){
                        if (callBack != null) {
                            callBack.onFailure(throwable);
                        }
                    }
                });
        return disposable;
    }



    /**
     * 发送下载卡通图片请求
     *
     * @param flowable
     * @param callBack UI线程回调
     * @return
     * @throws Exception
     */
    public Disposable sendCartoonRequestOnUICallBack(Context context, Flowable<ResponseBody> flowable, IHttpRequstCallBack<Bitmap> callBack) {
        if (flowable == null) {
            return null;
        }
        Disposable disposable = flowable
            .map(new Function<ResponseBody, Bitmap>() {
                @Override
                public Bitmap apply(@NonNull ResponseBody responseBody)  {
                    cartCount++;
                    //返回结果是图片
                    if (Constants.MEDIA_TYPE_IMAGE.equals(responseBody.contentType().type())) {
                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        cartCount = 0;
                        return bitmap;
                     //返回结果是文字
                    } else {
                        try {
                            String body = new String(responseBody.bytes());
                            Log.i(TAG,"body = " + body);
                            if(!TextUtils.isEmpty(body)){
                                JSONObject json = new JSONObject(body);
                                String failReason = json.optString("fail_reason");
                                //返回文字为fail信息,回调错误信息
                                if(!TextUtils.isEmpty(failReason)){
                                    callBack.onFailure(new Throwable(failReason));
                                    cartCount = 0;
                                    return null;
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //返回结果为文字，且非错误信息时，轮询请求
                        if (cartCount < CARTOON_REQEUST_MAX_TRY_COUNT) {
                            try {
                                synchronized (lock) {
                                    lock.wait(CARTOON_INTERVAL_WAIT_TIME);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            sendCartoonRequestOnUICallBack(context,flowable, callBack);
                        }
                    }
                    return null;
                }
            })
            .subscribeOn(Schedulers.io())
            //回调在子线程中执行
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap result){
                    if (callBack != null && result != null) {
                            callBack.onSuccess(result);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    if(callBack != null){
                        if(cartCount >= CARTOON_REQEUST_MAX_TRY_COUNT ){
                            callBack.onFailure(new Throwable(context.getString("5")));
                            cartCount = 0;
                            return;
                        }
                        //轮询的过程reture bitmap==null 不是异常，不回调
                        if(!throwable.getMessage().contains("The mapper function returned a null value")){
                            callBack.onFailure(throwable);
                        }
                    }
                }
            });
           return disposable;
    }


    /**
     * 获取host
     *
     * @param hostType
     * @return
     */
    private static String getHost(String hostType) {
        if (TextUtils.isEmpty(hostType)) {
            return null;
        }
        switch (hostType) {
            case HttpConstants.TAG_MODE_HOST:
                if (BuildConfig.DEBUG) {
                    //测试服务器挂了，暂时用正式服务器测试
                    return HttpConstants.OnLineUrl.MODE_HOST;
                } else {
                    return HttpConstants.OnLineUrl.MODE_HOST;
                }
            case HttpConstants.TAG_UNSPLASH_HOST:
                return HttpConstants.OnLineUrl.UNSPLASH_HOST;
            case TAG_MODE_ZIP_HOST:
                return HttpConstants.OnLineUrl.MODE_HOST;
            case TAG_CARTOON_HOST:
                if (BuildConfig.DEBUG) {
                    return HttpConstants.DevUrl.FLASH_HOST;
                } else {
                    return HttpConstants.OnLineUrl.FLASH_HOST;
                }
            default:
                return "";
        }
    }

}
