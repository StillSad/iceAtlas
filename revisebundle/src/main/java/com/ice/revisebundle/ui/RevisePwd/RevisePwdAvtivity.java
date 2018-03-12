package com.thinkwage.revisebundle.ui.RevisePwd;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.api.ReviseServiceFactory;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.revisebundle.R;
import com.thinkwage.revisebundle.R2;
import com.thinkwage.revisebundle.bean.RevisePwd;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/8/19.
 */

public class RevisePwdAvtivity extends BaseActivity {

    @BindView(R2.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R2.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R2.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R2.id.btn_submit)
    Button btnSubmit;


    @Override
    protected int initLayout() {
        return R.layout.activity_revise_pwd;
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        etOldPwd.addTextChangedListener(new changedWatcher());
        etNewPwd.addTextChangedListener(new changedWatcher());
        etConfirmPwd.addTextChangedListener(new changedWatcher());
    }
    @OnClick(R2.id.btn_submit)
    public void subMit() {
        if (confirmPassword()) {
            ReviseServiceFactory.modifyPassword(etOldPwd.getText().toString().trim(), etNewPwd.getText().toString().trim(), etConfirmPwd.getText().toString().trim())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .compose(this.<NetBody<String>>bindToLifecycle())
                    .subscribe(new HttpSubscriber<NetBody<String>>() {
                        @Override
                        public void onSuccess(@NonNull NetBody<String> result) {
                            if (200 == result.getCode()) {
                                showMessage("密码修改成功");
                                finish();
                            } else {
                                showMessage(result.getErrorMsg());
                            }
                        }
                    });
        } else {
            showMessage("两次输入的新密码不一致");
        }

    }

    private boolean confirmPassword() {
        return etNewPwd.getText().toString().trim().equals(etConfirmPwd.getText().toString().trim());
    }

    public void checkInput() {
        if (StringUtils.isNotNull(etOldPwd.getText().toString().trim(), etNewPwd.getText().toString().trim(), etConfirmPwd.getText().toString().trim())) {
            btnSubmit.setEnabled(true);
        } else {
            btnSubmit.setEnabled(false);
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    class changedWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkInput();
        }
    }
}
