package com.thinkwage.loginbundle.ui.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.api.LoginServiceFactory;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.library.utils.PhotoSelectUtils;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.loginbundle.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ICE on 2017/8/3.
 */

public class RegisterActivity extends BaseActivity {

    private RegisterStepOneFragment registerStepOneFragment;
    @Override
    protected int initLayout() {
        return R.layout.activity_fragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        registerStepOneFragment = new RegisterStepOneFragment();
        loadRootFragment(R.id.fl_content, registerStepOneFragment);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = PhotoSelectUtils.getPathFromResult(requestCode,resultCode,data);
        if (StringUtils.isNotNull(path)) {
            registerStepOneFragment.setPhoto(path);
        }
    }


}
