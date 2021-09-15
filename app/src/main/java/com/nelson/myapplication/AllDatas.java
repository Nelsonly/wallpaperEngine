package com.nelson.myapplication;

import com.nelson.mvplibrary.net.NetCallBack;
import com.nelson.mvplibrary.net.ServiceGenerator;
import com.nelson.myapplication.api.ApiService;
import com.nelson.myapplication.bean.AndroidWallpaperBean;

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


    public interface GetDataResult
    {
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
