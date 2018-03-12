package com.thinkwage.revisebundle.ui.ReviseNickname;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.api.ReviseServiceFactory;
import com.thinkwage.library.aop.annotation.SingleClick;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.library.utils.StringUtils;
import com.thinkwage.revisebundle.R;
import com.thinkwage.revisebundle.R2;


import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/8/19.
 */

public class ReviseNicknameAvtivity extends BaseActivity {
    @BindView(R2.id.et_nickname)
    EditText etNickname;

    @Override
    protected int initLayout() {
        return R.layout.activity_revise_nickname;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @SingleClick
    @OnClick(R2.id.btn_submit)
    public void modifyNickName() {
        ReviseServiceFactory.modifyNickName(StringUtils.getFromEtOrTv(etNickname))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<NetBody<String>>bindToLifecycle())
                .subscribe(new HttpSubscriber<NetBody<String>>() {
                    @Override
                    public void onSuccess(@NonNull NetBody<String> result) {
                        if (200 == result.getCode()) {
                            showMessage("昵称修改成功");
                            finish();
                        } else {
                            showMessage(result.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


}
