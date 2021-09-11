package com.irigel.common;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author zhangqing
 * @date 2/20/21
 */
public class Constants {
    public static final String FALLS_TYPE_AD = "ad";
    public static final String FALLS_TYPE_MODEL = "model";
    public static final String FALLS_TYPE_MATERIAL = "material";


    @StringDef({FALLS_TYPE_AD, FALLS_TYPE_MODEL, FALLS_TYPE_MATERIAL})

    @Retention(RetentionPolicy.SOURCE)
    public @interface FallsElementType {
    }
}
