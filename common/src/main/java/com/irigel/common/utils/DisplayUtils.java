package com.irigel.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.irigel.common.BuildConfig;

import java.lang.reflect.Method;

/**
 * Author JackSparrow
 * Create Date 01/11/2016.
 */

public class DisplayUtils {

    private static int screenHeight = -1;
    private static int navigationBarHeight = -1;

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        return drawable2Bitmap(drawable, config);
    }

    public static Bitmap drawable2BitmapWithoutLimited(Context context,Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        return drawable2BitmapWithoutLimited(context,drawable, config);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable, Bitmap.Config config) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        int byteCount = 4; //ARGB_8888
        switch (config) {
            case ALPHA_8:
                byteCount = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                byteCount = 2;
        }

        if (BuildConfig.DEBUG && width * height > 1024 * 1024 / byteCount) {
            throw new OutOfMemoryError("创建Bitmap大小最好不要超过1M!");
        }

        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable, int bitmapWidth, int bitmapHeight) {
        if (drawable == null) {
            return null;
        }
        return drawable2Bitmap(drawable, bitmapWidth, bitmapHeight, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable, int bitmapWidth, int bitmapHeight, Bitmap.Config config) {
        int byteCount = 4; //ARGB_8888
        switch (config) {
            case ALPHA_8:
                byteCount = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                byteCount = 2;
        }

        if (BuildConfig.DEBUG && bitmapWidth * bitmapHeight > 1024 * 1024 / byteCount) {
            throw new OutOfMemoryError("创建Bitmap大小最好不要超过1M!");
        }

        if (bitmapWidth <= 0) {
            bitmapWidth = 1;
        }

        if (bitmapHeight <= 0) {
            bitmapHeight = 1;
        }

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap vectorToBitmap(Context context, int vectorResId) {
        VectorDrawableCompat drawable;

        try {
            drawable = VectorDrawableCompat.create(context.getResources(), vectorResId, null);
        } catch (Resources.NotFoundException | OutOfMemoryError e) {
            return null;
        }

        if (drawable == null) {
            return null;
        }
        return drawable2Bitmap(drawable, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap vectorToBitmap(Context context, int vectorResId, Bitmap.Config config) {
        final VectorDrawableCompat drawable;
        try {
            drawable = VectorDrawableCompat.create(context.getResources(), vectorResId, null);
        } catch (Resources.NotFoundException | OutOfMemoryError e) {
            return null;
        }

        if (drawable == null) {
            return null;
        }
        return drawable2Bitmap(drawable, config);
    }

    public static Bitmap vectorToBitmap(Context context, int vectorResId, int width, int height) {
        final VectorDrawableCompat drawable;
        try {
            drawable = VectorDrawableCompat.create(context.getResources(), vectorResId, null);
        } catch (Exception e) {
            return null;
        }
        if (drawable == null) {
            return null;
        }
        return drawable2Bitmap(drawable, width, height, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap vectorToBitmapWithoutLimited(Context context, int vectorResId) {
        final VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), vectorResId, null);
        if (drawable == null) {
            return null;
        }
        return drawable2BitmapWithoutLimited(context,drawable, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap vectorToBitmapWithoutLimited(Context context, int vectorResId, int width, int height) {
        final VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), vectorResId, null);
        if (drawable == null) {
            return null;
        }
        return drawable2BitmapWithoutLimited(context,drawable, width, height, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap drawable2BitmapWithoutLimited(Context context,Drawable drawable, Bitmap.Config config) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        if (BuildConfig.DEBUG && (width > DisplayUtils.getScreenWidth(context)
                || height > DisplayUtils.getScreenWithNavigationBarHeight(context))) {
            throw new OutOfMemoryError("创建Bitmap大小不能超过屏幕大小!");
        }

        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawable2BitmapWithoutLimited(Context context,Drawable drawable, int bitmapWidth, int bitmapHeight, Bitmap.Config config) {
        if (BuildConfig.DEBUG && (bitmapWidth > DisplayUtils.getScreenWidth(context) || bitmapHeight > DisplayUtils.getScreenWithNavigationBarHeight(context))) {
            throw new OutOfMemoryError("创建Bitmap大小不能超过屏幕大小!");
        }

        if (bitmapWidth <= 0) {
            bitmapWidth = 1;
        }

        if (bitmapHeight <= 0) {
            bitmapHeight = 1;
        }

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable.draw(canvas);

        return bitmap;
    }

    @Deprecated
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable2Bitmap(drawable);
    }

    @Deprecated
    public static Bitmap drawableToBitmap(Drawable drawable, int bitmapWidth, int bitmapHeight) {
        return drawable2Bitmap(drawable, bitmapWidth, bitmapHeight);
    }

    @Deprecated
    public static Bitmap drawableToBitmap(Drawable drawable, int bitmapWidth, int bitmapHeight, Bitmap.Config config) {
        return drawable2Bitmap(drawable, bitmapWidth, bitmapHeight, config);
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static Bitmap getBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        if (w <= 0) {
            w = 1;
        }

        int h = bitmap.getHeight();
        if (h <= 0) {
            h = 1;
        }

        Matrix matrix = new Matrix();
        float scaleW = (float) width / w;
        float scaleH = (float) height / h;
        matrix.postScale(scaleW, scaleH);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    public static Bitmap tintBitmap(Bitmap bitmap, int tintColor) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        float roundPx = Math.min(bitmap.getWidth(), bitmap
                .getHeight()) / 2;

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getRoundCornerBitmap(Context context,Bitmap bitmap, int cornerRadius) {
        float roundPx = dpToPx(context,cornerRadius);

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static float dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics();
    }

    public static float getScreenDensity(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics().density;
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics().widthPixels;
    }

    public static int getScreenWithOutNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics().heightPixels;
    }

    public static int getScreenWithNavigationBarHeight(Context context) {
        if (screenHeight < 0) {
            Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                screenHeight = dm.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return screenHeight;
    }

    @SuppressLint("NewApi")
    public static boolean hasPhysicalNavigationBar(Context context) {
        boolean hasMenuKey = false;
        boolean hasBackKey = false;
        try {
            hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        try {
            hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        return hasMenuKey || hasBackKey;
    }

    public static int getNavigationBarHeight(Context context) {
        if (navigationBarHeight < 0) {
            if (hasPhysicalNavigationBar(context)) {
                navigationBarHeight = 0;
            } else {
                int resourceId = context.getResources()
                        .getIdentifier("navigation_bar_height", "dimen", "android");
                navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return navigationBarHeight;
    }

    public static int getTextWidth(Paint paint, String str) {
        int textWidth = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                textWidth += (int) Math.ceil(widths[j]);
            }
        }
        return textWidth;
    }

    public static int getColor(Context context,int id) {
        return context.getResources().getColor(id);
    }

    public static float getDimension(Context context,int id) {
        return context.getResources().getDimension(id);
    }

    public static float getFraction(Context context,int id) {
        return context.getResources().getFraction(id, 1, 1);
    }

    public static String getString(Context context,int id) {
        return context.getResources().getString(id);
    }

    public static int colorEvaluate(float fraction, int startColor, int endColor) {
        int startA = (startColor >> 24) & 0xff;
        int startR = (startColor >> 16) & 0xff;
        int startG = (startColor >> 8) & 0xff;
        int startB = startColor & 0xff;

        int endA = (endColor >> 24) & 0xff;
        int endR = (endColor >> 16) & 0xff;
        int endG = (endColor >> 8) & 0xff;
        int endB = endColor & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 获取两个有透明度的颜色重叠后的颜色值，两个参数都是通过getColor(R.color. )的方式获得的颜色值
     *
     * @param belowColor 底层的颜色值
     * @param aboveColor 上层的颜色值
     * @return 重叠后的结果颜色值，是使用Color.argb()创建出来的颜色值
     */
    public static int createOverlapColor(int belowColor, int aboveColor) {

        float belowAlpha = Color.alpha(belowColor) / 255f;
        float aboveAlpha = Color.alpha(aboveColor) / 255f;

        int belowRed = Color.red(belowColor);
        int aboveRed = Color.red(aboveColor);

        int belowGreen = Color.green(belowColor);
        int aboveGreen = Color.green(aboveColor);

        int belowBlue = Color.blue(belowColor);
        int aboveBlue = Color.blue(aboveColor);


        float newAlpha = (1f - aboveAlpha) * belowAlpha + aboveAlpha;

        int newRed = (int) ((((1 - aboveAlpha) * belowAlpha) * belowRed + aboveAlpha * aboveRed) / newAlpha);
        int newGreen = (int) ((((1 - aboveAlpha) * belowAlpha) * belowGreen + aboveAlpha * aboveGreen) / newAlpha);
        int newBlue = (int) ((((1 - aboveAlpha) * belowAlpha) * belowBlue + aboveAlpha * aboveBlue) / newAlpha);

        return Color.argb(Math.round(newAlpha * 255f), newRed, newGreen, newBlue);
    }

    public static void setSystemUiVisible(boolean visible, Window window) {
        if (visible) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    public static void setNavigationVisible(boolean visible, Window window) {
        if (visible) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static boolean hasDeviceNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }

    public static int getBytePerPixel(Bitmap.Config config) {

        if (config == null) {
            return 4;
        }

        switch (config) {
            case ALPHA_8:
                return 1;
            case RGB_565:
            case ARGB_4444:
                return 2;
            case ARGB_8888:
            default:
                return 4;
        }
    }

    public static int sp2px(Context context,float sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(sp * density);
    }

    public static int computeScrollVerticalDuration(int dy, int height) {
        final int duration;
        float absDelta = (float) Math.abs(dy);
        duration = (int) (((absDelta / height) + 1) * 200);
        return dy == 0 ? 0 : Math.min(duration, 500);
    }
}
