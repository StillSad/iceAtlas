package com.thinkwage.loginbundle.ui.FindPwd;

import android.app.Activity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.api.BaseServiceFactory;
import com.api.LoginServiceFactory;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.base.activity.BaseActivity;


import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.utils.CountDownUtils;
import com.thinkwage.library.utils.LogUtils;
import com.thinkwage.library.utils.RxUtils;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.R2;
import com.thinkwage.loginbundle.model.FindPwd;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ICE on 2017/8/19.
 */

public class FindPwdActivity extends BaseActivity {

    @BindView(R2.id.et_mobil)
    EditText etMobile;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.tv_code)
    TextView tvCode;
    @BindView(R2.id.et_pwd)
    EditText etPwd;
    @BindView(R2.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R2.id.btn_submit)
    Button btnSubmit;


//    private FindPwd findPwd;

    @Override
    protected int initLayout() {
        return R.layout.activity_find_pwd;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
//        loadRootFragment(R.id.fl_content,new FindPwdStepOneFragment());


       tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                if (!StringUtils.checkMobil(etMobile.getText().toString().trim())) {
                    getCode();
                } else {
                    showMessage("请输入电话号");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void countDown() {
        tvCode.setTextColor(getResources().getColor(R.color.text_gray_2));
        btnSubmit.setEnabled(true);
        showMessage("验证码已发送到手机");
        CountDownUtils.countDown(30, FindPwdActivity.this, new CountDownUtils.CountDownListener() {
            @Override
            public void onNext(@NonNull Integer integer) {
                tvCode.setText(integer + "S");
            }

            @Override
            public void onFinish() {
                tvCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvCode.setText("计时完成");
            }
        });
    }

    private void forgetPassword() {

        if (checkPwd()) {
            LoginServiceFactory
                    .forgetPassword(etMobile.getText().toString().trim(), etCode.getText().toString().trim(),etPwd.getText().toString().trim())
                    .subscribeOn(Schedulers.io())
                    .compose(this.<NetBody<String>>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpSubscriber<NetBody<String>>() {
                        @Override
                        public void onSuccess(@NonNull NetBody<String> stringNetBody) {
                            if (200 == stringNetBody.getCode()) {
                                showMessage("密码修改成功");
                                finish();
                            } else {
                                showMessage(stringNetBody.getErrorMsg());
                            }
                        }
                    });
        }

    }

    private boolean checkPwd() {
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showMessage("请输入密码");
            return false;
        }
        String confirmPwd = etConfirmPwd.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPwd)) {
            showMessage("请确认密码");
            return false;
        }
        if (pwd.equals(confirmPwd)) {
            showMessage("输入密码不一致");
            return false;
        }
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    public void getCode() {
        LogUtils.d("获取手机验证码");
        BaseServiceFactory.getCode(etMobile.getText().toString().trim())
                .compose(this.<NetBody<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<NetBody<String>>() {

                    @Override
                    public void onSuccess(@NonNull NetBody<String> result) {
                        if (200 == result.getCode()) {
                            countDown();
                        } else {
                            showMessage(result.getErrorMsg());
                        }

                    }
                });
    }


}
