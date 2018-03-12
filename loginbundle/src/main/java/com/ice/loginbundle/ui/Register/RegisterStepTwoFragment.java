package com.thinkwage.loginbundle.ui.Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.app.annotation.apt.MvpFragment;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.utils.CountDownUtils;
import com.thinkwage.library.utils.LogUtils;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.R2;
import com.thinkwage.loginbundle.base.ComponentFragment;
import com.thinkwage.loginbundle.di.component.FragmentComponent;
import com.thinkwage.loginbundle.dialog.RegisterSuccessDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

/**
 * Created by ICE on 2017/8/4.
 */
@MvpFragment
public class RegisterStepTwoFragment extends ComponentFragment<RegisterStepTwoPresenter> implements RegisterStepTwoContract.View {


    @BindView(R2.id.et_mobile)
    EditText etMobile;
    @BindView(R2.id.et_mobile_code)
    EditText etMobileCode;
    @BindView(R2.id.tv_code)
    TextView tvCode;
    @BindView(R2.id.et_pwd)
    EditText etPwd;


    private RegisterSuccessDialog mRSDialog;
    private String id;

    @Override
    protected int initLayout() {
        return R.layout.fragment_register_step_two;
    }


    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        id = (String) bundle.get(DataKey.BUNDLE);
        mRSDialog = new RegisterSuccessDialog(getActivity());
        mRSDialog.loginListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                mRSDialog.dismiss();
                getActivity().finish();
            }
        });

    }


    @OnClick(R2.id.btn_next)
    public void registerNext(){
        mPresenter.registerNext(id
                ,StringUtils.getFromEtOrTv(etMobile)
                ,StringUtils.getFromEtOrTv(etMobileCode)
                ,StringUtils.getFromEtOrTv(etPwd));
    }

    @SingleClick
    @OnClick(R2.id.tv_code)
    public void getCode() {
        if (!StringUtils.checkMobil(StringUtils.getFromEtOrTv(etMobile))) {
            mPresenter.getCode(StringUtils.getFromEtOrTv(etMobile));
        } else {
            showMessage("请输入电话号");
        }

    }

    public void countDown() {
        tvCode.setTextColor(getResources().getColor(R.color.text_gray_2));
        tvCode.setEnabled(false);
        showMessage("验证码已发送到手机");
        CountDownUtils.countDown(30, RegisterStepTwoFragment.this, new CountDownUtils.CountDownListener() {
            @Override
            public void onNext(@NonNull Integer integer) {
                tvCode.setText(integer + "S");
            }

            @Override
            public void onFinish() {
                tvCode.setEnabled(true);
                tvCode.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvCode.setText("获取验证码");
            }
        });

    }

    @Override
    public void registerSuccess() {
        mRSDialog.show();
    }
    @Override
    public void registerError(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }





}
