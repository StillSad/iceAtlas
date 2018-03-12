package com.thinkwage.revisebundle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.thinkwage.revisebundle.R;


/**
 * Created by ICE on 2017/8/3.
 */

public class ReviseSuccessDialog extends Dialog{

    private Button confirm;

    public ReviseSuccessDialog(Context context) {
        this(context, R.layout.dialog_revise_success);
    }

    public ReviseSuccessDialog(Context context, @LayoutRes int layoutId) {
        super(context, R.style.LoadingDialog);
        setContentView(LayoutInflater.from(context).inflate(layoutId,null,false));
        confirm = (Button) findViewById(R.id.btn_confirm);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
    }

    public void confirmListener(final View.OnClickListener listener) {
        confirm.setOnClickListener(listener);
    }

}
