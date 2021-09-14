package com.nelson.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nelson.mvplibrary.base.BaseFragment;
import com.nelson.myapplication.adapter.WallpaperAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WallpaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WallpaperFragment extends BaseFragment {

    private static String chose ;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    WallpaperAdapter wallpaperAdapter;
    List<String> imgUrls = new ArrayList<>();
    public WallpaperFragment() {
        // Required empty public constructor
    }


    @Override
    public void initData(Bundle savedInstanceState) {
//        recyclerView.setAdapter(new WallpaperAdapter(R.layout.item_image_list));
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallpaper;
    }

    /**
     * 弄一个静态工厂的方法调用 用于传参
     *
     * @param
     * @return
     */
    public static WallpaperFragment newInStance(String chose) {
        return new WallpaperFragment();
    }
    public void setImgUrl(List<String> imgUrls){
        this.imgUrls = imgUrls;
        wallpaperAdapter = new WallpaperAdapter(R.layout.item_wallpaper_list,imgUrls);

        wallpaperAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //这里的列表数据实际上有32条，有两条假数据，就是首尾这两条，所以点击的时候要做判断处理
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("position", position - 1);
                    intent.putStringArrayListExtra("ImageUrl", (ArrayList<String>) imgUrls);
                    startActivity(intent);
            }
        });

        recyclerView.setAdapter(wallpaperAdapter);

    }

}