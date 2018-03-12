package com.ice.library.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ice.library.base.delegate.IMvpActivity;
import com.ice.library.mvp.BasePresenter;
import com.ice.library.mvp.IView;

import javax.inject.Inject;

/**
 * Created by ICE on 2017/7/20.
 */


public abstract class MvpAvtivity<P extends BasePresenter> extends BaseActivity implements IMvpActivity {
    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mPresenter != null) mPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

}
