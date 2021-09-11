package com.irigel.common.view;

import android.os.Bundle;
import android.view.View;

/**
 * @author zhangqing
 * @param <P>
 */
public interface IView <P> {

    void bindUI(View rootView);

    int getLayoutId();

    void initData(Bundle savedInstanceState);

    P getPresent();

    P newPresent();
}
