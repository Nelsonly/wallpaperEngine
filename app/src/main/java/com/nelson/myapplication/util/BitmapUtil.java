package com.nelson.myapplication.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BitmapUtil {
    /**
     * 保存Bitmap到指定文件夹
     *
     * @param act         上下文
     * @param dirPath     文件夹全路径
     * @param bitmap      bitmap
     * @param namePrefix  保存文件的前缀名，文件最终名称格式为：前缀名+自动生成的唯一数字字符+.png
     * @param notifyMedia 是否更新到媒体库
     * @param callBack    保存图片后的回调，回调已经处于UI线程
     */
    public static void saveBitmapToDir(final Activity act, final String dirPath,
                                       final String namePrefix, final Bitmap bitmap,
                                       final boolean notifyMedia,
                                       final SaveBitmapCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    //android10+
                    saveBitmapToDirQ(act, dirPath, namePrefix, bitmap, notifyMedia, callBack);
                    return;
                }

                File dirF = new File(dirPath);
                if (!dirF.exists() || !dirF.isDirectory()) {
                    if (!dirF.mkdirs()) {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onCreateDirFailed();
                            }
                        });
                        return;
                    }
                }
                try {
                    final File writeFile = File.createTempFile(namePrefix, ".png", dirF);

                    FileOutputStream fos = null;
                    fos = new FileOutputStream(writeFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    if (notifyMedia) {
                        notifyMedia(act, writeFile);
                    }
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(writeFile);
                        }
                    });

                } catch (final IOException e) {
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onIOFailed(e);
                        }
                    });

                }
            }
        }).start();

    }
    private static void saveBitmapToDirQ(final Activity act, final String dirPath,
                                         final String namePrefix, final Bitmap bitmap,
                                         final boolean notifyMedia,
                                         final SaveBitmapCallBack callBack) {
        long dataTake = System.currentTimeMillis();
        String jpegName = namePrefix + dataTake + ".png";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, jpegName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        int dirIndex = dirPath.lastIndexOf("/");
        if (dirIndex == dirPath.length()) {
            String dirPath2 = dirPath.substring(0, dirIndex - 1);
            dirIndex = dirPath2.lastIndexOf("/");
        }
        String dirName = dirPath.substring(dirIndex + 1);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/" + dirName);

        Uri external;
        ContentResolver resolver = act.getContentResolver();
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else {
            external = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        }

        final Uri insertUri = resolver.insert(external, values);
        if (insertUri == null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onCreateDirFailed();
                }
            });
            return;
        }
        OutputStream os;
        try {
            os = resolver.openOutputStream(insertUri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            if (os != null) {
                os.flush();
                os.close();
            }
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String uriPath = UriUtils.getPathByUri(act, insertUri);
                    if (null == uriPath) {
                        callBack.onCreateDirFailed();
                    }else {
                        callBack.onSuccess(new File(uriPath));
                    }
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onIOFailed(e);
                }
            });
        }
    }

    //**************更新媒体库***********************

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt       上下文
     * @param filePaths 更新的文件地址
     */
    public static void notifyMedia(Context cxt, String... filePaths) {
        MediaScannerConnectionUtils.refresh(cxt, filePaths);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt   上下文
     * @param files 更新的文件
     */
    public static void notifyMedia(Context cxt, File... files) {
        MediaScannerConnectionUtils.refresh(cxt, files);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt      上下文
     * @param fileList 更新的文件地址集合
     */
    public static void notifyMedia(Context cxt, List<String> fileList) {
        MediaScannerConnectionUtils.refresh(cxt, fileList);
    }

}
