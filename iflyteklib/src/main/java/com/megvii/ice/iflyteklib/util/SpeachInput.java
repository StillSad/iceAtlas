package com.megvii.ice.iflyteklib.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.megvii.ice.iflyteklib.word.UserWord;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Created by ICE on 2017/1/12.
 */

public class SpeachInput {

    private static SpeachInput instance;
    private static String TAG = "SpeachInput";
    private SpeachInputListener inputListener;
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/demo.wav";


    public RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见Demo中JsonParser类；

        //volume音量值0~30，data音频数据
        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            if (inputListener != null) {
                inputListener.onVolumeChanged(volume, data);
            }
            Log.d(SpeachUtil.TAG, "volume = " + volume + ",data = ");
        }

        //开始录音
        @Override
        public void onBeginOfSpeech() {
            if (inputListener != null) {
                inputListener.onBeginOfSpeech();
            }
            Log.d(SpeachUtil.TAG, "开始录音");
        }

        //结束录音
        @Override
        public void onEndOfSpeech() {
            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            if (inputListener != null) {
                inputListener.onEndOfSpeech(resultBuffer.toString());
            }
            Log.d(SpeachUtil.TAG, "结束录音");
        }

        //isLast等于true时会话结束
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if (inputListener != null) {
                inputListener.onResult(results, isLast);
            }
            printResult(results);
            Log.d(SpeachUtil.TAG, "isLast = " + isLast);
        }

        //会话发生错误回调接口
        @Override
        public void onError(SpeechError speechError) {
            if (inputListener != null) {
                inputListener.onError(speechError);
            }
        }

        //扩展用接
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            if (inputListener != null) {
                inputListener.onEvent(i, i1, i2, bundle);
            }
        }
    };

    public String getWavPath() {
        return filePath;
    }

    public static SpeachInput getInstance() {
        if (instance == null) {
            synchronized (SpeachInput.class) {
                if (instance == null) {
                    instance = new SpeachInput();
                }

            }
        }
        return instance;
    }

    public SpeachInput setInputListener(SpeachInputListener listener) {
        this.inputListener = listener;
        return this;
    }

    public void in(Context context) {

        //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
        SpeechRecognizer recognizer = getSpeechRecognizer(context);

        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //识别音频保存路径
        //recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, "mandarin ");
        //音频格式 保存的音频格式
        //recognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        //3.开始听写
        int ret = recognizer.startListening(mRecoListener);
        if (ret != ErrorCode.SUCCESS) {

        } else {

        }
    }

    //取消会话
    public void cancel(Context context) {
        getSpeechRecognizer(context).cancel();
    }

    public void stop(Context context) {
        getSpeechRecognizer(context).stopListening();
    }

    //是否在会话中
    public boolean isListening(Context context) {
        return getSpeechRecognizer(context).isListening();
    }

    private synchronized SpeechRecognizer getSpeechRecognizer(Context context) {
        //获取单例对象 通过函数获取已创建的单例对象。
        SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
        //未创建过单例，去创建单例对象
        if (recognizer == null) {
            recognizer = SpeechRecognizer.createRecognizer(context, null);
//            recognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
//            // 指定引擎类型 m
//            recognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//            //上传用户词表
//            int ret =  recognizer.updateLexicon(UserWord.userName,UserWord.userWord,lexiconListener);
//            if(ret != ErrorCode.SUCCESS){  Log.d(TAG,"上传用户词表失败：" + ret); }
        }
        return recognizer;
    }

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        Log.d(SpeachUtil.TAG, resultBuffer.toString());
    }


    private LexiconListener lexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                Log.d(TAG, error.toString());
            } else {
                Log.d(TAG, "上传成功！");
            }
        }
    };
}
