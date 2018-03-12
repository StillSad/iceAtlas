package com.thinkwage.geshui.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.app.annotation.apt.MvpActivity;
import com.ice.library.base.activity.BaseActivity;
import com.ice.library.base.activity.RxSupportActivity;
import com.thinkwage.geshui.BuildConfig;
import com.thinkwage.geshui.R;
import com.thinkwage.geshui.base.ComponentActivity;
import com.thinkwage.geshui.di.component.ActivityComponent;
import com.ice.library.integration.AppManager;
import com.taobao.android.ActivityGroupDelegate;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.security.Key;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.debug.DebugHierarchyViewContainer;

@MvpActivity
public class MainActivity extends ComponentActivity<MainPresenter> implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rg_navigation)
    RadioGroup rgNavigation;
    private ActivityGroupDelegate mActivityDelegate;

    // 连续两次返回退出程序
    private long exitTime = 0;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mActivityDelegate = new ActivityGroupDelegate(this, savedInstanceState);
        rgNavigation.setOnCheckedChangeListener(this);
        switchToActivity("home", "com.thinkwage.homebundle.ui.HomeBundleActivity");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    public void switchToActivity(String key, String activityName) {
        Intent intent = new Intent();
        intent.setClassName(getBaseContext(), activityName);
        mActivityDelegate.startChildActivity(flContent, key, intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                switchToActivity("home", "com.thinkwage.homebundle.ui.HomeBundleActivity");
                break;
            case R.id.rb_apply:
                switchToActivity("apply", "com.thinkwage.applybundle.ui.ApplyBundleActivity");
                break;
            case R.id.rb_service:
                switchToActivity("service", "com.thinkwage.servicebundle.ui.ServiceBundleActivity");
                break;
            case R.id.rb_mine:
//                switchToActivity("mine", "com.thinkwage.minebundle.ui.MineBundleActivity");
//                DebugHierarchyViewContainer container = new DebugHierarchyViewContainer(this);
//                container.bindFragmentRecords(getFragmentRecords());
//                container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setPositiveButton(android.R.string.cancel, null)
                        .setCancelable(true)
                        .create();
                alertDialog.show();

                break;
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return true;
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showMessage("再按一次退出程序");

                exitTime = System.currentTimeMillis();
            } else {
                cancelMessage();
                mPresenter.exitApp();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // End of 连续两次返回退出程序

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}
