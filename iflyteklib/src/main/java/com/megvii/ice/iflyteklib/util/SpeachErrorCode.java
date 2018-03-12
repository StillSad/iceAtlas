package com.megvii.ice.iflyteklib.util;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.VerifierResult;

/**
 * Created by ICE on 2017/4/12.
 */

public interface SpeachErrorCode {

    int MSP_ERROR_ALREADY_EXIST = ErrorCode.MSP_ERROR_ALREADY_EXIST;
    //内核异常
    int MSS_ERROR_IVP_GENERAL = VerifierResult.MSS_ERROR_IVP_GENERAL;
    //训练达到最大次数
    int MSS_ERROR_IVP_EXTRA_RGN_SOPPORT = VerifierResult.MSS_ERROR_IVP_EXTRA_RGN_SOPPORT;
    //出现截幅
     int MSS_ERROR_IVP_TRUNCATED = VerifierResult.MSS_ERROR_IVP_TRUNCATED;
    //太多噪音
    int MSS_ERROR_IVP_MUCH_NOISE =  VerifierResult.MSS_ERROR_IVP_MUCH_NOISE;
    //录音太短
    int MSS_ERROR_IVP_UTTER_TOO_SHORT = VerifierResult.MSS_ERROR_IVP_UTTER_TOO_SHORT;
    //内容不一致
    int  MSS_ERROR_IVP_TEXT_NOT_MATCH = VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH;
    //音量太低
    int MSS_ERROR_IVP_TOO_LOW = VerifierResult.MSS_ERROR_IVP_TOO_LOW;
    //音长达不到自由说的要求
    int MSS_ERROR_IVP_NO_ENOUGH_AUDIO = VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO;
    //没有获取声纹注册密码
    int MSS_ERROR_IVP_NO_GET_PWD = 21608;

}
