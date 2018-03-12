package com.megvii.ice.iflyteklib.util;

/**
 * Created by ICE on 2017/1/12.
 */

public interface ISpeachVerifierListener {
    void verifyErroe(String error);

    void getPwdSuccess(String pwd);

    void verifySuccess(String string);

    void getPwdFailed(String failedMsg);

    void verifyFailed(String failedMsg);

    void verifying(String string);

    void onVolumeChanged(int volume, byte[] data);

    void onEndOfSpeech();

    void onBeginOfSpeech();
}
