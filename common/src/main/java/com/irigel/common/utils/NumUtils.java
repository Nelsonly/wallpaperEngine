package com.irigel.common.utils;

/**
 * Created by zhangqing on 3/15/21.
 */
public class NumUtils {

    public static int getRandomNum(int max, int min) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }
}
