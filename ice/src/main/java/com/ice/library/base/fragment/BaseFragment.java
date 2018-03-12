package com.ice.library.base.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.ice.library.base.activity.BaseActivity;
import com.ice.library.base.delegate.IFragment;
import com.ice.library.mvp.IView;

import butterknife.ButterKnife;

/**
 * 因为java只能单继承,所以如果有需要继承特定Fragment的三方库,那你就需要自己自定义Fragment
 * 继承于这个特定的Fragment,然后按照将BaseFragment的格式,复制过去,记住一定要实现{@link IFragment}
 */
public abstract class BaseFragment extends RxSupportFragment implements IFragment,IView {
    protected Activity mActivity;
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = initLayout();
        if (0 == layoutId) {
            return new FrameLayout(container.getContext());
        }
        return inflater.inflate(layoutId,null,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,this.getView());
        initView(savedInstanceState);
    }

    protected abstract int initLayout();



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }



    @Override
    public void showMessage(String message) {
        ((BaseActivity)getActivity()).showMessage(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    public <T extends View> T $( @IdRes int resId){
        return (T)mRootView.findViewById(resId);
    }


    public <T extends View> T $(View layoutView, @IdRes int resId){
        return (T)layoutView.findViewById(resId);
    }

    public void startActivity(String activityPath){
        Intent intent = new Intent();
        intent.setPackage(getActivity().getPackageName());
        intent.setClassName(getActivity(),activityPath);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}
