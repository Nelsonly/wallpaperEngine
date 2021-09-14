package com.nelson.myapplication.adapter;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import java.util.List;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.imageview.ShapeableImageView;
import com.nelson.myapplication.R;

public class WallpaperAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    //定义一个item的高度列表
    List<Integer> mHeightList;
    Context context;
    public WallpaperAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public WallpaperAdapter(@Nullable List<String> data) {
        super(data);
    }

    public WallpaperAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ShapeableImageView imageView = helper.getView(R.id.wallpaper);

        //获取imageView的LayoutParams
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//        layoutParams.height = dip2px(mHeightList.get(helper.getAdapterPosition()));
//        //重新设置imageView的高度
//        imageView.setLayoutParams(layoutParams);

//        if (Top.equals(item.getDesc())) {
//            imageView.setImageResource(R.mipmap.icon_top_wallpaper);
////        } else if (Bottom.equals(item.getDesc())) {
////            imageView.setImageResource(R.mipmap.icon_bottom_wallpaper);
//        } else {
            Glide.with(mContext).load(item).into(imageView);
//        }
//
        helper.addOnClickListener(R.id.item_wallpaper);
    }


    // dp 转成 px
    private int dip2px(float dpVale) {

        final float scale = context.getResources().getDisplayMetrics().density;;
        return (int) (dpVale * scale + 0.5f);
    }

}
