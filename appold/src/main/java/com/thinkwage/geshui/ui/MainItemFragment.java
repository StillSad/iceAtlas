package com.thinkwage.geshui.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkwage.geshui.R;
import com.thinkwage.geshui.model.UserInfo;
import com.thinkwage.geshui.update.Updater;
import com.ice.library.aop.annotation.SingleClick;
import com.ice.library.base.fragment.BaseFragment;
import com.ice.library.constant.DataKey;

import butterknife.BindView;

/**
 * Created by ICE on 2017/7/19.
 */

public class MainItemFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_minutes)
    Button btnMinutes;


    String type = DataKey.MINUTES_TEETH;


    @Override
    protected int initLayout() {
        return R.layout.item_fragment_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        UserInfo u = (UserInfo) bundle.get(DataKey.BUNDLE);
//        mViewBinding.setUserState(u);
        tvState.setText(u.state);
        btnTest.setText(u.test);
        if (u.test.contains("身高")) {
            type = DataKey.MINUTES_HEIGHT_WEIGHT;
            imageView.setImageResource(R.mipmap.icon_height);
        } else if (u.test.contains("体重")) {
            type = DataKey.MINUTES_HEIGHT_WEIGHT;
            imageView.setImageResource(R.mipmap.icon_weight);
        } else if (u.test.contains("视力")) {
            type = DataKey.MINUTES_EYE;
            imageView.setImageResource(R.mipmap.icon_vision);
        }
        btnTest.setOnClickListener(this);
        btnMinutes.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }


    @Override
    @SingleClick
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                if (type.equals(DataKey.MINUTES_EYE))
                    startActivity("com.ice.visiontestbundle.ui.VisionTestActivity");
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Updater.update(getActivity().getBaseContext());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
//                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }.execute();

                break;
            case R.id.btn_minutes:
                Intent minutesIntent = new Intent()
                        .setPackage(getActivity().getPackageName())
                        .setClassName(getActivity(), "com.ice.minutesbundle.ui.MinutesActivity")
                        .putExtra(DataKey.MINUTES_TYPE, type);
                startActivity(minutesIntent);
//                startActivity(new Intent(getActivity(), MinutesActivity.class).putExtra(DataKey.MINUTES_TYPE,type));
                break;
            default:
                break;
        }
    }



}
