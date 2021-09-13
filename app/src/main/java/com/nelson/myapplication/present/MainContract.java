package com.nelson.myapplication.present;

import com.nelson.mvplibrary.base.BasePresenter;
import com.nelson.mvplibrary.base.BaseView;
import com.nelson.myapplication.bean.AndroidWallpaperBean;

import retrofit2.Response;

public class MainContract {
    public  static class MainPresent extends BasePresenter<IMainView>{
        public void getAndroidWallpaper(){

        }




    }
    public interface  IMainView extends BaseView{
        void getAndroidWallpaperResult(Response<AndroidWallpaperBean> androidWallpaperBeanResponse);
        void getDataFailed();

    }
}
