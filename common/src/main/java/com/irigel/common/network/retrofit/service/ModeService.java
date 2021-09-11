package com.irigel.common.network.retrofit.service;

import com.irigel.common.entity.FallsModel;
import com.irigel.common.entity.ModelEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangqing on 3/11/21.
 */
public interface ModeService {
    //https://fig-service.atcloudbox.com//packages/album_content?version=1&bundle=com.casaba.cn
    // http://52.83.76.155:8136/packages/album_content?version=1&bundle=com.casaba.cn
    @GET("/packages/album_content")
    Flowable<ModelEntity> getData(@Query("version") int version , @Query("bundle") String bundle);
}
