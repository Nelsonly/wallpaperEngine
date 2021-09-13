package com.irigel.common.network.retrofit.service;


import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhangqing on 3/11/21.
 */
public interface ModeService {
    //https://fig-service.atcloudbox.com//packages/album_content?version=2&bundle=com.casaba.cn
    // http://52.83.76.155:8136/packages/album_content?version=2&bundle=com.casaba.cn
//    @GET("/packages/album_content")
//    Flowable<ModelBean> getData(@Query("version") int version , @Query("bundle") String bundle);


//    @Streaming
//    @GET
//    Flowable<ResponseBody> downloadZipFile(@Url String fileUrl);
//
//    @Multipart
//    @POST("upload")
//    Flowable<FlashBean> uploadFile(@PartMap Map<String, RequestBody> bodyMap);
//
//    @GET("download/{tokenId}")
//    Flowable<ResponseBody> downloadCartoonPic(@Path("tokenId") String tokenId);

//    @POST
//    Flowable<ResponseBody> uploadFile(@Url String url, @Body MultipartBody body);
}
