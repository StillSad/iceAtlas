package com.thinkwage.loginbundle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.thinkwage.loginbundle.R;
import com.thinkwage.loginbundle.ui.Register.RegisterStepOneContract;


/**
 * Created by ICE on 2017/8/3.
 */

public class RegisterSuccessDialog extends Dialog{

    private Button login;

    public RegisterSuccessDialog(Context context) {
        this(context, R.layout.dialog_register_success);
    }

    public RegisterSuccessDialog(final Context context, @LayoutRes int layoutId) {
        super(context, R.style.LoadingDialog);
        setContentView(LayoutInflater.from(context).inflate(layoutId,null,false));
        login = (Button) findViewById(R.id.btn_login);

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
    }

    public void loginListener(final View.OnClickListener listener) {
        login.setOnClickListener(listener);
    }

}
