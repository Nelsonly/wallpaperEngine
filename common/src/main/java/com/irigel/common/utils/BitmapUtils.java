package com.irigel.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.irigel.common.entity.BitmapEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * @author zhangqing
 * @date 3/3/21
 */
public class BitmapUtils {

    public static final String TAG = "ImageUtils";

    /**
     * 获取旋转正的bitmap
     *
     * @param
     * @return
     */
    public static int getRotatedAngle(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        //获取图片旋转的角度
        int angle = 0;
        try {
            Log.i(TAG, "getRotatedAngle path = " + path);
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return angle;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        Log.i(TAG, "rotateBitmap angle =" + angle);
        try {
            //根据角度将图片旋转
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        } catch (Exception e) {
            Log.e(TAG, "获取图片旋转的角度报错:" + e.getMessage());
        }
        return null;
    }

    /**
     * 根据模板抠出原图
     *
     * @param srcBitmap  原图
     * @param maskBitmap 模板
     * @return 抠出的图
     */
    public static Bitmap segResultSrcBitmap(Bitmap srcBitmap, Bitmap maskBitmap) {
        int srcBmpHeight;
        int srcBmpWidth;
        int srcColorArrayLength;
        int[] srcColorArray;
        int count = 0;

        srcBmpWidth = srcBitmap.getWidth();
        srcBmpHeight = srcBitmap.getHeight();

        srcColorArrayLength = srcBmpWidth * srcBmpHeight;
        srcColorArray = new int[srcColorArrayLength];
        for (int i = 0; i < maskBitmap.getHeight(); i++) {
            for (int j = 0; j < maskBitmap.getWidth(); j++) {
                //获得maskBitmap 图片中每一个点的color颜色值
                int color = maskBitmap.getPixel(j, i);
                int a = Color.alpha(color);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                if (a == 0) {
                    a = 0;
                    r = 255;
                    g = 210;
                    b = 220;
                    color = Color.argb(a, r, g, b);
                } else {
                    color = srcBitmap.getPixel(j, i);
                }

                srcColorArray[count] = color;
                count++;
            }
        }
        Bitmap targetBitmap = Bitmap.createBitmap(srcColorArray, srcBmpWidth, srcBmpHeight, Bitmap.Config.ARGB_8888);
        return targetBitmap;
    }

//    /**
//     * @param bitmap bitmap对象
//     * @return 返回字节数组
//     * @throws IOException .close()产生的异常
//     */
//    public static byte[] bitmapToByteArray(Bitmap bitmap) {
//        if (bitmap == null) {
//            return null;
//        }
//        try {
//            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
//            byte[] bitmapByteArray = localByteArrayOutputStream.toByteArray();
//            localByteArrayOutputStream.close();
//            return bitmapByteArray;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 字节数组转bitmap
     *
     * @param bitmapByteArray 传入的二进制数组
     * @return 返回bitmap对象
     */
    public static Bitmap byteArrayToBitmap(byte[] bitmapByteArray) {
        return BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.length);
    }

    /**
     * 根据uri获取bitmap
     *
     * @param contentResolver contentResolver
     * @param uri             uri
     * @return
     */
    public static Bitmap getBitmapByUri(ContentResolver contentResolver, Uri uri) {
        if (contentResolver == null || uri == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            InputStream input = contentResolver.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * @param context
     * @param maskBitmap
     * @param parentWidth
     * @param parentHeight
     * @return
     */
    public static BitmapEntity getFitCenterSizeInfo(Context context, Bitmap maskBitmap, int parentWidth, int parentHeight) {
        if (maskBitmap == null || parentWidth == 0 || parentHeight == 0) {
            return null;
        }
        BitmapEntity entity = new BitmapEntity();
        int maskWidth = maskBitmap.getWidth();
        int maskHeight = maskBitmap.getHeight();
        int maskDisplayWidth = 0;
        int maskDisplayHeight = 0;

        Log.i(TAG,  "getFitCenterWidth layoutContainerHeight = " + parentHeight);
        Log.i(TAG,  "getFitCenterWidth layoutContainerWidth = " + parentWidth);
        float maskRatio = maskHeight / (float) maskWidth;
        float ivSegRatio = parentHeight / (float) parentWidth;

        Log.i(TAG,  "getFitCenterWidth maskHeight/maskWidth = " + maskRatio);
        Log.i(TAG, "getFitCenterWidth layoutContainerHeight/layoutContainerWidth = " + ivSegRatio);
        Rect tarRect;
        //图像左上角x、y坐标点相对值
        int leftX = 0, topY = 0;
        //确定 绘制范围的宽高和左上角坐标
        if (maskRatio > 1) {
            if (maskRatio >= ivSegRatio) {
                maskDisplayHeight = parentHeight;
                maskDisplayWidth = (int) (maskDisplayHeight * maskWidth / (float) maskHeight);
                leftX = (parentWidth - maskDisplayWidth) / 2;
                topY = 0;
            } else {
                maskDisplayWidth = parentWidth;
                maskDisplayHeight = (int) (parentWidth * maskHeight / (float) maskWidth);
                leftX = 0;
                topY = (parentHeight - maskDisplayHeight) / 2;
            }

        } else if (maskRatio <= 1) {
            maskDisplayWidth = parentWidth;
            maskDisplayHeight = (int) (parentWidth * maskHeight / (float) maskWidth);
            Log.i(TAG, "getFitCenterWidth maskRatio<=1" + "maskDisplayWidth = " + maskDisplayWidth + "   maskDisplayHeight =" + maskDisplayHeight);
            leftX = 0;
            topY = (parentHeight - maskDisplayHeight) / 2;

        }
        entity.setWidth(maskDisplayWidth);
        entity.setHeight(maskDisplayHeight);
        return entity;
    }

    public static String saveBitmap(Activity activity, Bitmap bm, String filePath) {

        try {
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }

            if (file.isFile() && !file.exists()) {
                file.getParentFile().mkdirs();

                try {
                    file.createNewFile();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 去除扣出的人像四周透明区域
     * @param source 原始bitmap
     * @return 经去透明操作处理后的bitmap
     */
    public static Bitmap trim(Bitmap source) {
        int firstX = 0, firstY = 0;
        int lastX = source.getWidth();
        int lastY = source.getHeight();
        int[] pixels = new int[source.getWidth() * source.getHeight()];
        source.getPixels(pixels, 0, source.getWidth(), 0, 0, source.getWidth(), source.getHeight());
        loop:
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    firstX = x;
                    break loop;
                }
            }
        }
        loop:
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = firstX; x < source.getWidth(); x++) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    firstY = y;
                    break loop;
                }
            }
        }
        loop:
        for (int x = source.getWidth() - 1; x >= firstX; x--) {
            for (int y = source.getHeight() - 1; y >= firstY; y--) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    lastX = x;
                    break loop;
                }
            }
        }
        loop:
        for (int y = source.getHeight() - 1; y >= firstY; y--) {
            for (int x = source.getWidth() - 1; x >= firstX; x--) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    lastY = y;
                    break loop;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(source, firstX, firstY, lastX - firstX, lastY - firstY);

        return bitmap;
    }

    public static Bitmap compressBitmap(String imagePath, int maxNumOfPixels) {
        if (TextUtils.isEmpty(imagePath) || maxNumOfPixels == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只读取图片，不加载到内存中
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePath, options);
        //只读取图片，不加载到内存中
        options.inSampleSize = computeSampleSize(options, -1, maxNumOfPixels);
        //加载到内存中
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }


    public static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
