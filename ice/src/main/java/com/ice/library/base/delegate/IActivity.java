package com.ice.library.base.delegate;

import android.os.Bundle;

import com.ice.library.base.fragment.BaseFragment;

/**
 * Created by ICE on 2017/6/12.
 */

public interface IActivity {


    boolean useEventBus();

    void initView(Bundle savedInstanceState);

    void initData(Bundle savedInstanceState);
    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    boolean useFragment();
}
