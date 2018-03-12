package com.thinkwage.revisebundle.ui.RevisePhone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkwage.library.base.fragment.BaseFragment;
import com.thinkwage.library.utils.CountDownUtils;
import com.thinkwage.revisebundle.R;
import com.thinkwage.revisebundle.R2;
import com.trello.rxlifecycle2.LifecycleProvider;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by ICE on 2017/8/19.
 */

public class RevisePhoneStepOneFragment extends BaseFragment {

    @BindView(R2.id.tv_old_phone)
    TextView tvOldPhone;

    @Override
    protected int initLayout() {
        return R.layout.fragment_revise_phone_step_one;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        String text = "需先验证原家长手机号:";
        String phone = "188****8686";
        SpannableString spannableString = new SpannableString(text + phone);
        ForegroundColorSpan textColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_gray));
        ForegroundColorSpan phoneColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_black));
        spannableString.setSpan(textColorSpan,0,text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(phoneColorSpan,text.length(),text.length() + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvOldPhone.setText(spannableString);


    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }
}
