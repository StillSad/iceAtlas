package com.thinkwage.applybundle.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ice.library.aop.annotation.SingleClick;
import com.ice.library.utils.LogUtils;
import com.thinkwage.applybundle.R;

public class ApplyBundleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_bundle);

        findViewById(R.id.tv_test).setOnClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        LogUtils.d("应用");
    }
}
