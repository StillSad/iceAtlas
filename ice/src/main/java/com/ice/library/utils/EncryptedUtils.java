package com.ice.library.utils;



/**
 * Created by ICE on 2017/6/29.
 */

public class EncryptedUtils {
    public static final String KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKMzbZZq4QyMPLSye2QkBqf" + "cE3QbFzWO5r1+MrL5PVTOFMpC/SfYYXxa9F0mo3Yc/PszHd7EYsWCeVHjU0kHWBmmiWLqK92" + "ZbwLImnV434ZcXolEMZFLl2+2X1gikjibHUP/UYS+CG8" + "Gc7lfyYuv8N5aYHCzkKja+GQjZrlB5ZwQIDAQAB";

    //加密
    public static String encrypted(String secret) {
        String afterencrypt = "";
        try {
            byte[] encryptByte = RSAUtils.encryptData(Base64Utils.encode(secret.getBytes()).getBytes(),
                    RSAUtils.loadPublicKey(KEY ));
            afterencrypt = Base64Utils.encode(encryptByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("CommunityLoginHttp","secret = " + secret);
//        Log.d("CommunityLoginHttp","afterencrypt = " + afterencrypt);
        return afterencrypt;
    }

}
