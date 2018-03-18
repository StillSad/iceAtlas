package com.example.test.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.api.NetFactory;
import com.app.annotation.apt.MvpActivity;
import com.taobao.android.ActivityGroupDelegate;
import com.example.test.R;


import butterknife.BindView;

@MvpActivity
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    FrameLayout flContent;
    RadioGroup rgNavigation;
    private ActivityGroupDelegate mActivityDelegate;
    // 连续两次返回退出程序
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flContent = findViewById(R.id.fl_content);
        rgNavigation = findViewById(R.id.rg_navigation);
        mActivityDelegate = new ActivityGroupDelegate(this, savedInstanceState);
        rgNavigation.setOnCheckedChangeListener(this);
        switchToActivity("home", "com.thinkwage.homebundle.ui.HomeBundleActivity");
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
                switchToActivity("mine", "com.thinkwage.minebundle.ui.MineBundleActivity");
//                DebugHierarchyViewContainer container = new DebugHierarchyViewContainer(this);
//                container.bindFragmentRecords(getFragmentRecords());
//                container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                break;
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                showMessage("再按一次退出程序");
//
//                exitTime = System.currentTimeMillis();
//            } else {
//                cancelMessage();
//                mPresenter.exitApp();
//            }
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    } // End of 连续两次返回退出程序

}
