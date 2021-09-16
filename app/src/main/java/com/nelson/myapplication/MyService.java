package com.nelson.myapplication;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.bean.WallhavenBean;
import com.nelson.myapplication.present.MainContract;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Response;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AllDatas.getInstance().ResignInterface(new AllDatas.GetDataResult() {
            @Override
            public void getDataFailed() {

            }

            @Override
            public void getAndroidWallpaperResult(Response<AndroidWallpaperBean> response) {
                Intent intent1 = new Intent();
                intent1.setAction(NewAppWidget.ACTION_URL);
                intent1.setClassName(getPackageName(),"com.nelson.myapplication.NewAppWidget");
                intent1.putExtra("url",response.body().res.vertical.get(0).thumb);
//        intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                getApplicationContext().sendBroadcast(intent1);
            }

            @Override
            public void getWallHaveResult(Response<WallhavenBean> wallhavenBeanResponse) {
                Intent intent1 = new Intent();
                intent1.setAction(NewAppWidget.ACTION_URL);
                intent1.setClassName(getPackageName(),"com.nelson.myapplication.NewAppWidget");
                intent1.putExtra("url",wallhavenBeanResponse.body().data.get(0).path);
//        intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                getApplicationContext().sendBroadcast(intent1);
            }

            @Override
            public void getSearchHaveResult(Response<WallhavenBean> wallhavenBeanResponse) {

            }
        });
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    AllDatas.getInstance().getWallHavePaper();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, 20000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}