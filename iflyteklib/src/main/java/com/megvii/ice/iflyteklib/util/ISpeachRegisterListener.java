package com.megvii.ice.iflyteklib.util;

/**
 * Created by ICE on 2017/1/12.
 */

public interface ISpeachRegisterListener {
    void registerErroe(int errorCode,int itemNum);

    void getPwdSuccess(String pwd);

    void registerSuccess(String string);

    void getPwdFailed(String failedMsg);

    void registerFailed();

    void registering(String string, int nowTimes, int leftTimes);

    void onVolumeChanged(int volume, byte[] data);

    void onEndOfSpeech();

    void onBeginOfSpeech();
}
