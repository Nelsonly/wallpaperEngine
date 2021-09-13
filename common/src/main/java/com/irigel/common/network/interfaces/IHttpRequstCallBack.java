package com.irigel.common.network.interfaces;

import java.io.FileNotFoundException;

/**
 *
 * @author zhangqing
 * @date 2/22/21
 */
public interface IHttpRequstCallBack <T> {
    /**
     * 请求成功回调
     * @param result 返回数据
     */
    void onSuccess(T result);
    /**
     * 请求失败回调
     * @param exception 错误异常信息
     */
    void onFailure(Throwable exception);
}
