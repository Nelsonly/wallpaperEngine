package com.nelson.myapplication.present;

public interface IPresent<V> {

    /**
     * 关联view
     * @param view
     */
    void attachView(V view);

    /**
     * 分离view
     * @param view
     */
    void detachView(V view);

    /**
     * 获取view
     * @return
     */
    V getView();
}