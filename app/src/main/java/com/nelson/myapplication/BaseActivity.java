package com.nelson.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import com.irigel.common.utils.KnifeKit;
import com.irigel.common.view.IView;

import butterknife.Unbinder;

public abstract class BaseActivity<P extends IPresent>  extends AppCompatActivity implements IView<P> {
    private Unbinder unbinder;
    private P present;
    private Context context;
    /**
     * 加载弹窗
     */
    private Dialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        this.context = this;
        initData(savedInstanceState);
        if (getLayoutId() > 0) {
            bindUI(null);
        }
    }

    /**
     * 绑定UI
     *
     * @param rootView
     */
    @CallSuper
    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this);
    }

    /**
     * 获取present对象
     *
     * @return
     */
    @Override
    public P getPresent() {
        if (present == null) {
            present = newPresent();
            if (present != null) {
                present.attachView(this);
            }
        }
        return present;
    }

    /**
     * 创建present
     *
     * @return
     */
    @Override
    public P newPresent() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (present != null) {
            present.detachView(this);
        }

        KnifeKit.unbind(unbinder);
        present = null;
        super.onDestroy();
    }

}