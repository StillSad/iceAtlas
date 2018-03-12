package com.thinkwage.visiontestbundle.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;



import com.app.annotation.apt.MvpActivity;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.visiontestbundle.R;
import com.thinkwage.visiontestbundle.databinding.ActivityVisionTestBinding;

import com.thinkwage.library.base.activity.DataBindingActivity;


/**
 * Created by ICE on 2017/7/20.
 */
public class VisionTestActivity extends BaseActivity {



    @Override
    public void initView(Bundle savedInstanceState) {
        loadRootFragment(R.id.fl_content,new VisionTestStartFragment());
    }



    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


}
