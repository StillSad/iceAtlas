package com.megvii.ice.iflyteklib.util;

/**
 * Created by ICE on 2017/1/12.
 */

public abstract class SpeachRegisterListenerImpl implements ISpeachRegisterListener {

    @Override
    public void registerErroe(int errorCode,int itemNum) {

    }

    @Override
    public void getPwdSuccess(String pwd) {

    }

    /**
     *
     * @param string 声纹ID
     */
    @Override
    public void registerSuccess(String string) {

    }

    @Override
    public void getPwdFailed(String string) {

    }

    @Override
    public void registerFailed() {

    }

    @Override
    public void registering(String string, int nowTimes, int leftTimes) {

    }

    @Override
    public void onVolumeChanged(int volume, byte[] data) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onBeginOfSpeech() {

    }
}
