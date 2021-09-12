package com.nelson.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.nelson.myapplication.present.IPresent;
import com.zhengsr.tablib.view.flow.TabFlowLayout;

import butterknife.BindView;


public class MainActivity extends BaseActivity<IPresent> {

    @BindView(R.id.viewpager)
    ViewPager2 viewPager2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rectflow)
    TabFlowLayout tabFlowLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}