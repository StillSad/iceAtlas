package com.ice.library.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ice.library.base.delegate.IMvpFragment;
import com.ice.library.mvp.BasePresenter;
import com.ice.library.mvp.IView;

import javax.inject.Inject;

/**
 * Created by ICE on 2017/7/20.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements IMvpFragment{
    @Inject
    protected P mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this instanceof IView) mPresenter.setView(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

}
