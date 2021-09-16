package com.nelson.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.nelson.mvplibrary.mvp.MvpActivity;
import com.nelson.myapplication.bean.AndroidWallpaperBean;
import com.nelson.myapplication.bean.WallhavenBean;
import com.nelson.myapplication.present.MainContract;
import com.nelson.myapplication.util.SPUtils;
import com.nelson.myapplication.util.ToastUtil;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;
import com.nelson.myapplication.adapter.DateAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;


public class MainActivity extends MvpActivity<MainContract.MainPresent> implements MainContract.IMainView {

    @BindView(R.id.viewpager)
    ViewPager2 mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rectflow)
    TabFlowLayout flowLayout;
    @BindView(R.id.search_text)
    EditText editText;
    List<WallpaperFragment> mFragments = new ArrayList<>();
    List<String> imgUrl = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {
        mPresent.getAndroidWallPaper();
        mPresent.getWallHavePaper();

        for (int i = 0; i < 3; i++) {
            mFragments.add(WallpaperFragment.newInStance("Shh"));
            titles.add("sdsads");
        }

        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(new DateAdapter(getSupportFragmentManager(), getLifecycle(),mFragments));
        rectFlow(titles);
    }

    @Override
    protected MainContract.MainPresent createPresent() {
        return new MainContract.MainPresent();
    }


    @Override
    public void getAndroidWallpaperResult(Response<AndroidWallpaperBean> androidWallpaperBeanResponse) {
        List<AndroidWallpaperBean.ResDTO.VerticalDTO> verticalDTOS =  androidWallpaperBeanResponse.body().res.vertical;

        for(int i=0;i<verticalDTOS.size();i++){
            imgUrl.add(verticalDTOS.get(i).img);
        }
        mFragments.get(0).setImgUrl(imgUrl);

        Intent intent1 = new Intent();
        intent1.setAction(NewAppWidget.ACTION_URL);
        intent1.setClassName(this,"com.nelson.myapplication.NewAppWidget");
        intent1.putExtra("url",androidWallpaperBeanResponse.body().res.vertical.get(0).thumb);
//        intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getDataFailed() {

    }
    @OnClick({R.id.but_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_search:
                String ss = editText.getText().toString();
                String level;
                switch (ss.charAt(0)){
                    case '0':
                        level = "100";
                        ss =ss.substring(1,ss.length());
                        break;
                    case '1':
                        level = "110";
                        ss =ss.substring(1,ss.length());
                        break;
                    case '2':
                        level = "111";
                        ss =ss.substring(1,ss.length());
                        break;
                    case '3':
                        level = "011";
                        ss =ss.substring(1,ss.length());
                        break;
                    case '4':
                        level = "001";
                        ss =ss.substring(1,ss.length());
                        break;
                    default:
                        level = "100";
                        break;
                }
                mPresent.searchWallHavePaper(ss,level);
                break;
        }
    }

    private void rectFlow(List<String> mTitle2) {
        flowLayout.setViewPager(mViewPager)
                .setTextId(R.id.item_text)
                .setSelectedColor(Color.BLUE)
                .setDefaultPosition(0)
                .setUnSelectedColor(Color.BLACK);
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

    @Override
    public void getWallHaveResult(Response<WallhavenBean> wallhavenBeanResponse) {
        List<WallhavenBean.DataDTO> dataDTOS = wallhavenBeanResponse.body().data;
        List<String> urlList = new ArrayList<>();
        for (int i =0;i<dataDTOS.size();i++){
            urlList.add(dataDTOS.get(i).path);
        }
        mFragments.get(1).setImgUrl(urlList);
    }

    @Override
    public void getSearchHaveResult(Response<WallhavenBean> wallhavenBeanResponse) {
        List<WallhavenBean.DataDTO> dataDTOS = wallhavenBeanResponse.body().data;
        List<String> urlList = new ArrayList<>();
        for (int i =0;i<dataDTOS.size();i++){
            urlList.add(dataDTOS.get(i).path);
        }
        mFragments.get(2).setImgUrl(urlList);
    }
}