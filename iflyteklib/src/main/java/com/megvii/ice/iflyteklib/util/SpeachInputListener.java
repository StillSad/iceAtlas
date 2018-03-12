package com.megvii.ice.iflyteklib.util;

import android.os.Bundle;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;

/**
 * Created by mac on 17/4/9.
 */

public abstract class SpeachInputListener {
    public void onVolumeChanged(int volume, byte[] data){};
    //开始录音
    public void onBeginOfSpeech() {}
    //结束录音
    public void onEndOfSpeech(String result) {}
    //isLast等于true时会话结束
    public void onResult(RecognizerResult results, boolean isLast) {}
    //会话发生错误回调接口
    public void onError(SpeechError speechError) {}


    //扩展用接
    public void onEvent(int i, int i1, int i2, Bundle bundle) {}
}
