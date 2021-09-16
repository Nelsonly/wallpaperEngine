package com.nelson.myapplication;

import com.nelson.mvplibrary.net.NetCallBack;
import com.nelson.mvplibrary.net.ServiceGenerator;
import com.nelson.myapplication.api.ApiService;
import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.bean.WallhavenBean;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author nelson
 */
public class AllDatas {
    private static class AllDatasInstance{
        private static final AllDatas instance = new AllDatas();
    }
    private AllDatas(){}

    public static AllDatas getInstance(){
        return AllDatasInstance.instance;
    }
    public AndroidWallpaperBean androidWallpaperBean;
    private GetDataResult getDataResult;
    public void getAndroidWallPaper() {
        // 6 表示访问网络壁纸接口
        ApiService service = ServiceGenerator.createService(ApiService.class, 6);
        service.getWallPaper().enqueue(new NetCallBack<AndroidWallpaperBean>() {
            @Override
            public void onSuccess(Call<AndroidWallpaperBean> call, Response<AndroidWallpaperBean> response) {
                if (getDataResult != null) {
                    getDataResult.getAndroidWallpaperResult(response);
                }
            }

            @Override
            public void onFailed() {
                if (getDataResult != null) {
                    getDataResult.getDataFailed();
                }
            }
        });
    }
    public void getWallHavePaper() {
        // 6 表示访问网络壁纸接口
        ApiService service = ServiceGenerator.createService(ApiService.class, 7);
        service.getRandomWallHavenByApi().enqueue(new NetCallBack<WallhavenBean>() {
            @Override
            public void onSuccess(Call<WallhavenBean> call, Response<WallhavenBean> response) {
                if (getDataResult != null) {
                    getDataResult.getWallHaveResult(response);
                }
            }

            @Override
            public void onFailed() {
                if (getDataResult != null) {
                    getDataResult.getDataFailed();
                }
            }
        });
    }
    public void searchWallHavePaper(String searchs,String level) {
        // 6 表示访问网络壁纸接口
        ApiService service = ServiceGenerator.createService(ApiService.class, 7);
        service.searchWallHaveByApi(searchs,level).enqueue(new NetCallBack<WallhavenBean>() {
            @Override
            public void onSuccess(Call<WallhavenBean> call, Response<WallhavenBean> response) {
                if (getDataResult != null) {
                    getDataResult.getSearchHaveResult(response);
                }
            }

            @Override
            public void onFailed() {
                if (getDataResult != null) {
                    getDataResult.getDataFailed();
                }
            }
        });
    }



    public interface GetDataResult
    {
        void getWallHaveResult(Response<WallhavenBean> wallhavenBeanResponse);
        void getSearchHaveResult(Response<WallhavenBean> wallhavenBeanResponse);
        void getDataFailed();
        void getAndroidWallpaperResult(Response<AndroidWallpaperBean> response);
    }
    public void ResignInterface(GetDataResult getDataResult){
        this.getDataResult  = getDataResult;
    }
    public void UnResignInterface(){
        this.getDataResult = null;
    }
}
