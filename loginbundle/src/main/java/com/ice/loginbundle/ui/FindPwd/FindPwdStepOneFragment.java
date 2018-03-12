package com.thinkwage.loginbundle.ui.FindPwd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkwage.library.base.fragment.BaseFragment;
import com.thinkwage.library.utils.CountDownUtils;
import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.R2;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by ICE on 2017/8/19.
 */

public class FindPwdStepOneFragment extends BaseFragment {
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_pwd)
    EditText etPwd;
    @BindView(R2.id.tv_code)
    TextView tvCode;
    @BindView(R2.id.btn_next)
    Button btnNext;

    private FindPwdStepTwoFragment findPwdStepTwoFragment;
    @Override
    protected int initLayout() {
        findPwdStepTwoFragment = new FindPwdStepTwoFragment();
        return R.layout.fragment_find_pwd_step_one;
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(findPwdStepTwoFragment);
            }
        });

        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tvCode.setTextColor(getResources().getColor(R.color.text_gray_2));
                btnNext.setEnabled(true);
                CountDownUtils.countDown(30, FindPwdStepOneFragment.this, new CountDownUtils.CountDownListener() {
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
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }


}
