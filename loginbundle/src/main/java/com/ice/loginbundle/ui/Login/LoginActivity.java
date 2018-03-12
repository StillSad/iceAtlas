package com.thinkwage.loginbundle.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.app.annotation.apt.MvpActivity;
import com.api.LoginServiceFactory;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.utils.DeviceUtil;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.loginbundle.BuildConfig;
import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.R2;
import com.thinkwage.loginbundle.base.ComponentActivity;
import com.thinkwage.loginbundle.di.component.ActivityComponent;
import com.thinkwage.loginbundle.ui.FindPwd.FindPwdActivity;
import com.thinkwage.loginbundle.ui.Register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ICE on 2017/8/3.
 */
@MvpActivity
public class LoginActivity extends ComponentActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_pwd)
    EditText etPwd;


    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @OnClick(R2.id.btn_login)
    public void login() {
        mPresenter.login(StringUtils.getFromEtOrTv(etPhone), StringUtils.getFromEtOrTv(etPwd));
    }
    @OnClick(R2.id.tv_register)
    public void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R2.id.tv_forget_pwd)
    public void forgetPwd() {
        startActivity(new Intent(LoginActivity.this, FindPwdActivity.class));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void loginSuccess() {
        showMessage("登陆成功");
        finish();
    }

    @Override
    public void loginError(String errorMessage) {
        showMessage(errorMessage);
    }


}
