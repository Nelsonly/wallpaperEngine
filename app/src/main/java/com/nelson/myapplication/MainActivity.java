package com.nelson.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.nelson.myapplication.present.IPresent;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;
import com.nelson.myapplication.adapter.DateAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager2 mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rectflow)
    TabFlowLayout flowLayout;
    List<WallpaperFragment> mFragments = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 15; i++) {
            mFragments.add(WallpaperFragment.newInStance());
        }
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(new DateAdapter(getSupportFragmentManager(), getLifecycle(),mFragments));

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