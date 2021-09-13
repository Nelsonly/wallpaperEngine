package com.irigel.common.network.retrofit.service;


import com.irigel.common.network.HttpConstants;

import java.util.List;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zhangqing
 * 服务范例
 */
public interface MeteralService {
    /**
     * 网络请求范例
     *
     * https://api.unsplash.com/photos?client_id=tU03by1oIgUu8BDpMhPSejFa7YjERFNj3n43c2TXn5c&page=1&per_page=10
     */

    /**
     *
     * @param page 多少页
     * @param pageSize 每页返回多少值
     * @return  UnSplashPhotoResult实体
     */
//    @GET("/photos?client_id="+HttpConstants.Key.UNSPLASH_KEY)
//    Flowable<List<FallsMeterial>> getPhoto(@Query(HttpConstants.Param.PAGE) int page, @Query(HttpConstants.Param.PAGE_SIZE) int pageSize);
//
//
//    @GET("/photos/random?client_id="+HttpConstants.Key.UNSPLASH_KEY)
//    Flowable<List<UnSplashRandomPhotoResult>> getRandomPhoto();
//
//    /**
//     *
//     * @param page 多少页
//     * @param pageSize 一页多少个
//     * @return  UnSplashTopicsResult实体
//     */
//
//    @GET("/topics?client_id="+HttpConstants.Key.UNSPLASH_KEY)
//    Flowable<List<UnSplashTopicsResult>> getTopics(@Query(HttpConstants.Param.PAGE) int page, @Query(HttpConstants.Param.PAGE_SIZE) int pageSize);
//
//    /**
//     *
//     * @param topic 主题
//     * @param page  多少页
//     * @param pageSize  一页多少个
//     * @return UnSplashTopicPhotoResult
//     */
//    @GET("/topics/{topic}/photos?client_id="+HttpConstants.Key.UNSPLASH_KEY)
//    Flowable<List<UnSplashTopicPhotoResult>> getTopicPhoto(@Path(HttpConstants.Path.TOPIC) String topic, @Query(HttpConstants.Param.PAGE) int page, @Query(HttpConstants.Param.PAGE_SIZE) int pageSize);


//    @GET(HttpConstants.BaseUrl.UNSPLASH_HOST+"search/photos?client_id="+HttpConstants.Key.UNSPLASH_KEY)
//    Flowable<Result<List<>>>


//    @FormUrlEncoded
//    @POST(HttpConstants.Path.SETTING)
//    Flowable<TestEntity> setting(
//            @Field(HttpConstants.Param.TOKEN) String loginToken,
//            @Field(HttpConstants.Param.TYPE) String source
//    );
//    @FormUrlEncoded
//    @POST(HttpConstants.Path.SETTING)
//    Flowable<MeterialEntity> setting(
//            @Field(HttpConstants.Param.TOKEN) String loginToken,
//            @Field(HttpConstants.Param.TYPE) String source
//    );



}
