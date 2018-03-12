package com.thinkwage.visiontestbundle.ui;

import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkwage.library.aop.annotation.Permission;
import com.thinkwage.library.base.activity.BaseActivity;
import com.thinkwage.library.base.fragment.DataBindingFragment;
import com.thinkwage.library.utils.LogUtils;
import com.thinkwage.visiontestbundle.R;
import com.thinkwage.visiontestbundle.databinding.FragmentVisionTestingBinding;
import com.thinkwage.visiontestbundle.dialog.CountDownDialog;
import com.thinkwage.visiontestbundle.model.VisionTest;
import com.thinkwage.visiontestbundle.view.E;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.megvii.ice.iflyteklib.util.SpeachInputListener;
import com.megvii.ice.iflyteklib.util.SpeachUtil;

import java.io.IOException;

/**
 * Created by ICE on 2017/8/21.
 */
public class VisionTestingFragment extends BaseActivity<FragmentVisionTestingBinding> {
    private long startTime;
    //会话停止
    private boolean isStop = false;
    //10s停止
    private boolean isStop10s = false;
    //60s停止
    private boolean isStop60s = false;
    private VisionTest visionTest;

    private String testingText;
    private String currentEye = "左眼";
    private CountDownDialog countDownDialog;
    private MediaPlayer mediaPlayer;


    @Override
    public FragmentVisionTestingBinding viewBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_vision_testing, container, false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        initCountDownDialog();
        countDownDialog.show(CountDownDialog.LEFT_EYE);
//        startTime = System.currentTimeMillis();
//
//        visionTest = new VisionTest();
//        visionTest.addTest();
//        int currentTestDirection = visionTest.getCurrentTestDirectionIndex();
//        int currentTestGrades = visionTest.getCurrentTestGrades();
//        String currentTestDirection1 = visionTest.getCurrentTestDirection();
//        float testGrades = visionTest.getTestGrades();
//        LogUtils.d("visionTest", "currentTestDirection1 = " + currentTestDirection1 + ",testGrades = " + testGrades);
//        mViewBinding.ivE.setE(currentTestDirection, currentTestGrades);
//        mViewBinding.ivSuccess.setVisibility(View.GONE);
//        startTest();


