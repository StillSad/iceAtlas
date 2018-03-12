package com.ice.library.mvp;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jess on 16/4/28.
 */
public class BasePresenter<M extends IModel, V> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mRootView;


    public BasePresenter(M model) {
        this.mModel = model;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    public void setView(V v) {
        this.mRootView = v;
    }


    public void setViewModule(V v,M m) {
        this.mRootView = v;
        this.mModel = m;
    }

    @Override
    public void onStart() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//解除注册eventbus
        if (mModel != null) {
            mModel.onDestory();
            this.mModel = null;
        }
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }

    protected void showLoading(){
        if (mRootView instanceof IView)
            ((IView) mRootView).showLoading();
    }

    /**
     * 隐藏加载
     */
    protected void hideLoading(){
        if (mRootView instanceof IView)
            ((IView) mRootView).hideLoading();
    }


}
