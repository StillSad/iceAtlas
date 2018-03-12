package com.thinkwage.visiontestbundle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thinkwage.visiontestbundle.R;


/**
 * Created by ICE on 2017/8/3.
 */

public class CountDownDialog extends Dialog {

    public static final int LEFT_EYE = 1;
    public static final int RIGHT_EYE = 2;
    public static final String replaceEye = "{eye}";
    private int mEyeFirstDirections = LEFT_EYE;
    private int mEyeDirections = LEFT_EYE;
    private TextView mTestFinish;
    private TextView mReady;
    private TextView mCountDown;
    private int mTime;


    Handler countDownaHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            mCountDown.setText(msg.arg1 + "");
            if (msg.arg1 == 0) {
                dismiss();
                return;
            }
            Message newMsg = new Message();
            newMsg.arg1 = msg.arg1 - 1;
            countDownaHandler.sendMessageDelayed(newMsg, 1000);
        }
    };
    private MediaPlayer mediaPlayer;


    public CountDownDialog(Context context) {
        this(context, R.layout.dialog_count_down, 5);
    }

    public CountDownDialog(Context context, @LayoutRes int layoutId, int time) {
        super(context, R.style.LoadingDialog);
        this.mTime = time;
        initView(context, layoutId);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
    }

    private void initView(Context context, @LayoutRes int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, null, false);
        setContentView(view);
        mTestFinish = (TextView) view.findViewById(R.id.tv_test_finish);
        mReady = (TextView) view.findViewById(R.id.tv_ready);
        mCountDown = (TextView) view.findViewById(R.id.tv_count_down);
    }


    public void setEyeFirstDirections(int eyeDirections) {
        this.mEyeFirstDirections = eyeDirections;
    }

    public void show(int eyeDirections) {
        show();
        this.mEyeDirections = eyeDirections;
        //测试方向与设置方向一致，测试第一只眼睛
        testEye(this.mEyeFirstDirections == eyeDirections);
        mCountDown.setText("5");

    }

    private void testEye(boolean isFirist) {


        String readyText = getContext().getResources().getString(R.string.test_ready);
        int raw;
        if (LEFT_EYE == mEyeDirections) {
            readyText = readyText.replace(replaceEye, "左眼");
            raw = R.raw.start_test_left_eye;
        } else {
            readyText = readyText.replace(replaceEye, "右眼");
            raw = R.raw.start_test_right_eye;
        }
        mReady.setText(readyText);
        mediaPlayer = MediaPlayer.create(getContext(), raw);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayerCompletionListener());

        if (isFirist) {
            mTestFinish.setVisibility(View.GONE);
            return;
        }
        String finishText = getContext().getResources().getString(R.string.test_finish);

        if (LEFT_EYE == mEyeFirstDirections) {
            finishText = finishText.replace(replaceEye, "左眼");
        } else {
            finishText = finishText.replace(replaceEye, "右眼");
        }
        mTestFinish.setVisibility(View.VISIBLE);
        mTestFinish.setText(finishText);
    }

    private void countDown() {
        Message msg = new Message();
        msg.arg1 = mTime - 1;
        countDownaHandler.sendMessageDelayed(msg, 1000);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mEyeReadyListener == null) return;
        if (mEyeFirstDirections == mEyeDirections) {
            mEyeReadyListener.firstEyeReadyFinish();
        } else {
            mEyeReadyListener.secondEyeReadyFinish();
        }
    }

    EyeReadyListener mEyeReadyListener;

    public void setEyeReadyListener(EyeReadyListener mEyeReadyListener) {
        this.mEyeReadyListener = mEyeReadyListener;
    }

    public interface EyeReadyListener {

        void firstEyeReadyFinish();

        void secondEyeReadyFinish();
    }

    class MediaPlayerCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.release();
            countDown();
        }
    }
}
