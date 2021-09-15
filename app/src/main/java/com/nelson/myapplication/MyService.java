package com.nelson.myapplication;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.bean.WallhavenBean;
import com.nelson.myapplication.present.MainContract;

import retrofit2.Response;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        AppWidgetManager.getInstance(this).updateAppWidget();
        AllDatas.getInstance().ResignInterface(new AllDatas.GetDataResult() {
            @Override
            public void getDataFailed() {

            }

            @Override
            public void getAndroidWallpaperResult(Response<AndroidWallpaperBean> response) {
                new NewAppWidget();
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}