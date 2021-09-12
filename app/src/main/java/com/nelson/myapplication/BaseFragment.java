package com.nelson.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.irigel.common.utils.KnifeKit;
import com.irigel.common.view.IView;
import com.nelson.myapplication.present.IPresent;

import butterknife.Unbinder;

/**
 * @author zhangqing
 * @date 2/19/21
 * activity 基类
 */
public abstract class BaseFragment<P extends IPresent>  extends Fragment implements IView<P> {
    private Unbinder unbinder;
    private P present;
    protected Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getLayoutId() > 0){
            View view = inflater.inflate(getLayoutId(), container, false);
            bindUI(view);
            return view;
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 绑定UI
     * @param rootView
     */
    @CallSuper
    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this,rootView);
    }

    /**
     * 获取Present
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

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (present != null) {
            present.detachView(this);
        }
        KnifeKit.unbind(unbinder);
        present = null;
        super.onDestroy();
    }
}
