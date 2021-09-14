package com.nelson.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nelson.mvplibrary.base.BaseFragment;
import com.nelson.myapplication.adapter.WallpaperAdapter;

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
        recyclerView.setAdapter(new WallpaperAdapter(R.layout.item_image_list,imgUrls));
    }

}