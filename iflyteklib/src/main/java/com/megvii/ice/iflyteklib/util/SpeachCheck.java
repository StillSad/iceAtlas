package com.megvii.ice.iflyteklib.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeakerVerifier;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.VerifierListener;
import com.iflytek.cloud.VerifierResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ICE on 2017/1/12.
 */

public class SpeachCheck {

    public static final String TAG = "SpeachCheck";
    public static final String CHECK_NUM = "3";
    // 当前声纹密码类型，1、2、3分别为文本、自由说和数字密码
    private static int pwdType = 3;
    private SpeakerVerifier mVerifier;
    private ISpeachRegisterListener mSpeachRegisterListener;
    private ISpeachVerifierListener mSpeachVerifierListener;
    private String mNumPwd = "";
    private String mAuthId;
    // 数字声纹密码段，默认有5段
    private String[] mNumPwdSegs;

    public SpeachCheck(Context context) {
        // 首先创建SpeakerVerifier对象
        mVerifier = SpeakerVerifier.createVerifier(context, null);
        // 通过setParameter设置密码类型，pwdType的取值为1、3，分别表示文本密码和数字密码
        mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
    }

    public void setSpeachRegisterListener(ISpeachRegisterListener speachRegisterListener) {
        mSpeachRegisterListener = speachRegisterListener;
    }

    public void setSpeachVerifierListener(ISpeachVerifierListener speachVerifierListener) {
        mSpeachVerifierListener = speachVerifierListener;
    }


