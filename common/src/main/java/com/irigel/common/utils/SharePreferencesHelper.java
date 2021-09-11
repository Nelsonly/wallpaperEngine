package com.irigel.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author zhangqing
 * @date 3/9/21
 */
public class SharePreferencesHelper {
    private static final String TAG = "SharePreferencesHelper";

    /**
     * sp文件的名字
     */
    private static final String SP_NAME = "ilregel_album";
    //记录次数
    public static final String COUNT="count";



    private SharedPreferences mPreferences;

    private SharedPreferences.Editor mEditor;

    private static SharePreferencesHelper mSPHelper;


    public static SharePreferencesHelper getInstance(Context context) {
        if(mSPHelper == null){
            synchronized (SharePreferencesHelper.class) {
                if (mSPHelper == null) {
                    mSPHelper = new SharePreferencesHelper(context);
                }
            }
        }

        return mSPHelper;
    }

    private SharePreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }


    public boolean putString(String key, String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean removeString(String key) {
        mEditor = mPreferences.edit();
        mEditor.remove(key);
        return mEditor.commit();
    }


    public boolean putInt(String key, int value) {
        mEditor = mPreferences.edit();
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }


    public boolean putLong(String key, long value) {
        mEditor = mPreferences.edit();
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public long getLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }


    public boolean putBoolean(String key, boolean value) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }




    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public void clearData(Context context) {
        mPreferences = context.getSharedPreferences(SharePreferencesHelper.SP_NAME, 0);
        mPreferences.edit().clear().commit();
    }
}
