package com.irigel.common.utils.photopicker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.engine.ImageEngine;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对相册框架EasyPhotos的封装，实现图片选择界面，无需处理权限问题
 * 返回结果在回调中接收，使用时需实现接口的方法（不同方法接口不同）
 * @author lixuan
 */
public class PhotoPickerUtils {

    private static OnPickOnePhotoResultListener onPickOnePhotoResultListener;
    private static OnPickPhotosResultListener onPickPhotosResultListener;
    private static OnPickOneBitmapPhotoResultListener onPickOneBitmapPhotoResultListener;

    public static void pickOnePhotoUsingUri(Activity activity) {
        onPickOnePhotoResultListener = (OnPickOnePhotoResultListener) activity;

        EasyPhotos.createAlbum(activity, false, GlideEngine.getInstance())
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        onPickOnePhotoResultListener.onPickOnePhotoResultListener(photos.get(0).uri);
                    }
                });
    }

    /**
     * 选择一张照片，需实现OnPickOneBitmapPhotoResultListener，回调中以Bitmap形式返回图片
     * @param activity 当前activity
     */
    public static void pickOnePhotoUsingBitmap(Activity activity) {
        onPickOneBitmapPhotoResultListener = (OnPickOneBitmapPhotoResultListener) activity;

        EasyPhotos.createAlbum(activity, false, GlideEngine.getInstance())
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .start(new SelectCallback() {
                           @Override
                           public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                               // Uri转bitmap
                               try {
                                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), photos.get(0).uri);
                                   onPickOneBitmapPhotoResultListener.onPickOnePhotoResultListener(bitmap);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }

                       }
                );
    }

    /**
     * 选择多张照片，需实现OnPickPhotosResultListener接口，回调中以List<Uri>形式返回选中照片的uri
     * @param activity 当前activity
     */
    public static void pickPhotosUsingUri(Activity activity) {
        onPickPhotosResultListener = (OnPickPhotosResultListener) activity;
        EasyPhotos.createAlbum(activity, false, GlideEngine.getInstance())
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .setCount(9)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        List<Uri> list = new ArrayList<>();
                        for (Photo photo : photos) {
                            list.add(photo.uri);
                        }
                        onPickPhotosResultListener.onPickOnePhotoResultListener(list);
                    }
                });
    }

    /**
     * 封装好的带底部预览的图片选择界面（多选），需要activity实现PuzzleSelectorActivity2.OnSelectPhotosForPuzzleDoneListener接口
     * @param activity listener
     */
    public static void pickPhotosForPuzzleUsingUri(Activity activity){
        EasyPhotos.startSelectorForPuzzle(activity, GlideEngine.getInstance());
    }

    public interface OnPickOnePhotoResultListener {
        /**
         * 封装的选择照片界面的结果回调
         * @param uri 选择单张照片的回调
         */
        void onPickOnePhotoResultListener(Uri uri);
    }

    public interface OnPickOneBitmapPhotoResultListener {
        /**
         * 封装的选择照片界面的结果回调
         * @param bitmap 选择单张照片的，返回Bitmap的回调
         */
        void onPickOnePhotoResultListener(Bitmap bitmap);
    }

    public interface OnPickPhotosResultListener {
        /**
         * 封装的选择照片界面的结果回调
         * @param list 选择多张照片的回调
         */
        void onPickOnePhotoResultListener(List<Uri> list);
    }


}
