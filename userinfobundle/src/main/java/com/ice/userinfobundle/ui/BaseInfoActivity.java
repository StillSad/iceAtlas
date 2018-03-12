package com.thinkwage.userinfobundle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.annotation.apt.MvpActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thinkwage.library.utils.DataHelper;
import com.thinkwage.library.utils.PhotoSelectUtils;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.library.utils.helper.GlideCircleTransform;
import com.thinkwage.userinfobundle.R;
import com.thinkwage.userinfobundle.R2;
import com.thinkwage.userinfobundle.base.ComponentActivity;
import com.thinkwage.userinfobundle.bean.UserInfo;
import com.thinkwage.userinfobundle.di.component.ActivityComponent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


@MvpActivity
public class BaseInfoActivity extends ComponentActivity<BaseInfoPresenter> implements BaseInfoContract.View {

    @BindView(R2.id.iv_headpic)
    ImageView ivHeadpic;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.tv_gender)
    TextView tvGender;
    @BindView(R2.id.tv_birthdate)
    TextView tvBirthdate;
    @BindView(R2.id.tv_school_name)
    TextView tvSchoolName;
    @BindView(R2.id.tv_schoolid)
    TextView tvSchoolId;
    @BindView(R2.id.tv_mobile)
    TextView tvMobile;

    private String headpicPath;

    @Override
    protected int initLayout() {
        return R.layout.activity_base_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.profile();
        tvName.setText("动态部署后1.0.1");
        tvNickname.setText("动态部署后1.0.1");
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
//        LoaderFactory.getLoader()
//                .transformation(new GlideCircleTransform(this))
//                .loadNet(ivHeadpic, userInfo.headpic, null);
        Glide.with(this)
                .load(userInfo.headpic)
                .apply(new RequestOptions().transform(new GlideCircleTransform(this)))
                .into(ivHeadpic);
        userInfo.gender = "";
        userInfo.nickname = "动态部署后1.0.1";
         tvName.setText(userInfo.truename);
         tvNickname.setText(userInfo.nickname);
         tvGender.setText(userInfo.gender);
         tvBirthdate.setText(userInfo.birthdate);
         tvSchoolName.setText(userInfo.school_name);
         tvSchoolId.setText(userInfo.schoolid);
         tvMobile.setText(userInfo.mobile);
    }

    @OnClick(R2.id.fl_headpic)
    public void selectHeadPid() {
        PhotoSelectUtils.selectFormCameraOrImage(this);
    }

    @OnClick(R2.id.fl_revise_nickname)
    public void reviseNickname() {
        finish();
        startActivity("com.ice.revisebundle.ui.ReviseNickname.ReviseNicknameAvtivity");
    }

    @OnClick(R2.id.fl_revise_phone)
    public void revisePhone() {
        startActivity("com.ice.revisebundle.ui.RevisePhone.RevisePhoneActivity");
    }

    @OnClick(R2.id.tv_revise_pwd)
    public void revisePwd() {
        startActivity("com.ice.revisebundle.ui.RevisePwd.RevisePwdAvtivity");
    }

    @OnClick(R2.id.btn_out)
    public void out() {
        DataHelper.clearShareprefrence();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = PhotoSelectUtils.getPathFromResult(requestCode, resultCode, data);
        if (StringUtils.isNotNull(path)) {
            setHeadpic(path);
        }

    }

    public void setHeadpic(String path) {
        this.headpicPath = path;
        Glide.with(this)
                .load( new File(path))
                .apply(new RequestOptions().transform(new GlideCircleTransform(this)))
                .into(ivHeadpic);
//        LoaderFactory.getLoader()
//                .transformation(new GlideCircleTransform(this))
//                .loadFile(ivHeadpic, new File(path), null);
    }


}
