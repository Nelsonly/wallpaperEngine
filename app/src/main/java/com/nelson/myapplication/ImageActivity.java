package com.nelson.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nelson.mvplibrary.base.BaseActivity;
import com.nelson.mvplibrary.bean.WallPaper;
import com.nelson.mvplibrary.view.dialog.AlertDialog;
import com.nelson.myapplication.adapter.WallpaperAdapter;
import com.nelson.myapplication.util.BitmapUtil;
import com.nelson.myapplication.util.SaveBitmapCallBack;
import com.nelson.myapplication.util.ToastUtil;
import com.nelson.myapplication.util.StatusBarUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.nelson.myapplication.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_setting_wallpaper)
    MaterialButton btnSettingWallpaper;
    @BindView(R.id.btn_download)
    MaterialButton btnDownload;
    @BindView(R.id.vp)
    ViewPager2 vp;

    List<String> mList = new ArrayList<>();
    WallPaperAdapter mAdapter;
    String wallpaperUrl = null;

    private int position;
    private Bitmap bitmap;

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoadingDialog();
        //透明状态栏
        StatusBarUtil.transparencyBar(context);
        //获取位置
        position = getIntent().getIntExtra("position", 0);

//        Log.d("list-->", "" + mList.size());
//        if (mList != null && mList.size() > 0) {
//            for (int i = 0; i < mList.size(); i++) {
//                if ("".equals(mList.get(i).getImgUrl())) {
//                    mList.remove(i);
//                }
//            }
//        }
        mList = getIntent().getStringArrayListExtra("ImageUrl");


        mAdapter = new WallPaperAdapter(R.layout.item_image_list, mList);
        //ViewPager2实现方式
        vp.setAdapter(mAdapter);

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("position-->", "" + position);
                wallpaperUrl = mList.get(position);
                bitmap = getBitMap(wallpaperUrl);
            }
        });

        mAdapter.notifyDataSetChanged();
        vp.setCurrentItem(position, false);

        dismissLoadingDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }


    @OnClick({R.id.iv_back, R.id.btn_setting_wallpaper, R.id.btn_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //设置壁纸
            case R.id.btn_setting_wallpaper:
                //放入缓存
                SPUtils.putString(Constant.WALLPAPER_URL, wallpaperUrl, context);
                //壁纸列表
                SPUtils.putInt(Constant.WALLPAPER_TYPE, 1, context);
                ToastUtil.showShortToast(context, "已设置");
                break;
            //下载壁纸
            case R.id.btn_download:
                showLoadingDialog();
                saveImageToGallery(context, bitmap);
                break;
            default:
                break;
        }
    }

    /**
     * 壁纸适配器
     */
    public class WallPaperAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public WallPaperAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = helper.getView(R.id.wallpaper);
            Glide.with(mContext).load(item).into(imageView);
        }
    }

    /**
     * 保存图片到本地相册
     *
     * @param context 上下文
     * @param bitmap  bitmap
     * @return
     */
    public boolean saveImageToGallery(Context context, Bitmap bitmap) {

        // 首先保存图片
        String filePath = Environment.getExternalStorageDirectory().getAbsoluteFile()
                + "/" + Environment.DIRECTORY_PICTURES
                + "/" + getString(R.string.app_name);
        BitmapUtil.saveBitmapToDir((Activity) this,
                filePath, "IMG", bitmap, true, new
                        SaveBitmapCallBack() {
                            @Override
                            public void onSuccess(File file) {
                                ToastUtil.showShortToast(context, "图片保存成功");
                                dismissLoadingDialog();
                            }

                            @Override
                            public void onIOFailed(IOException exception) {
                                ToastUtil.showShortToast(context, "图片保存失败");
                                dismissLoadingDialog();
                            }

                            @Override
                            public void onCreateDirFailed() {
                                ToastUtil.showShortToast(context, "图片保存失败");
                                dismissLoadingDialog();
                            }
                        });
        return false;
    }

    /**
     * Url转Bitmap
     *
     * @param url
     * @return
     */
    public Bitmap getBitMap(final String url) {
        //新启一个线程进行转换
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return bitmap;

    }
}