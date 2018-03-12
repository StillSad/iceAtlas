package com.ice.library.utils;

import android.os.Build;

import com.ice.library.constant.AppConfig;

import java.util.UUID;

/**
 * Created by ICE on 2017/8/3.
 */

public class DeviceUtil {
    //获得独一无二的Psuedo ID
    public static String getDeviceId() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
//            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString() + "-" + StringConstant.APP_NAME;
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        } finally {
            //使用硬件信息拼凑出来的15位号码 + app名称
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString() + "-" + AppConfig.APP_NAME;
        }

    }
    //获取设备信息
    public static String getDeviceInfo() {
        return android.os.Build.MODEL + " " + android.os.Build.VERSION.SDK + " " + android.os.Build.VERSION.RELEASE;
    }
    public static String getTime() {
        return System.currentTimeMillis() + "";
    }
}
