package com.nelson.myapplication.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.nelson.myapplication.bean.WallhavenBean;
import com.nelson.myapplication.bean.AndroidWallpaperBean;
import static com.nelson.myapplication.Constant.WallHaveAPiKey;
/**
 * API服务接口
 *
 * @author nelson
 */
public interface ApiService {
    /**
     * 必应每日一图
     *
     * @return BiYingImgResponse 必应壁纸返回
     */
//    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1")
//    Call<BiYingImgResponse> biying();


    /**
     * 手机壁纸API
     *
     * @return WallPaperResponse 网络壁纸数据返回
     */
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Call<AndroidWallpaperBean> getWallPaper();

    @GET("/api/v1/search")
    Call<WallhavenBean> getWallHave();

    @GET("/api/v1/search?apikey="+WallHaveAPiKey+"&purity=0")
    Call<WallhavenBean> searchWallHaveByApi(@Query("p") String search);

    @GET("/api/v1/search?apikey="+WallHaveAPiKey+"&purity=0")
    Call<WallhavenBean> getWallHaveByApi();

    @GET("/api/v1/search?apikey="+WallHaveAPiKey+"&purity=0"+"&sorting=random")
    Call<WallhavenBean> getRandomWallHavenByApi();
}
