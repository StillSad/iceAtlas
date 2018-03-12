package com.ice.library.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ICE on 2017/9/7.
 */

public class StringUtils {
    public static boolean checkMobil(String mobile) {
        LogUtils.d("获取手机验证码");
        if (TextUtils.isEmpty(mobile)) return true;
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\\d{8}$";
        if (!mobile.matches(regex)) return true;
        return false;
    }

    public static String getFromEtOrTv(View view) {
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else if (view instanceof TextView){
            return ((TextView) view).getText().toString().trim();
        } else {
            throw new IllegalArgumentException("view isn't EditText or TextView");
        }
    }

    public static boolean isNotNull(String value) {
        return value != null && !"".equals(value.trim()) && !"null".equalsIgnoreCase(value);
    }

    public static boolean isNotNull(String... values) {
        String[] var = values;
        int vLength = values.length;

        for(int i = 0; i < vLength; ++i) {
            String value = var[i];
            if(!isNotNull(value)) {
                return false;
            }
        }

        return true;
    }
}
