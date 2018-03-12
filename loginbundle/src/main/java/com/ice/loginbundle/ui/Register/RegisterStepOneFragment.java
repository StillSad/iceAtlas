package com.thinkwage.loginbundle.ui.Register;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.annotation.apt.MvpFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.utils.PhotoSelectUtils;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.library.utils.helper.GlideCircleTransform;
import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.R2;
import com.thinkwage.loginbundle.base.ComponentFragment;
import com.thinkwage.loginbundle.di.component.FragmentComponent;

import java.io.File;
import java.util.Calendar;


import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

import static java.lang.Integer.parseInt;

/**
 * Created by ICE on 2017/8/4.
 */

@MvpFragment
public class RegisterStepOneFragment extends ComponentFragment<RegisterStepOnePresenter> implements RegisterStepOneContract.View {

    @BindView(R2.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R2.id.tv_select_kindergarten)
    TextView tvSelectKindergarten;
    @BindView(R2.id.et_truename)
    EditText etTruename;
    @BindView(R2.id.et_schoolno)
    EditText etSchoolno;
    @BindView(R2.id.tv_birth)
    TextView tvBirth;
    @BindView(R2.id.btn_next)
    Button btnNext;

    private RegisterStepTwoFragment stepTwoFragment;

    private String mPhotoPath;
    private String[] kindergartens;

    @Override
    protected int initLayout() {
        return R.layout.fragment_register_step_one;
    }

    @Override
    protected void initInject(FragmentComponent mFragmentComponent) {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @SingleClick
    @OnClick(R2.id.iv_photo)
    public void selectPhoto() {
        PhotoSelectUtils.selectFormCameraOrImage(getActivity());

    }

    @SingleClick
    @OnClick(R2.id.tv_select_kindergarten)
    public void selectKindergarten() {
        if (null == kindergartens) {
            showMessage("获取幼儿园信息失败！");
            return;
        }
        OptionPicker picker = new OptionPicker(getActivity(), kindergartens);
        picker.setOffset(2);
        picker.setSelectedIndex(1);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                tvSelectKindergarten.setText(option);
            }
        });
        picker.show();
    }

    @SingleClick
    @OnClick(R2.id.btn_next)
    public void register() {
        if (TextUtils.isEmpty(mPhotoPath)) {
            showMessage("请上传照片");
            return;
        }
        mPresenter.register(StringUtils.getFromEtOrTv(tvSelectKindergarten)
                , StringUtils.getFromEtOrTv(etTruename)
                , StringUtils.getFromEtOrTv(etSchoolno)
                , StringUtils.getFromEtOrTv(tvBirth)
                , mPhotoPath);
    }

    @SingleClick
    @OnClick(R2.id.tv_birth)
    public void selectBirth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        DatePicker picker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH_DAY);
        picker.setRange(year - 10, year);//年份范围
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvBirth.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getKindergartenList();

    }

    @Override
    public void setData(Object data) {

    }

    public void setPhoto(String path) {

        this.mPhotoPath = path;
//        LoaderFactory.getLoader()
//                .transformation(new GlideCircleTransform(getActivity()))
//                .loadFile(ivPhoto, new File(path), null);
        Glide.with(getActivity())
                .load(new File(path))
                .apply(new RequestOptions().transform(new GlideCircleTransform(getActivity())))
                .into(ivPhoto);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }


    @Override
    public void next(String id) {
        Bundle idBundle = new Bundle();
        idBundle.putString(DataKey.BUNDLE, id);
        stepTwoFragment = new RegisterStepTwoFragment();
        stepTwoFragment.setArguments(idBundle);
        start(stepTwoFragment);
    }

    @Override
    public void setKindergarten(String[] kindergartens) {
        this.kindergartens = kindergartens;
    }


}
