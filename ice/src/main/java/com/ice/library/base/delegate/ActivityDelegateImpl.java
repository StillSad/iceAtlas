package com.ice.library.base.delegate;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.thinkwage.library.R;
import com.ice.library.base.App;

import org.simple.eventbus.EventBus;

/**
 * Created by ICE on 2017/6/12.
 */

public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iActivity.useEventBus())
            EventBus.getDefault().register(mActivity);
        if (iActivity instanceof IMvpActivity)
            ((IMvpActivity)iActivity).setupActivityComponent(((App) mActivity.getApplication()).getAppComponent());


        //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
        //找到 Toolbar 并且替换 Actionbar
        if (mActivity.findViewById(R.id.toolbar) != null) {
            if (mActivity instanceof AppCompatActivity) {
                ((AppCompatActivity) mActivity).setSupportActionBar((Toolbar) mActivity.findViewById(R.id.toolbar));
                ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayShowTitleEnabled(false);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mActivity.setActionBar((android.widget.Toolbar) mActivity.findViewById(R.id.toolbar));
                    mActivity.getActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        }
        //找到 Toolbar 的标题栏并设置标题名
        if (mActivity.findViewById(R.id.toolbar_title) != null) {
            ((TextView) mActivity.findViewById(R.id.toolbar_title)).setText(mActivity.getTitle());
        }
        //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
        if (mActivity.findViewById(R.id.toolbar_back) != null) {
            mActivity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (iActivity != null && iActivity.useEventBus())
            EventBus.getDefault().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
}
