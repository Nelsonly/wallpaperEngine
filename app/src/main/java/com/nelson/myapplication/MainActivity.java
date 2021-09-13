package com.nelson.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.nelson.mvplibrary.mvp.MvpActivity;
import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.present.MainContract;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;
import com.nelson.myapplication.adapter.DateAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;


public class MainActivity extends MvpActivity<MainContract.MainPresent> implements MainContract.IMainView {

    @BindView(R.id.viewpager)
    ViewPager2 mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rectflow)
    TabFlowLayout flowLayout;
    List<WallpaperFragment> mFragments = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {

        for (int i = 0; i < 1; i++) {
            mFragments.add(WallpaperFragment.newInStance("Shh"));
        }
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(new DateAdapter(getSupportFragmentManager(), getLifecycle(),mFragments));

    }

    @Override
    protected MainContract.MainPresent createPresent() {
        return new MainContract.MainPresent();
    }

    @Override
    public void getAndroidWallpaperResult(Response<AndroidWallpaperBean> androidWallpaperBeanResponse) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getDataFailed() {

    }

    private void rectFlow(List<String> mTitle2) {
        flowLayout.setViewPager(mViewPager)
                .setTextId(R.id.item_text)
                .setSelectedColor(Color.WHITE)
                .setDefaultPosition(0)
                .setUnSelectedColor(getResources().getColor(Color.LTGRAY));
        flowLayout.setAdapter(new TabFlowAdapter<String>(R.layout.item_tab_text, mTitle2) {
            //            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void bindView(View view, String data, int position) {
                TextView textView = view.findViewById(R.id.item_text);
                textView.setText(data);
            }
            @Override
            public void onItemClick(View view, String data, int position) {
                super.onItemClick(view, data, position);
                mViewPager.setCurrentItem(position);
            }
        });
    }

}