    /*-------------------------------------------注册-------------------------------------------------------*/
    private SpeechListener mPwdListenter = new SpeechListener() {
        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            String result = new String(buffer);
            StringBuffer numberString = new StringBuffer();
            try {
                JSONObject object = new JSONObject(result);
                if (!object.has("num_pwd")) {
                    mNumPwd = null;
                    return;
                }

                JSONArray pwdArray = object.optJSONArray("num_pwd");
                numberString.append(pwdArray.get(0));
                for (int i = 1; i < pwdArray.length(); i++) {
                    numberString.append("-" + pwdArray.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mNumPwd = numberString.toString();
            mNumPwdSegs = mNumPwd.split("-");

            Log.d(TAG, "mNumPwd = " + mNumPwd);
            mSpeachRegisterListener.getPwdSuccess(mNumPwd);
            starRegister();
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (null != error && ErrorCode.SUCCESS != error.getErrorCode()) {
                mSpeachRegisterListener.getPwdFailed("" + error.getErrorCode());
            }
        }
    };

    private VerifierListener mRegisterListener = new VerifierListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
            mSpeachRegisterListener.onVolumeChanged(volume, data);
            Log.d(TAG, "返回音频数据：" + data.length + ",当前正在说话，音量大小：" + volume);
        }

        @Override
        public void onResult(VerifierResult result) {

            if (result.ret == ErrorCode.SUCCESS) {
                mSpeachRegisterListener.registerErroe(result.err,result.suc);
//                switch (result.err) {
//                    case VerifierResult.MSS_ERROR_IVP_GENERAL:
//                        //内核异常
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_GENERAL);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_EXTRA_RGN_SOPPORT:
//                        //训练达到最大次数
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_EXTRA_RGN_SOPPORT);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_TRUNCATED:
//                        //出现截幅
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_TRUNCATED);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_MUCH_NOISE:
//                        //太多噪音
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_MUCH_NOISE);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_UTTER_TOO_SHORT:
//                        //录音太短
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_UTTER_TOO_SHORT);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH:
//                        //内容不一致
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_TEXT_NOT_MATCH);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
//                        //音量太低
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_TOO_LOW);
//                        break;
//                    case VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO:
//                        //音长达不到自由说的要求
//                        mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_NO_ENOUGH_AUDIO);
//                    default:
//                        mSpeachRegisterListener.registerErroe(result.err);
//                        break;
//                }

                if (result.suc == result.rgn) {
//                    mShowMsgTextView.setText("注册成功");
                    mSpeachRegisterListener.registerSuccess(result.vid);
//                    mResultEditText.setText("您的数字密码声纹ID：\n" + );
                } else {
                    int nowTimes = result.suc;
                    int leftTimes = result.rgn - nowTimes;

                    Log.e(TAG, "mSpeachRegisterListener = " + mSpeachRegisterListener);
                    mSpeachRegisterListener.registering(mNumPwdSegs[nowTimes], nowTimes, leftTimes);

                }

            } else {
                mSpeachRegisterListener.registerFailed();
            }
        }

        // 保留方法，暂不用
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle arg3) {
//             以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
//            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//                Log.d(TAG, "session id =" + sid);
//            }
        }

        @Override
        public void onError(SpeechError error) {

            if (error.getErrorCode() == ErrorCode.MSP_ERROR_ALREADY_EXIST) {
//                Log.d(TAG, "模型已存在，如需重新注册，请先删除");
                mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSP_ERROR_ALREADY_EXIST,0);
            } else {
                Log.d(TAG, "onError Code：" + error.getPlainDescription(true));
                mSpeachRegisterListener.registerFailed();
            }
        }

        @Override
        public void onEndOfSpeech() {
            mSpeachRegisterListener.onEndOfSpeech();
            Log.d(TAG, "结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            mSpeachRegisterListener.onBeginOfSpeech();
            Log.d(TAG, "开始说话");
        }
    };

    public void register(String authId) {
        //注册第一步获取密码
        //第二步在mPwdListenter成功获取密码后
        mVerifier.cancel();
        // 清空参数
        mVerifier.setParameter(SpeechConstant.PARAMS, null);
        mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        mVerifier.setParameter(SpeechConstant.ISV_RGN, "" + CHECK_NUM);
        mVerifier.getPasswordList(mPwdListenter);
        Log.d(TAG, "mNumPwd = " + mNumPwd);
        this.mAuthId = authId;
    }

    private void starRegister() {
        mVerifier.setParameter(SpeechConstant.PARAMS, null);
        mVerifier.setParameter(SpeechConstant.ISV_RGN, "" + CHECK_NUM);
        mVerifier.setParameter(SpeechConstant.ISV_AUDIO_PATH,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/test.pcm");
//         对于某些麦克风非常灵敏的机器，如nexus、samsung i9300等，建议加上以下设置对录音进行消噪处理
//			mVerify.setParameter(SpeechConstant.AUDIO_SOURCE, "" + MediaRecorder.AudioSource.VOICE_RECOGNITION);

//         数字密码注册需要传入密码
        if (TextUtils.isEmpty(mNumPwd)) {
//            showTip("请获取密码后进行操作");
            mSpeachRegisterListener.registerErroe(SpeachErrorCode.MSS_ERROR_IVP_NO_GET_PWD,-1);
            return;
        }
        mVerifier.setParameter(SpeechConstant.ISV_PWD, mNumPwd);

        mSpeachRegisterListener.registering(mNumPwd.substring(0, 8), 0, 2);
        // 设置auth_id，不能设置为空
        mVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);
        // 设置业务类型为注册
        mVerifier.setParameter(SpeechConstant.ISV_SST, "train");
        // 设置声纹密码类型
        mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        // 开始注册
        mVerifier.startListening(mRegisterListener);
    }

    /*--------------------------------------------验证-------------------------------------------------------*/
    private VerifierListener mVerifyListener = new VerifierListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//         showTip("当前正在说话，音量大小：" + volume);
            mSpeachVerifierListener.onVolumeChanged(volume, data);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onResult(VerifierResult result) {
//            setRadioClickable(true);
//         mShowMsgTextView.setText(result.source);
            mSpeachVerifierListener.verifying(result.source);
            if (result.ret == 0) {
                // 验证通过
//             mShowMsgTextView.setText("验证通过");
                mSpeachVerifierListener.verifySuccess("验证通过");
            } else {
                // 验证不通过
                switch (result.err) {
                    case VerifierResult.MSS_ERROR_IVP_GENERAL:
//                     mShowMsgTextView.setText("内核异常");
                        mSpeachVerifierListener.verifyErroe("内核异常");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TRUNCATED:
//                     mShowMsgTextView.setText("出现截幅");
                        mSpeachVerifierListener.verifyErroe("出现截幅");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_MUCH_NOISE:
//                     mShowMsgTextView.setText("太多噪音");
                        mSpeachVerifierListener.verifyErroe("太多噪音");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_UTTER_TOO_SHORT:
//                     mShowMsgTextView.setText("录音太短");
                        mSpeachVerifierListener.verifyErroe("录音太短");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH:
//                     mShowMsgTextView.setText("验证不通过，您所读的文本不一致");
                        mSpeachVerifierListener.verifyErroe("验证不通过，您所读的文本不一致");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
//                     mShowMsgTextView.setText("音量太低");
                        mSpeachVerifierListener.verifyErroe("音量太低");
                        break;
                    case VerifierResult.MSS_ERROR_IVP_NO_ENOUGH_AUDIO:
//                     mShowMsgTextView.setText("音频长达不到自由说的要求");
                        mSpeachVerifierListener.verifyErroe("音频长达不到自由说的要求");
                        break;
                    default:
//                     mShowMsgTextView.setText("验证不通过");
                        mSpeachVerifierListener.verifyErroe("验证不通过");
                        break;
                }
            }
        }

        // 保留方法，暂不用
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle arg3) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

        @Override
        public void onError(SpeechError error) {
//            setRadioClickable(true);

            switch (error.getErrorCode()) {
                case ErrorCode.MSP_ERROR_NOT_FOUND:
//                 mShowMsgTextView.setText("模型不存在，请先注册");
                    mSpeachVerifierListener.verifyErroe("模型不存在，请先注册");
                    break;

                default:
//                 showTip("onError Code：" + error.getPlainDescription(true));
                    mSpeachVerifierListener.verifyErroe(error.getPlainDescription(true));
                    break;
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//         showTip("结束说话");
            mSpeachVerifierListener.onEndOfSpeech();
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//         showTip("开始说话");
            mSpeachVerifierListener.onBeginOfSpeech();
        }
    };

    public void verify(String authId) {

        // 清空参数
        mVerifier.setParameter(SpeechConstant.PARAMS, null);
        mVerifier.setParameter(SpeechConstant.ISV_AUDIO_PATH,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/verify.pcm");
        mVerifier = SpeakerVerifier.getVerifier();
        // 设置业务类型为验证
        mVerifier.setParameter(SpeechConstant.ISV_SST, "verify");
        // 对于某些麦克风非常灵敏的机器，如nexus、samsung i9300等，建议加上以下设置对录音进行消噪处理
//			mVerify.setParameter(SpeechConstant.AUDIO_SOURCE, "" + MediaRecorder.AudioSource.VOICE_RECOGNITION);
        // 数字密码注册需要传入密码
        String verifyPwd = mVerifier.generatePassword(8);
        mVerifier.setParameter(SpeechConstant.ISV_PWD, verifyPwd);
//        ((TextView) findViewById(R.id.showPwd)).setText("请读出："
//                + verifyPwd);
        mSpeachVerifierListener.verifying(verifyPwd);
        // 设置auth_id，不能设置为空
        mVerifier.setParameter(SpeechConstant.AUTH_ID, authId);
        mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);
        // 开始验证
        mVerifier.startListening(mVerifyListener);
    }

    /*--------------------------------------------模型操作-------------------------------------------------------*/
    private static final int PWD_TYPE_TEXT = 1;
    private static final int PWD_TYPE_NUM = 3;

    public void performModelOperation(String operation,String authId,String textPwd) {
        // 清空参数
        mVerifier.setParameter(SpeechConstant.PARAMS, null);
        mVerifier.setParameter(SpeechConstant.ISV_PWDT, "" + pwdType);

        if (pwdType == PWD_TYPE_TEXT) {
            // 文本密码删除需要传入密码
            if (TextUtils.isEmpty(textPwd)) {
                return;
            }
            mVerifier.setParameter(SpeechConstant.ISV_PWD, textPwd);
        } else if (pwdType == PWD_TYPE_NUM) {

        }
        // 设置auth_id，不能设置为空
        mVerifier.sendRequest(operation, authId,mModelOperationListener);
    }

    private SpeechListener mModelOperationListener = new SpeechListener() {

        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            String result = new String(buffer);
            try {
                JSONObject object = new JSONObject(result);
                String cmd = object.getString("cmd");
                int ret = object.getInt("ret");

                if ("del".equals(cmd)) {
                    if (ret == ErrorCode.SUCCESS) {
//                        showTip("删除成功");
//                        mResultEditText.setText("");
                    } else if (ret == ErrorCode.MSP_ERROR_FAIL) {
//                        showTip("删除失败，模型不存在");
                    }
                } else if ("que".equals(cmd)) {
                    if (ret == ErrorCode.SUCCESS) {
//                        showTip("模型存在");
                    } else if (ret == ErrorCode.MSP_ERROR_FAIL) {
//                        showTip("模型不存在");
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onCompleted(SpeechError error) {

            if (null != error && ErrorCode.SUCCESS != error.getErrorCode()) {
//                showTip("操作失败：" + error.getPlainDescription(true));
            }
        }
    };


}
