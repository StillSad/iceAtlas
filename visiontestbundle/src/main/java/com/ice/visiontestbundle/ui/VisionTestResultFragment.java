package com.thinkwage.visiontestbundle.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.api.VisionTestServiceFactory;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.base.fragment.DataBindingFragment;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.visiontestbundle.R;
import com.thinkwage.visiontestbundle.databinding.FragmentVisionTestResultBinding;
import com.thinkwage.visiontestbundle.model.VisionTest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/8/21.
 */

public class VisionTestResultFragment extends DataBindingFragment<FragmentVisionTestResultBinding> {
    @Override
    public FragmentVisionTestResultBinding viewBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_vision_test_result, container, false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewBinding.tvLeftEye.setText(VisionTest.leftEyeGrades);
        mViewBinding.tvRightEye.setText(VisionTest.rightEyeGrades);
        mViewBinding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                startActivity("com.ice.visiontestbundle.ui.VisionTestActivity");
                getActivity().finish();
            }
        });
        mViewBinding.btnMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                Intent minutesIntent = new Intent();
                minutesIntent.setPackage(getActivity().getPackageName());
                minutesIntent.setClassName(getActivity(),"com.ice.minutesbundle.ui.MinutesActivity");
                minutesIntent.putExtra(DataKey.MINUTES_TYPE,DataKey.MINUTES_EYE);
                startActivity(minutesIntent);

            }
        });
        VisionTestServiceFactory.addEyesight(VisionTest.leftEyeGrades, VisionTest.rightEyeGrades)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<NetBody<String>>bindToLifecycle())
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> stringNetBody) {

                    }
                });
    }
}
