package com.ice.library.base.delegate;

import com.ice.library.di.component.AppComponent;

/**
 * Created by ICE on 2017/7/20.
 */

public interface IMvpFragment {
    /**
     * 提供AppComponent(提供所有的单例对象)给实现类，进行Component依赖
     * @param appComponent
     */
    void setupFragmentComponent(AppComponent appComponent);
}
