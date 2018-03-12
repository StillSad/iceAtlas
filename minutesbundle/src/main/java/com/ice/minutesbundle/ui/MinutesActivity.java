package com.thinkwage.minutesbundle.ui;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.annotation.apt.MvpActivity;
import com.thinkwage.library.constant.DataKey;
import com.thinkwage.library.utils.WindowUtils;
import com.thinkwage.minutesbundle.R;
import com.thinkwage.minutesbundle.base.ComponentActivity;
import com.thinkwage.minutesbundle.di.component.ActivityComponent;


/**
 * Created by ICE on 2017/7/17.
 */
@MvpActivity
public class MinutesActivity extends ComponentActivity<ViewDataBinding, MinutesPresenter> implements MinutesContract.View {

    private MinutesAdapter minutesAdapter;
    private RecyclerView rvInfo;
    private String minutesType;

    @Override
    public ViewDataBinding viewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_minutes);
    }
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rvInfo = $(R.id.rv_info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvInfo.setLayoutManager(layoutManager);


        rvInfo.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) > 1)
                    outRect.top = WindowUtils.dp2px(MinutesActivity.this, 10);
            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        minutesType = getIntent().getStringExtra(DataKey.MINUTES_TYPE);
        switch (minutesType) {
            case DataKey.MINUTES_EYE:
                mPresenter.eyesight();
                break;
            case DataKey.MINUTES_HEIGHT_WEIGHT:
                mPresenter.heightWeight();
                break;
            case DataKey.MINUTES_TEETH:
                mPresenter.teeth();
                break;
        }

    }

    public void setAdapter() {
        minutesAdapter = new MinutesAdapter(minutesType);
        rvInfo.setAdapter(minutesAdapter);
    }


}
