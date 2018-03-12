package com.thinkwage.revisebundle.ui.RevisePhone;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.api.BaseServiceFactory;
import com.api.ReviseServiceFactory;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.utils.CountDownUtils;
import com.thinkwage.library.utils.DataHelper;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.revisebundle.R;
import com.thinkwage.revisebundle.R2;
import com.thinkwage.revisebundle.bean.RevisePhone;
import com.thinkwage.revisebundle.dialog.ReviseSuccessDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ICE on 2017/8/19.
 */

public class RevisePhoneActivity extends BaseActivity {

    @BindView(R2.id.tv_old_phone)
    TextView tvOldPhone;
    @BindView(R2.id.et_oldvalid_code)
    EditText etOldCalidCode;
    @BindView(R2.id.tv_old_mobil_code)
    TextView tvOldMobilCode;
    @BindView(R2.id.et_newmobile)
    EditText etNewMobile;
    @BindView(R2.id.et_new_valid_code)
    EditText etNewValidCode;
    @BindView(R2.id.tv_new_mobil_code)
    TextView tvNewMobilCode;
    @BindView(R2.id.btn_submit)
    Button btnSubmit;


    ReviseSuccessDialog mReviseDialog;

    private boolean code_oldMobil;
    private boolean code_newMobil;
    private RevisePhone revisePhone;

    @Override
    protected int initLayout() {
        mReviseDialog = new ReviseSuccessDialog(this);
        return R.layout.activity_revise_phone;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        revisePhone = new RevisePhone();
        String phone = DataHelper.getStringSF(DataKey.MOBILE);
        revisePhone.mobile = phone;
//        mViewBinding.setRevisePhone(revisePhone);
        mReviseDialog.confirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String text = "需先验证原家长手机号:";
        phone = phone.substring(0, 3) + "****" + phone.substring(7);
        SpannableString spannableString = new SpannableString(text + phone);
        ForegroundColorSpan textColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_gray));
        ForegroundColorSpan phoneColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_black));
        spannableString.setSpan(textColorSpan, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(phoneColorSpan, text.length(), text.length() + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvOldPhone.setText(spannableString);

    }


    @OnClick(R2.id.tv_old_mobil_code)
    public void oldMobilGetCode() {
        code_oldMobil = true;
        getCode(revisePhone.mobile);
    }

    @OnClick(R2.id.tv_new_mobil_code)
    public void newMobilGetCode() {
        code_newMobil = true;
        if (!StringUtils.checkMobil(revisePhone.newmobile)) {
            getCode(revisePhone.newmobile);
        } else {
            showMessage("请输入新电话号");
        }
    }


    @OnClick(R2.id.btn_submit)
    public void modifyMobile() {
        ReviseServiceFactory.modifyMobile(revisePhone.mobile, revisePhone.newmobile, revisePhone.oldvalidCode, revisePhone.newvalidCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<NetBody<String>>bindToLifecycle())
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> stringNetBody) {
                        mReviseDialog.show();
                    }
                });
    }


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void countDown(final TextView code) {
        code.setTextColor(getResources().getColor(R.color.text_gray_2));
        code.setEnabled(false);
        btnSubmit.setEnabled(code_oldMobil & code_newMobil);
        showMessage("验证码已发送到手机");
        CountDownUtils.countDown(30, RevisePhoneActivity.this, new CountDownUtils.CountDownListener() {
            @Override
            public void onNext(@NonNull Integer integer) {
                code.setText(integer + "S");
            }

            @Override
            public void onFinish() {
                code.setEnabled(true);
                code.setTextColor(getResources().getColor(R.color.colorPrimary));
                code.setText("获取验证码");
            }
        });
    }

    public void countDown(boolean isOldCode) {

        countDown(isOldCode ? tvOldMobilCode : tvNewMobilCode);
    }


    public void getCode(final String mobile) {
        BaseServiceFactory.getCode(mobile.trim())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<NetBody<String>>bindToLifecycle())
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> stringNetBody) {
                        if (mobile.equals(revisePhone.newmobile)) {
                            countDown(tvNewMobilCode);
                        } else {
                            countDown(tvOldMobilCode);
                        }
                    }
                });
    }


}
