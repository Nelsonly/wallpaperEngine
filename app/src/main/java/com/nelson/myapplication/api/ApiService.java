package com.nelson.myapplication.api;

package com.nelson.weather.api;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


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
    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1")
    Call<BiYingImgResponse> biying();


    /**
     * 手机壁纸API
     *
     * @return WallPaperResponse 网络壁纸数据返回
     */
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Call<WallPaperResponse> getWallPaper();
}
