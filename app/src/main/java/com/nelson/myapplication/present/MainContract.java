package com.nelson.myapplication.present;


import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.api.ApiService;
import com.nelson.myapplication.bean.WallhavenBean;
import com.nelson.mvplibrary.base.BasePresenter;
import com.nelson.mvplibrary.base.BaseView;
import com.nelson.mvplibrary.net.NetCallBack;
import com.nelson.mvplibrary.net.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Response;
public class MainContract {
    public  static class MainPresent extends BasePresenter<IMainView>{
        public void getAndroidWallPaper() {
            // 6 表示访问网络壁纸接口
            ApiService service = ServiceGenerator.createService(ApiService.class, 6);
            service.getWallPaper().enqueue(new NetCallBack<AndroidWallpaperBean>() {
                @Override
                public void onSuccess(Call<AndroidWallpaperBean> call, Response<AndroidWallpaperBean> response) {
                    if (getView() != null) {
                        getView().getAndroidWallpaperResult(response);
                    }
                }

                @Override
                public void onFailed() {
                    if (getView() != null) {
                        getView().getDataFailed();
                    }
                }
            });
        }
        public void getWallHavePaper() {
            // 6 表示访问网络壁纸接口
            ApiService service = ServiceGenerator.createService(ApiService.class, 7);
            service.getWallHave().enqueue(new NetCallBack<WallhavenBean>() {
                @Override
                public void onSuccess(Call<WallhavenBean> call, Response<WallhavenBean> response) {
                    if (getView() != null) {
                        getView().getWallHaveResult(response);
                    }
                }

                @Override
                public void onFailed() {
                    if (getView() != null) {
                        getView().getDataFailed();
                    }
                }
            });
        }
        public void searchWallHavePaper(String searchs) {
            // 6 表示访问网络壁纸接口
            ApiService service = ServiceGenerator.createService(ApiService.class, 7);
            service.searchWallHaveByApi(searchs).enqueue(new NetCallBack<WallhavenBean>() {
                @Override
                public void onSuccess(Call<WallhavenBean> call, Response<WallhavenBean> response) {
                    if (getView() != null) {
                        getView().getWallHaveResult(response);
                    }
                }

                @Override
                public void onFailed() {
                    if (getView() != null) {
                        getView().getDataFailed();
                    }
                }
            });
        }


    }
    public interface  IMainView extends BaseView{
        void getAndroidWallpaperResult(Response<AndroidWallpaperBean> androidWallpaperBeanResponse);
        void getWallHaveResult(Response<WallhavenBean> wallhavenBeanResponse);
        void getSearchHaveResult(Response<WallhavenBean> wallhavenBeanResponse);
        void getDataFailed();

    }
}
