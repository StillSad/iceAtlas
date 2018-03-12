package com.megvii.ice.iflyteklib.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by ICE on 2017/1/12.
 */

public class SpeachOutput {
    public static final String TAG = "SpeachOut";
    private static SpeachOutput instance;
    //合成监听器
    private static SynthesizerListener mSynListener = new SynthesizerListener(){
        //开始播放
        @Override
        public void onSpeakBegin() {
            Log.d(TAG,"开始播放");
        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在 文本中结束位置，info为附加信息
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            Log.d(TAG,"缓冲进度回调");
        }
        //暂停播放
        @Override
        public void onSpeakPaused() {
            Log.d(TAG,"暂停播放");
        }
        //恢复播放回调接口
        @Override
        public void onSpeakResumed() {
            Log.d(TAG,"恢复播放回调接口");
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文 本中结束位置.
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            Log.d(TAG,"播放进度回调");
        }
        //会话结束回调接口，没有错误时，error为null
        @Override
        public void onCompleted(SpeechError error) {
            Log.d(TAG,"会话结束回调接口");
        }
        //会话事件回调接口
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.d(TAG,"会话事件回调接口");
        }
    };

    public void out(Context context,String string){

        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context, null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        // 设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成
        mTts.startSpeaking(string, mSynListener);
    }
    private SpeachOutput() {}
    public static synchronized SpeachOutput getInstance() {
        if (instance == null) {
            instance = new SpeachOutput();
        }
        return instance;
    }
}