        testingText = getString(R.string.testing);
        mViewBinding.tvTitle.setText(testingText.replace("{eye}", currentEye));

    }


    private void initCountDownDialog() {
        countDownDialog = new CountDownDialog(getActivity());
        countDownDialog.setEyeReadyListener(new CountDownDialog.EyeReadyListener() {

            @Override
            public void firstEyeReadyFinish() {
                startTime = System.currentTimeMillis();
                visionTest = new VisionTest();
                //添加一次测试
                visionTest.addTest();
                int currentTestDirection = visionTest.getCurrentTestDirectionIndex();
                int currentTestGrades = visionTest.getCurrentTestGrades();
                String currentTestDirection1 = visionTest.getCurrentTestDirection();
                float testGrades = visionTest.getTestGrades();
                LogUtils.d("visionTest", "currentTestDirection1 = " + currentTestDirection1 + ",testGrades = " + testGrades);
                mViewBinding.ivE.setE(currentTestDirection, currentTestGrades);
                mViewBinding.ivSuccess.setVisibility(View.GONE);
                startTest();
            }

            @Override
            public void secondEyeReadyFinish() {
                startTime = System.currentTimeMillis();
                visionTest = new VisionTest();
                //添加一次测试
                visionTest.addTest();
                int currentTestDirection = visionTest.getCurrentTestDirectionIndex();
                int currentTestGrades = visionTest.getCurrentTestGrades();
                String currentTestDirection1 = visionTest.getCurrentTestDirection();
                float testGrades = visionTest.getTestGrades();
                LogUtils.d("visionTest", "currentTestDirection1 = " + currentTestDirection1 + ",testGrades = " + testGrades);
                mViewBinding.ivE.setE(currentTestDirection, currentTestGrades);
                startTest();
            }
        });
    }

    @Override
    @Permission(Manifest.permission_group.MICROPHONE)
    public void initData(Bundle savedInstanceState) {
        mViewBinding.ivE.setE(E.D_DOWN, 0);

    }

    public void startTest() {
        isStop = false;
        SpeachUtil.speachInput(getActivity(), new SpeachInputListener() {
            @Override
            public void onVolumeChanged(int volume, byte[] data) {
                long currentTime = System.currentTimeMillis();
                long dTime = currentTime - startTime;
                if (dTime > 30000 && !isStop60s) {
                    isStop60s = true;
                    mViewBinding.tvTitle.setText("超过30s未检测到测试信息");

                    //播放语音
                    playAudio(R.raw.retest);
                } else if (dTime > 10000 && dTime < 30000 && !isStop10s) {
                    isStop10s = true;
                    mViewBinding.tvTitle.setText("播放超过10s未检测到测试信息");
                    //播放语音
                    playAudio(R.raw.nothing);
                }
                LogUtils.d("SpeachActivityInput", "onVolumeChanged volume = " + volume + ",data" + data.toString());
            }

            @Override
            public void onBeginOfSpeech() {
                LogUtils.d("SpeachActivityInput", "onVolumeChanged");
            }

            @Override
            public void onEndOfSpeech(String result) {
                endSpeech();
                LogUtils.d("SpeachActivityInput", "onEndOfSpeech result = " + result);
            }

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                resultSpeech(results, isLast);
            }

            @Override
            public void onError(SpeechError speechError) {
                endSpeech();
                LogUtils.d("SpeachActivityInput", "onError speechError = " + speechError.getErrorDescription());
            }


        });

    }

    private void resultSpeech(RecognizerResult results, boolean isLast) {
        String result = results.getResultString();
        LogUtils.d("SpeachActivityInput", "resultSpeech result = " + result + ",isList = " + isLast);
        if (TextUtils.isEmpty(result)) return;
        if (mViewBinding.ivSuccess.getVisibility() == View.GONE)
            mViewBinding.ivSuccess.setVisibility(View.VISIBLE);

        mViewBinding.tvTitle.setText(result);
        int containsUp = result.matches(VisionTest.upReg) ? 1 : 0;
        int containsDown = result.matches(VisionTest.downReg) ? 1 : 0;
        int containsLeft = result.matches(VisionTest.leftReg) ? 1 : 0;
        int containsRight = result.matches(VisionTest.rightReg) ? 1 : 0;


        int re = containsUp + containsDown + containsLeft + containsRight;

        if (re == 1) {

            //重置开始时间
            startTime = System.currentTimeMillis();

            //设置本次测试结果
            if (1 == containsUp) {
                result = "上";
            } else if (1 == containsDown) {
                result = "下";
            } else if (1 == containsLeft) {
                result = "左";
            } else if (1 == containsRight) {
                result = "右";
            }
            boolean isSuccess = visionTest.setCurrentTestResult(result);

            if (isSuccess) {
                mViewBinding.ivSuccess.setImageResource(R.mipmap.icon_checkmark_round);
            } else {
                mViewBinding.ivSuccess.setImageResource(R.mipmap.icon_error_round);
            }
            //停止当前会话
            cancelSpeach();

            if (VisionTest.RESULT_END.equals(visionTest.addTest())) {
                //结束测试
                visionTest.getTestGrades();
                if ("左眼".equals(currentEye)) {
                    visionTest.leftEyeGrades = visionTest.getTestGrades() + "";
                    countDownDialog.show(CountDownDialog.RIGHT_EYE);
                    currentEye = "右眼";
                } else {
                    visionTest.rightEyeGrades = visionTest.getTestGrades() + "";
                    start(new VisionTestResultFragment());
                }

//                mViewBinding.tvDirection.setText("测试结果：" + visionTest.getTestGrades() + "");
            } else {
                int currentTestDirection = visionTest.getCurrentTestDirectionIndex();
                int currentTestGrades = visionTest.getCurrentTestGrades();
                String currentTestDirection1 = visionTest.getCurrentTestDirection();
                float testGrades = visionTest.getTestGrades();
                mViewBinding.ivE.setE(currentTestDirection, currentTestGrades);
                mViewBinding.ivSuccess.setVisibility(View.GONE);
                LogUtils.d("visionTest", "currentTestDirection1 = " + currentTestDirection1 + ",testGrades = " + testGrades);
                //开始下一次测试
                startTest();
//                mViewBinding.tvDirection.setText(visionTest.getCurrentTtestDirection() + ",等级" + visionTest.getTestGrades());
            }
        }else {
            mViewBinding.ivSuccess.setImageResource(R.mipmap.icon_error_round);
        }

    }

    //一次会话结束
    private void endSpeech() {

//            //超过60s没有检测到信息
//            if (isStop60s) finish();
        //没有检测到测试信息,或检测到多个测试信息开始下一次检测或者停止
        if (!isStop) startTest();


    }

    //播放提示音频
    private void playAudio(int raw) {
        //取消会话
        cancelSpeach();


        mediaPlayer = MediaPlayer.create(getActivity(), raw);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                LogUtils.d("SpeachActivityInput", "playAudio End");
                mViewBinding.tvTitle.setText("开始检测");
                if (isStop60s) {
                    getActivity().finish();
                } else {
                    startTest();
                }
            }
        });
        mediaPlayer.start();

        LogUtils.d("SpeachActivityInput", "playAudio Start");

    }

    //取消当前会话
    private void cancelSpeach() {
        isStop = true;
        SpeachUtil.cancelInput(getActivity());
        LogUtils.d("SpeachActivityInput", "cancelSpeach");
    }


    @Override
    public void onPause() {
        super.onPause();
        cancelSpeach();
        SpeachUtil.stopInput(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        countDownDialog.dismiss();
        if (mediaPlayer != null)
            mediaPlayer.release();
    }
}
