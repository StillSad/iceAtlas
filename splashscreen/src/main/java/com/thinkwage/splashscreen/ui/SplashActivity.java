package com.thinkwage.splashscreen.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ice.library.base.activity.BaseActivity;
import com.ice.library.constant.DataKey;
import com.ice.library.utils.DataHelper;
import com.ice.library.utils.StringUtils;
import com.thinkwage.splashscreen.R;
import com.thinkwage.splashscreen.R2;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R2.id.iv_ad)
    ImageView ivAd;
    @BindView(R2.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R2.id.fl_count_down)
    FrameLayout flCountDown;

    //广告倒计时
    private CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvCountDown.setText((millisUntilFinished / 1000) + "S跳过");
        }

        @Override
        public void onFinish() {
            swapMain(null);
        }
    };


    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        flCountDown.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //清空定位信息
        DataHelper.setStringSF("city", "");
        DataHelper.setStringSF("cityId", "");
        showAd();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    private void showAd() {
        String ivUrl = DataHelper.getStringSF(DataKey.LOADING_AD_IMG_URL);
        String url = DataHelper.getStringSF(DataKey.LOADING_AD_URL);
        if (!StringUtils.isNotNull(ivUrl, url)) {
            swapMain(null);
            return;
        }

        tvCountDown.setVisibility(View.VISIBLE);
        countDownTimer.start();
        ivAd.setTag(R.id.iv_ad, url);
        Glide.with(this)
                .asBitmap()
                .load(ivUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(new BitmapImageViewTarget(ivAd){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        //获取imageView的宽
                        int imageViewWidth= ivAd.getWidth();
                        //计算缩放比例
                        float sy= (float) (imageViewWidth* 0.1)/(float) (width * 0.1);
                        //计算图片等比例放大后的高
                        int imageViewHeight= (int) (height * sy);
                        ViewGroup.LayoutParams params = ivAd.getLayoutParams();
                        params.height = imageViewHeight;
                        ivAd.setLayoutParams(params);
                    }
                });
    }


    private void swapMain(String adUrl) {
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        if (StringUtils.isNotNull(adUrl)) {
//            intent.putExtra(StringConstant.AD_URL, adUrl);
//        }
//        startActivity(intent);
//        finish();
//        Intent intent = new Intent();
//        intent.setClassName(getBaseContext(),"com.thinkwage.geshui.ui.MainActivity");
//        startActivity(intent);

        flCountDown.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClassName(getBaseContext(),"com.thinkwage.geshui.ui.MainActivity");
                startActivity(intent);
                finish();
                overridePendingTransition(-1,android.R.anim.slide_out_right);
            }
        });

    }


}
