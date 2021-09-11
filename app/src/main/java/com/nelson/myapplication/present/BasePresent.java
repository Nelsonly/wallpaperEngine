package com.nelson.myapplication.present;

public class BasePresent<V extends IView> implements IPresent<V> {

    private WeakReference<V> mWeakReference;
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 关联view
     * @param view
     */
    @Override
    public void attachView(V view) {
        mWeakReference = new WeakReference<>(view);
    }

    /**
     * 分离view
     * @param view
     */
    @Override
    public void detachView(V view) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (mWeakReference != null){
            mWeakReference.clear();
            mWeakReference = null;
        }
        releaseDisposable();
    }

    /**
     * 获取view
     * @return
     */
    @Override
    public V getView(){
        if (mWeakReference != null){
            return mWeakReference.get();
        }
        return null;
    }

    protected boolean isAttached() {
        return mWeakReference != null;
    }
