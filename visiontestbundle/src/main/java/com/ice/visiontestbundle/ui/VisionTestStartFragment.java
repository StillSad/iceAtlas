package com.thinkwage.visiontestbundle.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.base.fragment.BaseFragment;
import com.thinkwage.library.base.fragment.DataBindingFragment;
import com.thinkwage.visiontestbundle.R;
import com.thinkwage.visiontestbundle.databinding.FragmentVisionTestStartBinding;

/**
 * Created by ICE on 2017/8/21.
 */

public class VisionTestStartFragment extends BaseFragment{
    @Override
    public FragmentVisionTestStartBinding viewBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_vision_test_start,container,false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewBinding.btnGoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                start(new VisionTestingFragment());
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }
}
