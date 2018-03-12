package com.ice.library.base.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ice.library.mvp.IView;
import com.thinkwage.library.R;
import com.ice.library.base.delegate.IActivity;
import com.ice.library.base.fragment.BaseFragment;
import com.ice.library.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 因为java只能单继承,所以如果有需要继承特定Activity的三方库,那你就需要自己自定义Activity
 * 继承于这个特定的Activity,然后按照将BaseActivity的格式,复制过去记住一定要实现{@link IActivity}
 */
public abstract class BaseActivity extends RxSupportActivity implements IActivity, IView {

    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;

//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        View view = convertAutoView(name, context, attrs);
//        return view == null ? super.onCreateView(name, context, attrs) : view;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        try {
            int layoutResID = initLayout();
            if (layoutResID != -1){//如果initView返回0,框架则不会调用setContentView()
                setContentView(layoutResID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        //初始化控件
        initView(savedInstanceState);

        LogUtils.showLog("ActivityClass", this.getClass().getSimpleName());

    }

    protected abstract int initLayout();

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    //查找控件
    public <T extends View> T $(@IdRes int resId) {
        return (T) getWindow().findViewById(resId);
    }
    //
    public <T extends View> T $(View layoutView, @IdRes int resId) {
        return (T) layoutView.findViewById(resId);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private Toast mToast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private TextView toastTextView;

    @Override
    public void showMessage(String message) {
        if (mToast == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_toast, null);
            toastTextView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(Html.fromHtml("<font color=#eeff41>" + warning + "</font>"));
            toastTextView.setText(message);
            mToast = new Toast(this);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = message;
                toastTextView.setText(message);
                mToast.show();
            }
        }
    }

    public void cancelMessage() {
        if (null != mToast) {
            mToast.cancel();
        }
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }

    public void startActivity(String activityPath){
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setClassName(this,activityPath);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
    }
}
